package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.StudentDTO;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.StudentCoverService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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

    @DeleteMapping("{id}")
    public ResponseEntity deleteBook(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(200);
    }

    @GetMapping(path = "faculty/{id}")
    public Faculty getFaculty(@PathVariable Long id) {
        return studentService.getFaculty(id);
    }

    @Autowired
    StudentCoverService studentCoverService;


    @PostMapping(value = "/{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCover(@PathVariable Long id, @RequestParam MultipartFile cover) throws IOException {
        if (cover.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("file is to big");
        }
        studentCoverService.uploadCover(id, cover);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/cover/preview")
    public ResponseEntity<byte[]> downoloadCover(@PathVariable Long id) {
        Avatar studentCover = studentCoverService.findStudentCover(id);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(studentCover.getMediaType()));
        headers.setContentLength(studentCover.getPreview().length);

        return ResponseEntity.ok().headers(headers).body(studentCover.getPreview());
    }

    @GetMapping(value = "/{id}/cover")
    public void downoloadCover(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar studentCover = studentCoverService.findStudentCover(id);

        Path path = Path.of(studentCover.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(studentCover.getMediaType());
            response.setContentLength((int) studentCover.getFileSize());
            is.transferTo(os);
        }
    }


    @GetMapping(value = "all")
    public int countAllStudent() {
        return studentService.counterAllStudents();
    }

    @GetMapping(value = "average-age")
    public int getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping(value = "last-five-students")
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("/id")
    public ResponseEntity<List<Avatar>> getAllAvatars(@RequestParam("page") Integer pageNumber,
                                                      @RequestParam("size") Integer pageSize){
        List<Avatar> avatars = studentCoverService.getAllAvatars(pageNumber, pageSize);
        return ResponseEntity.ok(avatars);
    }

}
