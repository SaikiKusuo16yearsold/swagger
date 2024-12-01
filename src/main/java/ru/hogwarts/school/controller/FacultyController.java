package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "faculty")
public class FacultyController {
    @Autowired
    private FacultyService facultyService;


    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
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
    public Faculty editFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @GetMapping(path = "find-faculties/{nameOrColor}")
    public List<Faculty> findByColorOrName(@PathVariable String nameOrColor){
        return facultyService.findByColorOrName(nameOrColor, nameOrColor);
    }

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }


}
