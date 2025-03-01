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

import java.util.Collection;

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

    @Test
    @Order(1)
    public void contextLoads() {
        Assertions.assertThat(controller).isNotNull();
    }

    @Test
    @Order(2)
    public void addFaculty() {
        Faculty object = new Faculty("Тестовый факультет", "Фиолетовый");
        Faculty response = testRestTemplate.postForObject("http://localhost:" + port + "/faculty", object, Faculty.class);
        id = response.getId();
        Assertions.assertThat(id).isNotNull();
    }

    @Test
    @Order(3)
    public void getAllFaculty() {
        Collection<Faculty> response = testRestTemplate.getForObject("http://localhost:" + port + "/faculty", Collection.class);
        Assertions.assertThat(response).isNotNull();
    }

    @Test
    @Order(4)
    public void getFaculty() {
        Faculty response = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/" + id, Faculty.class);
        Assertions.assertThat(response.getName()).isEqualTo("Тестовый факультет");
    }
}
