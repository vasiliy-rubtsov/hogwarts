package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.Collection;

public interface IAvatarService {
    void uploadAvatar(Long studentId, MultipartFile avatar) throws IOException;

    Avatar findAvatarByStudentId(Long studentId);

    Collection<Avatar> findAll(Integer pageNumber, Integer pageSize);
}
