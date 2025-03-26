package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface IStudentService extends IService<Student> {
    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Faculty faculty(Long studentId);

    Integer getNumberOfStudents();

    Integer getAverageAge();

    Integer getAverageAgeV2();

    Collection<Student> getLast5Students();

    List<String> getStudentNamesStartingWithLitera(String litera);
}
