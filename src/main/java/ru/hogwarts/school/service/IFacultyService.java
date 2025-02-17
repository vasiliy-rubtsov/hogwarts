package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface IFacultyService extends IService<Faculty> {
    Collection<Faculty> findByColor(String color);
    Collection<Faculty> findGlobal(String str);
    Collection<Student> students(Long facultyId);
}
