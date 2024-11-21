package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long counter = 1L;

    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(counter);
        faculties.put(counter, faculty);
        counter++;
        return faculty;
    }

    public Faculty getFacultyById(Long id) {
        return faculties.get(id);
    }

    public Faculty updateInformationAboutFaculty(Faculty faculty) {
        return faculties.put(faculty.getId(), faculty);
    }

    public Faculty deleteFaculty(Long id) {
        return faculties.remove(id);
    }

    public List<Faculty> filterFacultyByColor(String color) {
        List<Faculty> listFaculty = faculties.values()
                .stream().filter(s -> s.getColor().equals(color))
                .toList();

        return listFaculty;
    }
}
