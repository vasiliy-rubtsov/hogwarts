package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Service
public class FacultyService implements IFacultyService {

    private final FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        faculty.setId(null);
        return repository.save(faculty);
    }

    @Override
    public Faculty upd(Faculty faculty) {
        return repository.save(faculty);
    }

    @Override
    public void remove(Long id) {
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
        return repository.findByColorLike(color);
    }
}
