package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void saveFacultyTest() throws Exception {
        List<Student> facultyList = new ArrayList<>();


        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", "pufendui");
        facultyObject.put("color", "pink");

        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("pufendui");
        faculty.setColor("pink");
        faculty.setStudents(facultyList);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        when(facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase("pink", "pink")).thenReturn(List.of(faculty));
        when(facultyRepository.findByColorLike("pink")).thenReturn(List.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty").content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("pufendui"))
                .andExpect(jsonPath("$.color").value("pink"))
                .andExpect(jsonPath("$.students").value(facultyList));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/1").content(facultyObject.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("pufendui"))
                .andExpect(jsonPath("$.color").value("pink"))
                .andExpect(jsonPath("$.students").value(facultyList));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/find-faculties/pink")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("1")) // Ожидаем первый элемент в массиве
                .andExpect(jsonPath("$.[0].name").value("pufendui"))
                .andExpect(jsonPath("$.[0].color").value("pink"))
                .andExpect(jsonPath("$.[0].students").isArray());

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/faculty-id").param("facultyId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(facultyList));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/color/pink")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("1")) // Ожидаем первый элемент в массиве
                .andExpect(jsonPath("$.[0].name").value("pufendui"))
                .andExpect(jsonPath("$.[0].color").value("pink"))
                .andExpect(jsonPath("$.[0].students").isArray());
    }

//    @Test
//    public void StudentTest() throws Exception {
//
//        JSONObject studentObject = new JSONObject();
//        studentObject.put("name", "dima lucifer");
//        studentObject.put("age", "17");
//        studentObject.put("facultyId", "1");
//
//        List<Student> facultyList = new ArrayList<>();
//
//        Faculty faculty = new Faculty();
//        faculty.setId(1L);
//        faculty.setName("pufendui");
//        faculty.setColor("pink");
//        faculty.setStudents(facultyList);
//
//        Student student = new Student();
//        student.setId(1L);
//        student.setName("dima");
//        student.setAge(17L);
//        student.setFaculty(faculty);
//
//        when(studentRepository.save(any(Student.class))).thenReturn(student);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/student").content(studentObject.toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
}
