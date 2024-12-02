package ru.hogwarts.school.model;

import jakarta.persistence.*;

@Entity
public class FacultyCover {
    @Id
    @GeneratedValue
    private Long Id;

    private String filepath;

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public byte[] getPreview() {
        return preview;
    }

    public void setPreview(byte[] preview) {
        this.preview = preview;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Faculty getStudent() {
        return faculty;
    }

    public void setStudent(Faculty student) {
        this.faculty = student;
    }

    private Long filesize;
    private String mediaType;

    @Lob
    private byte[] preview;
    @OneToOne

    public Faculty faculty;
}
