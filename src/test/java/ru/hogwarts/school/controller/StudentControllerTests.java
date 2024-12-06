package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentCoverService;
import ru.hogwarts.school.service.StudentService;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private StudentCoverService studentCoverService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void StudentTest() throws Exception {

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", "dima lucifer");
        studentObject.put("age", "17");
        studentObject.put("facultyId", "1");

        List<Student> facultyList = new ArrayList<>();

        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("pufendui");
        faculty.setColor("pink");
        faculty.setStudents(facultyList);

        Student student = new Student();
        student.setId(1L);
        student.setName("dima");
        student.setAge(17L);
        student.setFaculty(faculty);

        when(facultyRepository.findById(1L)).thenReturn(java.util.Optional.of(faculty));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findByAge(1L)).thenReturn(List.of(student));
        when(studentRepository.findByAgeBetween(1L, 19L)).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Измените на .isCreated() если// ожидается статус 201


        mockMvc.perform(MockMvcRequestBuilders.get("/student/students-by-age/1")
//                        .content(studentObject.toString())
//                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Измените на .isCreated() если ожидается статус 201

//        mockMvc.perform(MockMvcRequestBuilders.get("/student/students-by-agu/1/19").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/student/faculty/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/student/students-by-age/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


        MockMultipartFile mockFile = new MockMultipartFile(
                "file",                         // Имя параметра
                "faculty.png",                  // Имя файла
                MediaType.IMAGE_PNG_VALUE,      // Тип содержимого
                new FileInputStream("path/to/faculty.png") // Содержимое файла
        );

//        mockMvc.perform(MockMvcRequestBuilders.multipart("/faculty")
//                        .file(mockFile) // Передаем файл
//                        .accept(MediaType.APPLICATION_JSON)) // Указываем, что ожидаем JSON-ответ
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value("1"))
//                .andExpect(jsonPath("$.name").value("pufendui"))
//                .andExpect(jsonPath("$.color").value("pink"));
    }
}
