package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.IFacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final IFacultyService service;

    public FacultyController(IFacultyService service) {
        this.service = service;
    }

    @PostMapping
    public Faculty addStudent(@RequestBody Faculty student) {
        return service.add(student);
    }

    @PutMapping
    public Faculty updStudent(@RequestBody Faculty student) {
        return service.upd(student);
    }

    @DeleteMapping("/{id}")
    public Faculty removeStudent(@PathVariable("id") long id) {
        return service.remove(id);
    }

    @GetMapping("/{id}")
    public Faculty findStudent(@PathVariable("id") long id) {
        return service.find(id);
    }

    @GetMapping
    public Collection<Faculty> getAll() {
        return service.getAll();
    }
}
