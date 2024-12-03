    package ru.hogwarts.school.controller;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import ru.hogwarts.school.model.Faculty;
    import ru.hogwarts.school.model.Student;
    import ru.hogwarts.school.model.StudentDTO;
    import ru.hogwarts.school.repository.FacultyRepository;
    import ru.hogwarts.school.service.StudentService;

    import java.util.List;


    @RestController
    @RequestMapping(path = "student")
    public class StudentController {
        @Autowired

        private StudentService studentService;

        @Autowired
        private FacultyRepository facultyRepository;


        @PostMapping
        public Student createFaculty(@RequestBody StudentDTO studentDTO) {
            Faculty faculty = facultyRepository.findById(studentDTO.getFacultyId())
                    .orElseThrow(() -> new RuntimeException("Faculty not found")); // Обработка, если факультет не найден

            Student student = new Student();
            student.setName(studentDTO.getName());
            student.setAge(studentDTO.getAge());
            student.setFaculty(faculty);
            return studentService.addStudent(studentDTO);
        }

        @GetMapping(path = "students-by-age/{age}")
        public ResponseEntity<List<Student>> studentsByAge(@PathVariable Long age) {
            List<Student> students = studentService.filterStudentByAge(age);
            if (students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(students);
        }

        @GetMapping(path = {"filter-by-age/{minAge}/{maxAge}"})
        public ResponseEntity<List<Student>> findByAgeBetween(@PathVariable Long minAge, @PathVariable Long maxAge) {
            return ResponseEntity.ok(studentService.findByAgeBetween(minAge, maxAge));
        }


        public ResponseEntity<Student> getFacultyInfo(@PathVariable Long id) {
            Student student = studentService.getStudentById(id);
            if (student == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.ok(student);
        }

        @PutMapping
        public ResponseEntity<Student> editFaculty(@RequestBody StudentDTO studentDTO) {
            Student students = studentService.addStudent(studentDTO);
            if (students == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(students);
        }

        @DeleteMapping("{idi}")
        public ResponseEntity deleteBook(@PathVariable Long id) {
            studentService.deleteStudent(id);
            return ResponseEntity.ok(200);
        }

        @GetMapping(path = "faculty/{id}")
        public Faculty getFaculty(@PathVariable Long id) {
            return studentService.getFaculty(id);
        }

    }
