package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Service
public class StudentService implements IStudentService {
    private final HashMap<Long, Student> students = new HashMap<>();
    private long lastId = 0;

    @Override
    public Student add(Student student) {
        student.setId(++lastId);
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Student upd(Student student) {
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Student remove(long id) {
        return students.remove(id);
    }

    @Override
    public Student find(long id) {
        return students.get(id);
    }

    @Override
    public Collection<Student> getAll() {
        return students.values();
    }

    @Override
    public Collection<Student> findByAge(int age) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getAge() == age) {
                result.add(student);
            }
        }

        return result;
    }
}
