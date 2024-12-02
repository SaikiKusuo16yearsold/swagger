package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.FacultyCover;

import java.util.Optional;

public interface FacultyCoverRepository extends JpaRepository<FacultyCover, Integer> {
    Optional<FacultyCover> findByFacultyId(Integer facultyId);
}
