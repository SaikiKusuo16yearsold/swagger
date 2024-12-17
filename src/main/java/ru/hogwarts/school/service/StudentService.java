package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.StudentDTO;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private AvatarRepository avatarRepository;

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student addStudent(StudentDTO studentDTO) {
        logger.info("Was invoked method for add student");
        Faculty faculty = facultyRepository.findById(studentDTO.getFacultyId())
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setAge(studentDTO.getAge());
        student.setFaculty(faculty);
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        logger.info("Was invoked method for get student by id");
        return studentRepository.findById(id).get();
    }

    @Transactional
    public void deleteStudent(Long id) {
        logger.info("Was invoked method for delete student");
        avatarRepository.deleteByStudentId(id);
        studentRepository.deleteById(id);
    }

    public List<Student> filterStudentByAge(Long age) {
        logger.info("Was invoked method for filter student ny age");
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween(Long minAge, Long maxAge) {
        logger.info("Was invoked method for find student by age between");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty getFaculty(Long id) {
        logger.info("Was invoked method for getFaculty by id");
        return studentRepository.findById(id).get().getFaculty();
    }


    public int counterAllStudents() {
        logger.info("Was invoked method for counter all students");
        return studentRepository.counterAllStudents();
    }

    public int getAverageAge() {
        logger.info("Was invoked method for get average age");
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students");
        return studentRepository.getLastFiveStudents();
    }

    public Long getMiddleAge() {
        List<Student> allStudents = studentRepository.findAll();
        Long sum = allStudents.stream().mapToLong(Student::getAge).sum();
        return sum / allStudents.size();
    }

    public List<String> getStudentsByAlphabet() {
        List<Student> students = studentRepository.findAll();
        List<String> studentsName = students.parallelStream() // Используем параллельный поток
                .map(Student::getName) // Получаем имена студентов
                .filter(name -> name.startsWith("A")) // Фильтруем по первой букве
                .map(String::toUpperCase) // Преобразуем в верхний регистр
                .sorted() // Сортируем в алфавитном порядке
                .collect(Collectors.toList());
        return studentsName;
    }

    public void printSynchronized() {
        List<Student> students = studentRepository.findAll();
        print(0);
        print(1);

        new Thread(() -> {
            print(2);
            print(3);
        }).start();

        new Thread(() -> {
            print(4);
            print(5);
        }).start();
    }

    public Object flag = new Object();

    public void printParallel() {
        List<Student> students = studentRepository.findAll();
        System.out.println(students.get(0));
        System.out.println(students.get(1).toString());

        new Thread(() -> {
            System.out.println(students.get(2).toString());
            System.out.println(students.get(3).toString());
        }).start();

        new Thread(() -> {
            System.out.println(students.get(4).toString());
            System.out.println(students.get(5).toString());
        }).start();

    }

    private void print(int index) {
        List<Student> students = studentRepository.findAll();
        synchronized (flag) {
            System.out.println(students.get(index).toString());
        }
    }

}
