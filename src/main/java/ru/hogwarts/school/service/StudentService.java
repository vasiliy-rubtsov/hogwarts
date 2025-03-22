package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentService implements IStudentService {

    private final StudentRepository repository;
    private Logger logger;

    public StudentService(StudentRepository repository, Logger logger) {
        this.repository = repository;
        this.logger = logger;
    }

    @Override
    public Student add(Student student) {
        logger.info("Was invoked method for create student {}", student);
        student.setId(null);
        return repository.save(student);
    }

    @Override
    public Student upd(Student student) {
        logger.info("Was invoked method for update student with ID = {}. New values: {}", student.getId(), student);
        return repository.save(student);
    }

    @Override
    public void remove(Long id) {
        logger.warn("Was invoked method for delete student with ID = {}", id);
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
    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        return repository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty faculty(Long studentId) {
        Optional<Student> student = repository.findById(studentId);
        if (student.isEmpty()) {
            return null;
        }

        return student.get().getFaculty();
    }

    @Override
    public Integer getNumberOfStudents() {
        return this.repository.getNumberOfStudents();
    }

    @Override
    public Integer getAverageAge() {
        return this.repository.getAverageAge();
    }

    @Override
    public Collection<Student> getLast5Students() {
        return this.repository.getLast5Students();
    }
}
