package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.FacultyDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;


import java.util.List;


@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;


    @Autowired
    private StudentRepository studentRepository;


    public Faculty addFaculty(FacultyDTO facultyDTO) {
        Faculty faculty = new Faculty();
        List<Student> students = studentRepository.findByFacultyId(faculty.getId());
        faculty.setName(facultyDTO.getName());
        faculty.setColor(facultyDTO.getColor());
        faculty.setStudents(students);
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

    public List<Student> findByName(Long id) {
        return facultyRepository.findById(id).get().getStudents();
    }
}
