package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest
public class StudentControllerV2Test {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void addStudentTest() throws Exception {
        long id = 1L;
        String name = "Гарри Поттер";
        int age = 11;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name).put("age", age);

        Student student = new Student(name, age);
        student.setId(id);

        Mockito.when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(
            MockMvcRequestBuilders
            .post("/student")
            .content(studentObject.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age));

    }

    @Test
    public void findStudentByIdTest() throws Exception {
        long id = 1L;
        String name = "Гарри Поттер";
        int age = 11;

        Student student = new Student(name, age);
        student.setId(id);

        Mockito.when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/student/" + id)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
        .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(age));
    }

}
