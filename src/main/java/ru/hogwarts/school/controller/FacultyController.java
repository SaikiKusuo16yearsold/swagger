package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "faculty")
public class FacultyController {

    private FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }


    @GetMapping(path = "{color}")
    public ArrayList<Faculty> getFacultryByColor(String color) {
        return facultyService.filterFacultyByColor(color);
    }

    @GetMapping(path = "{id}")
    public Faculty getFacultyInfo(@PathVariable Long id) {
        return facultyService.getFacultyById(id);
    }

    @PutMapping
    public Faculty editFaculty(@RequestBody Faculty faculty) {
        return facultyService.updateInformationAboutFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public Faculty deleteBook(@PathVariable Long id) {
        return facultyService.deleteFaculty(id);
    }


}
