package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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

    /**
     * Добавить факультет
     * @throws Exception
     */
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

    /**
     * Получить факультет по ID
     * @throws Exception
     */
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(color))
                .andDo(print());
    }

    /**
     * Найти факультет по фрагменту имени или цвета
     */
    @Test
    public void findGlobalTest() throws Exception {
        long id = 1L;
        String name = "Гриффиндор";
        String color = "Красный";

        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);

        ArrayList<Faculty> allFaculties = new ArrayList<>();
        allFaculties.add(faculty);

        Mockito.when(facultyRepository.findGlobal(any(String.class))).thenReturn(allFaculties);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/faculty/findGlobal?str=Красный")
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].color").value(color));
    }

    /**
     * Найти факультет по точному значению цвета
     */
    @Test
    public void FindByColorTest() throws Exception {
        long id = 1L;
        String name = "Гриффиндор";
        String color = "Красный";

        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);

        ArrayList<Faculty> allFaculties = new ArrayList<>();
        allFaculties.add(faculty);

        Mockito.when(facultyRepository.findGlobal(any(String.class))).thenReturn(allFaculties);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
            .get("/faculty/findGlobal?str=Красный")
            .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].color").value(color));
    }

    /**
     * Обновить данные факультета
     */
    @Test
    public void updateFacultyTest() throws Exception {
        long id = 1L;
        String name = "Гриффиндор";
        String color = "Красный";

        Faculty faculty = new Faculty(name, color);
        faculty.setId(id);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name).put("color", color);

        Mockito.when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(color));
    }

    /**
     * Удалить факультет
     */
    @Test
    public void deleteFacultyTest() throws Exception {
        long id = 1L;

            Mockito.doAnswer(new Answer<Void>() {
                @Override
                public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                    return null;
                }
            }).when(facultyRepository).deleteById(any(Long.class));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/" + id));
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}
