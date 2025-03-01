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
import ru.hogwarts.school.model.Student;

import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentControllerV1Test {

    private static long id;

    @LocalServerPort
    private int port;

    @Autowired
    StudentController controller;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    @Order(1)
    public void contextLoads() {
        Assertions.assertThat(controller).isNotNull();
    }

    @Test
    @Order(2)
    public void addStudent() {
        Student object = new Student("Тестовый студент", 15);
        Student response = testRestTemplate.postForObject("http://localhost:" + port + "/student", object, Student.class);
        id = response.getId();
        Assertions.assertThat(id).isNotNull();
    }

    @Test
    @Order(3)
    public void getAllStudents() {
        Collection<Student> response = testRestTemplate.getForObject("http://localhost:" + port + "/student", Collection.class);
        Assertions.assertThat(response).isNotNull();
    }

    @Test
    @Order(4)
    public void getStudent() {
        Student response = testRestTemplate.getForObject("http://localhost:" + port + "/student/" + id, Student.class);
        Assertions.assertThat(response.getName()).isEqualTo("Тестовый студент");
    }
}
