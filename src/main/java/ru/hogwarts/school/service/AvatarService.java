package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService implements IAvatarService{
    private AvatarRepository avatarRepository;
    private StudentRepository studentRepository;
    private Logger logger;

    @Value("${path.to.avatars.folder}")
    private String fileDir;


    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository, Logger logger) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.logger = logger;
    }

    private String getExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index < 1) {
            return "";
        } else {
            return fileName.substring(index + 1);
        }
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for upload avatar for student wjjth ID = {}", studentId);
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            logger.error("There is not student with ID = {}", studentId);
            throw new IOException("Не найден студент c ID = " + studentId);
        }
        String extension = getExtension(avatarFile.getOriginalFilename());
        Path filePath = Path.of(fileDir, "student-" + student.getId() + "." + extension);

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
            InputStream is = avatarFile.getInputStream();
            OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setData(generateImagePreview(filePath, 100));
        avatarRepository.save(avatar);
    }

    private byte[] generateImagePreview(Path filePath, int width) throws IOException {
        try (
            InputStream is = Files.newInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ) {
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight() * width / image.getWidth();
            BufferedImage preview = new BufferedImage(width, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, width, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.toString()), baos);
            return baos.toByteArray();
        }
    }

    @Override
    public Avatar findAvatarByStudentId(Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(null);
    }

    @Override
    public Collection<Avatar> findAll(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
