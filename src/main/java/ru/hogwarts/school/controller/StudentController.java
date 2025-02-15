package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.IStudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final IStudentService service;

    public StudentController(IStudentService service) {
        this.service = service;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return service.add(student);
    }

    @PutMapping
    public Student updStudent(@RequestBody Student student) {
        return service.upd(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeStudent(@PathVariable("id") long id) {
        service.remove(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudent(@PathVariable("id") long id) {
        Student result = service.find(id);
        if (result == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAll() {
        Collection<Student> result = service.getAll();
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(result);
        }

    }

    @GetMapping("/findByAge")
    public ResponseEntity<Collection<Student>> findByAge(@RequestParam("age") int age) {
        Collection<Student> result = service.findByAge(age);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }
}
