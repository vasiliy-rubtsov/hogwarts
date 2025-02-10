package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface IFacultyService extends IService<Faculty> {
    Collection<Faculty> findByColor(String color);
}
