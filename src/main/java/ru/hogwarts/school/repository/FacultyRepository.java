package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColorLike(String color);

    List<Faculty> findByColorIgnoreCaseOrNameIgnoreCase(String color, String name);

    @Query("SELECT f.name FROM Faculty f")
    List<String> findAllFacultyName();


}