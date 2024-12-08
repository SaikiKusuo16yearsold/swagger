package ru.hogwarts.school.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Avatar;

import java.util.List;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Avatar findByStudentId(Long id);

    void deleteByStudentId(Long id);
}
