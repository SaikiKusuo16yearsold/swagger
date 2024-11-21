package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "student")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createFaculty(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping(path = "age/{age}")
    public ResponseEntity<List<Student>> studentsByAge(@PathVariable Long age) {
        List<Student> students = studentService.filterStudentsByAge(age);
        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(students);
    }


    @GetMapping(path = "{id}")
    public ResponseEntity<Student> getFacultyInfo(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<Student> editFaculty(@RequestBody Student student) {
        Student students = studentService.updateInformationAboutStudent(student);
        if (students == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteBook(@PathVariable Long id) {
        Student student = studentService.deleteStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
}
