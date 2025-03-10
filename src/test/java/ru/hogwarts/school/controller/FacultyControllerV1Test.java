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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

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
    public void addFacultyTest() {
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
    public void getAllFacultyTest() {
        Collection<Faculty> response = testRestTemplate.getForObject("http://localhost:" + port + "/faculty", Collection.class);
        Assertions.assertThat(response).isNotNull();
    }

    /**
     * Получение факултьтета по его ID
     */
    @Test
    @Order(4)
    public void getFacultyTest() {
        Faculty response = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/" + id, Faculty.class);
        Assertions.assertThat(response.getName()).isEqualTo("Тестовый факультет");
    }

    /**
     * Получение факультета по фрагменту цвета или названия
     */
    @Test
    @Order(5)
    public void findGlobalTest() {
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
    public void findByColorTest() {
        ArrayList<LinkedHashMap<String, ?>> response = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/findByColor?color=Фиолетовый", ArrayList.class);
        Assertions.assertThat(response.size()).isGreaterThan(0);
        LinkedHashMap<String, ?> firstElement = response.get(0);
        Assertions.assertThat(firstElement.get("name")).isEqualTo("Тестовый факультет");
    }

    /**
     * Обновление факультета
     */
    @Test
    @Order(7)
    public void updateFacultyTest() {
        Faculty faculty = new Faculty("Новый тестовый факультет", "синий");
        faculty.setId(id);

        HttpEntity<Faculty> entity = new HttpEntity<>(faculty);
        ResponseEntity<Faculty> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/faculty", HttpMethod.PUT, entity, Faculty.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody().getName()).isEqualTo("Новый тестовый факультет");
        Assertions.assertThat(responseEntity.getBody().getName()).isEqualTo("Новый тестовый факультет");
    }

    /**
     * удаление факультета
     */
    @Test
    @Order(7)
    public void deleteFacultyTest() {
        ResponseEntity<Void> responseEntity =  testRestTemplate.exchange("http://localhost:" + port + "/faculty/{id}", HttpMethod.DELETE, null,  Void.class, id);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
