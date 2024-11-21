package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;

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

    @GetMapping(path = "{age}")
    public ArrayList<Student> studentsByAge(@PathVariable Long age) {
        return studentService.filterStudentsByAge(age);
    }


    @GetMapping(path = "{id}")
    public Student getFacultyInfo(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping
    public Student editFaculty(@RequestBody Student student) {
        return studentService.updateInformationAboutStudent(student);
    }

    @DeleteMapping("{id}")
    public Student deleteBook(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }
}
