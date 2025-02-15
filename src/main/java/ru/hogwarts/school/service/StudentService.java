package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Service
public class StudentService implements IStudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student add(Student student) {
        student.setId(null);
        return repository.save(student);
    }

    @Override
    public Student upd(Student student) {
        return repository.save(student);
    }

    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Student find(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Collection<Student> getAll() {
        return repository.findAll();
    }

    @Override
    public Collection<Student> findByAge(int age) {
        return repository.findByAge(age);
    }
}
