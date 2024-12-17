package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@RestController
public class InfoController {
    @Value("${server.port}")
    String port;

    @GetMapping(path = "get/port")
    public String getPort() {
        return port;
    }

    @GetMapping(path = "get-value")
    public long getValue() {
        long startTime = System.nanoTime();
        int parallelSum = IntStream.rangeClosed(1, 1_000_000)
                .sum();
        long endTime = System.nanoTime(); // Конец измерения времени в наносекундах
        long duration = endTime - startTime; // Разница во времени

        System.out.println("Время выполнения: " + duration + " наносекунд");
        return parallelSum;
    }
}
