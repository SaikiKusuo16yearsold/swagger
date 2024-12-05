package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.FacultyDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping(path = "faculty")
public class FacultyController {
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private FacultyRepository facultyRepository;

    @PostMapping
    public Faculty createFaculty(@RequestBody FacultyDTO facultyDTO) {
        return facultyService.addFaculty(facultyDTO);
    }


    @GetMapping(path = "color/{color}")
    public List<Faculty> getFacultyByColor(@PathVariable String color) {
        return facultyService.filterFacultyByColor(color);
    }

    @GetMapping(path = "/{id}")
    public Faculty getFacultyInfo(@PathVariable Long id) {
        return facultyService.getFacultyById(id);
    }

    @PutMapping
    public Faculty editFaculty(@RequestBody FacultyDTO faculty) {
        return facultyService.addFaculty(faculty);
    }

    @GetMapping(path = "find-faculties/{nameOrColor}")
    public List<Faculty> findByColorOrName(@PathVariable String nameOrColor) {
        return facultyService.findByColorOrName(nameOrColor, nameOrColor);
    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping("/faculty-id")
    public List<Student> getStudent(@RequestParam Long facultyId) {
        return facultyService.findByName(facultyId);

    }

    @GetMapping("/faculties")
    public List<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }

    @GetMapping
    public String getFaculty() {
        return "web app is work ";
    }


}
