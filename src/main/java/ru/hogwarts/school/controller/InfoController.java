package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8082")
public class InfoController {
    @Value("${server.port}")
    String port;
    @GetMapping(path = "get/port")
    public String getPort() {
        return port;
    }
}
