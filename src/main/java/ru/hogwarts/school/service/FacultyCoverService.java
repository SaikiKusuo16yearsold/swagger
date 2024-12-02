package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.school.repository.FacultyCoverRepository;

@Service
@Transactional
public class FacultyCoverService {

    @Value("covers")
    private String covers;


    private final FacultyService facultyService;
    private final FacultyCoverRepository facultyCoverRepository;

    public FacultyCoverService(FacultyService facultyService, FacultyCoverRepository facultyCoverRepository) {
        this.facultyService = facultyService;
        this.facultyCoverRepository = facultyCoverRepository;
    }
}
