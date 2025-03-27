package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FacultyService implements IFacultyService {

    private final FacultyRepository repository;
    private Logger logger;

    public FacultyService(FacultyRepository repository, Logger logger) {
        this.repository = repository;
        this.logger = logger;
    }

    @Override
    public Faculty add(Faculty faculty) {
        logger.info("Was invoked method for create faculty {}", faculty);
        faculty.setId(null);
        return repository.save(faculty);
    }

    @Override
    public Faculty upd(Faculty faculty) {
        logger.info("Was invoked method for update faculty with ID = {}. New values: {}", faculty.getId(), faculty);
        return repository.save(faculty);
    }

    @Override
    public void remove(Long id) {
        logger.warn("Was invoked method for delete faculty with ID = {}", id);
        repository.deleteById(id);
    }

    @Override
    public Faculty find(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Collection<Faculty> getAll() {
        return repository.findAll();
    }

    @Override
    public Collection<Faculty> findByColor(String color) {
        return repository.findByColorIgnoreCase(color);
    }

    @Override
    public Collection<Faculty> findGlobal(String str) {
        return repository.findGlobal(str);
    }

    @Override
    public Collection<Student> students(Long facultyId) {
        Optional<Faculty> faculty = repository.findById(facultyId);
        if (faculty.isEmpty()) {
            return null;
        }

        return faculty.get().getStudents();
    }

    @Override
    public String getLongestName() {

        Optional<String> longestName = this.repository
            .findAll()
            .stream()
            .map(Faculty::getName)
            .reduce((name1, name2) -> name1.length() > name2.length() ? name1 : name2);

        return longestName.orElse("");
    }

    /**
     * Расчет суммы без использования параллельных потоков
     */
    private Integer getSummV1() {
        int sum = Stream.iterate(1, a -> a +1) .limit(1_000_000).reduce(0, (a, b) -> a + b );
        return sum;
    }

    /**
     * Расчет суммы c использованием параллельных потоков
     */
    private Integer getSummV2() {
        int sum = Stream.iterate(1, a -> a +1).limit(1_000_000).parallel().reduce(0, (a, b) -> a + b );
        return sum;
    }

    @Override
    public Integer getSumm(Integer variant) {
        long t1 = System.currentTimeMillis();
        logger.info("(Вариант {}) Начало вычисление суммы всех чисел от 1 до 1000000", variant);

        // Само вычисление суммы
        int sum;
        if (variant == 1) {
            // Вариант 1 - без использования параллельных потоков
            sum = getSummV1();
        } else {
            // Вариант 2 - с использованием параллельных потоков
            sum = getSummV2();
        }

        long t2 = System.currentTimeMillis();
        long delta = t2 - t1;
        logger.info("(Вариант {}) Закончено вычисление суммы всех чисел от 1 до 1000000. На это потребовалось {} мс", variant, delta);
        return sum;
    }
}

