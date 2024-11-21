package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;

import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class StudentService {
    private Map<Long, Student> students = new HashMap<>();
    private Long counter = 1L;

    public Student addStudent(Student student) {
        student.setId(counter);
        students.put(counter, student);
        counter++;
        return student;
    }

    public Student getStudentById(Long id) {
        return students.get(id);
    }

    public Student updateInformationAboutStudent(Student student) {
        return students.put(student.getId(), student);
    }

    public Student deleteStudent(Long id) {
        return students.remove(id);
    }

    public List<Student> filterStudentsByAge(Long age) {
        // Применяем фильтр по возрасту и собираем результат в ArrayList
        List<Student> listStudents = students.values()
                .stream().filter(s -> s.getAge().equals(age))
                .toList();

        return listStudents;
    }
}
