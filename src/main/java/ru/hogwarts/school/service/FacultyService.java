package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.FacultyDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;


import java.util.Comparator;
import java.util.List;


@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;


    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);


    @Autowired
    private StudentRepository studentRepository;


    @Autowired
    private AvatarRepository avatarRepository;

    public Faculty addFaculty(FacultyDTO facultyDTO) {
        Faculty faculty = new Faculty();
        List<Student> students = studentRepository.findByFacultyId(faculty.getId());
        faculty.setId(facultyDTO.getId());
        faculty.setName(facultyDTO.getName());
        faculty.setColor(facultyDTO.getColor());
        faculty.setStudents(students);
        logger.info("Was invoked method for add faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        logger.info("Was invoked method for get faculty by id");
        return facultyRepository.findById(id).get();
    }


    //    @Transactional
    public void deleteFaculty(Long id) {
//        avatarRepository.deleteByFacultyId(id);
        logger.info("Was invoked method for delete faculty by id");
        facultyRepository.deleteById(id);
    }

    public List<Faculty> filterFacultyByColor(String color) {
        logger.info("Was invoked method for filter faculty by id");
        return facultyRepository.findByColorLike(color);
    }

    public List<Faculty> findByColorOrName(String colorOrName, String nameOrColor) {
        logger.info("Was invoked method for find faculty by color or name");
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(colorOrName, nameOrColor);
    }

    public List<Student> findByName(Long id) {
        logger.info("Was invoked method for find faculty by name");
        return facultyRepository.findById(id).get().getStudents();
    }


    public String getLongestNaming() {
        List<String> allFacultiesName = facultyRepository.findAllFacultyName();
        String longestName = allFacultiesName.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("Нет слов в списке");
        return longestName;
    }
}
