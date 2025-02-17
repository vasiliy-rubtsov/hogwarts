package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByColorIgnoreCase(String color);

    @Query(value = "SELECT * FROM faculty WHERE (UPPER(color) LIKE UPPER(CONCAT('%', :str, '%')) OR UPPER(name) LIKE UPPER(CONCAT('%', :str, '%')))", nativeQuery = true)
    Collection<Faculty> findGlobal(@Param("str") String str);

}
