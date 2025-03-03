package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FacultyControllerV1Test {

    private static long id;

    @LocalServerPort
    private int port;

    @Autowired
    FacultyController controller;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    StudentController studentController;

    /**
     * Создался ли экземпляр контроллера со всеми включенными в него зависимостями?
     */
    @Test
    @Order(1)
    public void contextLoads() {
        Assertions.assertThat(controller).isNotNull();
    }

    /**
     * Добавление нового факультета
     */
    @Test
    @Order(2)
    public void addFaculty() {
        Faculty object = new Faculty("Тестовый факультет", "Фиолетовый");
        Faculty response = testRestTemplate.postForObject("http://localhost:" + port + "/faculty", object, Faculty.class);
        id = response.getId();
        Assertions.assertThat(id).isNotNull();
    }

    /**
     * Получение списка факультетов
     */
    @Test
    @Order(3)
    public void getAllFaculty() {
        Collection<Faculty> response = testRestTemplate.getForObject("http://localhost:" + port + "/faculty", Collection.class);
        Assertions.assertThat(response).isNotNull();
    }

    /**
     * Получение факултьтета по его ID
     */
    @Test
    @Order(4)
    public void getFaculty() {
        Faculty response = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/" + id, Faculty.class);
        Assertions.assertThat(response.getName()).isEqualTo("Тестовый факультет");
    }

    /**
     * Получение факультета по фрагменту цвета или названия
     */
    @Test
    @Order(5)
    public void findGlobal() {
        // Проверяем поиск по фрагменту названия
        ArrayList<LinkedHashMap<String, ?>> response = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/findGlobal?str=тест", ArrayList.class);
        Assertions.assertThat(response.size()).isGreaterThan(0);
        LinkedHashMap<String, ?> firstElement = response.get(0);
        Assertions.assertThat(firstElement.get("name")).isEqualTo("Тестовый факультет");

        // Проверяем поиск по фрагменту цвета
        response = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/findGlobal?str=тест", ArrayList.class);
        Assertions.assertThat(response.size()).isGreaterThan(0);
        firstElement = response.get(0);
        Assertions.assertThat(firstElement.get("name")).isEqualTo("Тестовый факультет");
    }

    /**
     * Получение факультета по точному значению цвета
     */
    @Test
    @Order(6)
    public void findByColor() {
        ArrayList<LinkedHashMap<String, ?>> response = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/findByColor?color=Фиолетовый", ArrayList.class);
        Assertions.assertThat(response.size()).isGreaterThan(0);
        LinkedHashMap<String, ?> firstElement = response.get(0);
        Assertions.assertThat(firstElement.get("name")).isEqualTo("Тестовый факультет");
    }

}
