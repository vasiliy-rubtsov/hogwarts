package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
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
    public Faculty addFaculty(@RequestBody Faculty student) {
        return service.add(student);
    }

    @PutMapping
    public Faculty updFaculty(@RequestBody Faculty student) {
        return service.upd(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeFaculty(@PathVariable("id") long id) {
        service.remove(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> findFaculty(@PathVariable("id") long id) {
        Faculty result = service.find(id);
        if (result == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAll() {
        Collection<Faculty> result = service.getAll();
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/findByColor")
    public ResponseEntity<Collection<Faculty>> findByColor(@RequestParam("color") String color) {
        Collection<Faculty> result = service.findByColor(color);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

}
