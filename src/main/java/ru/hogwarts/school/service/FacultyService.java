package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;


import java.util.List;


@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;


    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).get();
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> filterFacultyByColor(String color) {
        return facultyRepository.findByColorLike(color);
    }

    public List<Faculty> findByColorOrName(String colorOrName, String nameOrColor) {
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(colorOrName, nameOrColor);
    }
}
