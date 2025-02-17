package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface IStudentService extends IService<Student> {
    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Faculty faculty(Long studentId);
}
