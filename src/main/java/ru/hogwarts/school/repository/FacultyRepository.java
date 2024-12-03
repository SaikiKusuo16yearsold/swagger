package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.List;
import java.util.Set;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColorLike(String color);

    List<Faculty> findByColorIgnoreCaseOrNameIgnoreCase(String color, String name);
}