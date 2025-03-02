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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest
public class FacultyControllerV2Test {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private FacultyController facultycontroller;

    @Test
    public void addFacultyTest() throws Exception {
        long id = 1L;
        String name = "Гриффиндор";
        String color = "Красный";

        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name).put("color", color);

        Mockito.when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
            .post("/faculty")
            .content(facultyObject.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(color));
    }

    @Test
    public void getFacultyByIdTest() throws Exception {
        long id = 1L;
        String name = "Гриффиндор";
        String color = "Красный";

        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name).put("color", color);

        Mockito.when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(color));
    }

}
