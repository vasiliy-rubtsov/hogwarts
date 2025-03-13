package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.IAvatarService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class AvatarController {
    private final IAvatarService avatarService;

    public AvatarController(IAvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{studentId}/avatar/preview")
    public ResponseEntity<byte[]> previewAvatar(@PathVariable Long studentId) {
        Avatar avatar = avatarService.findAvatarByStudentId(studentId);
        if (avatar == null) {
            return ResponseEntity.noContent().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());

    }

    @GetMapping("/{studentId}/avatar")
    public void downloadAvatar(@PathVariable Long studentId, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatarByStudentId(studentId);
        if (avatar == null) {
            throw new IOException("Не найден аватар для студента с ID = " + studentId);
        }

        Path path = Path.of(avatar.getFilePath());

        try (
            InputStream is = Files.newInputStream(path);
            OutputStream os = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            bis.transferTo(bos);
        }
    }

}
