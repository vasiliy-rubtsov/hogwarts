package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Service
public class FacultyService implements IFacultyService {

    private final FacultyRepository repository;
    private Logger logger;

    public FacultyService(FacultyRepository repository, Logger logger) {
        this.repository = repository;
        this.logger = logger;
    }

    @Override
    public Faculty add(Faculty faculty) {
        logger.info("Was invoked method for create faculty {}", faculty);
        faculty.setId(null);
        return repository.save(faculty);
    }

    @Override
    public Faculty upd(Faculty faculty) {
        logger.info("Was invoked method for update faculty with ID = {}. New values: {}", faculty.getId(), faculty);
        return repository.save(faculty);
    }

    @Override
    public void remove(Long id) {
        logger.warn("Was invoked method for delete faculty with ID = {}", id);
        repository.deleteById(id);
    }

    @Override
    public Faculty find(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Collection<Faculty> getAll() {
        return repository.findAll();
    }

    @Override
    public Collection<Faculty> findByColor(String color) {
        return repository.findByColorIgnoreCase(color);
    }

    @Override
    public Collection<Faculty> findGlobal(String str) {
        return repository.findGlobal(str);
    }

    @Override
    public Collection<Student> students(Long facultyId) {
        Optional<Faculty> faculty = repository.findById(facultyId);
        if (faculty.isEmpty()) {
            return null;
        }

        return faculty.get().getStudents();
    }
}

