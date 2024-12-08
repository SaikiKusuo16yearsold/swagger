package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import ru.hogwarts.school.model.Faculty;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(Long age);

    List<Student> findByAgeBetween(Long from, Long to);

    List<Student> findByFacultyId(Long facultyId);

    @Query(value = "SELECT Count(*) FROM student ", nativeQuery = true)
    int counterAllStudents();

    @Query(value = "SELECT Avg(age) FROM student ", nativeQuery = true)
    int getAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}