package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class StudentService implements IStudentService {

    private final StudentRepository repository;
    private Logger logger;

    public StudentService(StudentRepository repository, Logger logger) {
        this.repository = repository;
        this.logger = logger;
    }

    @Override
    public Student add(Student student) {
        logger.info("Was invoked method for create student {}", student);
        student.setId(null);
        return repository.save(student);
    }

    @Override
    public Student upd(Student student) {
        logger.info("Was invoked method for update student with ID = {}. New values: {}", student.getId(), student);
        return repository.save(student);
    }

    @Override
    public void remove(Long id) {
        logger.warn("Was invoked method for delete student with ID = {}", id);
        repository.deleteById(id);
    }

    @Override
    public Student find(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Collection<Student> getAll() {
        return repository.findAll();
    }

    @Override
    public Collection<Student> findByAge(int age) {
        return repository.findByAge(age);
    }
    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        return repository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty faculty(Long studentId) {
        Optional<Student> student = repository.findById(studentId);
        if (student.isEmpty()) {
            return null;
        }

        return student.get().getFaculty();
    }

    @Override
    public Integer getNumberOfStudents() {
        return this.repository.getNumberOfStudents();
    }

    @Override
    public Integer getAverageAge() {
        return this.repository.getAverageAge();
    }

    @Override
    public Collection<Student> getLast5Students() {
        return this.repository.getLast5Students();
    }

    @Override
    public List<String> getStudentNamesStartingWithLitera(String litera) {
        return this.repository
            .findAll()
            .stream()
            .map(v -> {
                // преобразуем поток студентов в поток имен с заглавной первой буквой
                String name = v.getName();
                return name.substring(0, 1).toUpperCase() + name.substring(1);
            })
            .filter(name -> name.startsWith(litera.toUpperCase())) // фильтруем по пераой букве
            .sorted() // упорядочиваем
            .collect(Collectors.toList());
    }

    @Override
    public Integer getAverageAgeV2() {
        int[] r = {0, 0};
        this.repository
            .findAll()
            .stream()
            .map(Student::getAge)
            .forEach(v -> {
                r[0] = r[0] + v; // Накапливаем сумму
                r[1]++; // Накапливаем количество
            });

        if (r[1] == 0) {
            return 0;
        } else {
            return (int) Math.round((float) r[0] / r[1]);
        }
    }

    private final Object flag = new Object();

    private void methodInThread(int threadNum, List<Student> students) {
        try {
            int index1;
            int index2;
            switch (threadNum) {
                case 0:
                    index1 = 0;
                    index2 = 1;
                    break;
                case 1:
                    index1 = 2;
                    index2 = 3;
                    break;
                default:
                    index1 = 4;
                    index2 = 5;
            }

            Thread.sleep(500); // Имитируем длительность выполнения операции
            System.out.println("Поток " + threadNum + ": " + index1 + " " + students.get(index1).getName());

            Thread.sleep(500);
            System.out.println("Поток " + threadNum + ": " + index2 + " " + students.get(index2).getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void printParallel() {
        List<Student> students = repository.findAll();

        methodInThread(0, students);

        new Thread(() -> {
            methodInThread(1, students);
        }).start();

        new Thread(() -> {
            methodInThread(2, students);
        }).start();
    }

    @Override
    public void printSynchronized() {
        List<Student> students = repository.findAll();

        synchronized (flag) {
            methodInThread(0, students);
        }

        new Thread(() -> {
            synchronized (flag) {
                methodInThread(1, students);
            }
        }).start();

        new Thread(() -> {
            synchronized (flag) {
                methodInThread(2, students);
            }
        }).start();

    }
}
