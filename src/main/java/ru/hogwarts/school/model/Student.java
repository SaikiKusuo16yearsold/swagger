package ru.hogwarts.school.model;

import jakarta.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long age;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty; // Это поле сохраняет связь с объектом Faculty

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Faculty getFaculty() {
        return faculty; // Метод для получения объекта Faculty
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty; // Метод для установки объекта Faculty
    }
}

