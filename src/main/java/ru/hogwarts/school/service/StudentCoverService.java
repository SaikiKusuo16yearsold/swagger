package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;



@Service
@Transactional
public class StudentCoverService {
    @Value("${books.cover.dir.path}")
    private String coversDir;
    @Autowired
    private final FacultyService facultyService;
    private final AvatarRepository avatarRepository;


    public StudentCoverService(FacultyService facultyService, AvatarRepository avatarRepository) {
        this.facultyService = facultyService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadCover(Long facultyId, MultipartFile file) throws IOException {
        Faculty faculty = facultyService.getFacultyById(facultyId);

        Path filePath = Path.of(coversDir, facultyId + "." + file.getOriginalFilename());
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(inputStream, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(outputStream, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar facultyCover = findFacultyCover(facultyId);
        facultyCover.setFaculty(faculty);
        facultyCover.setFilePath(filePath.toString());
        facultyCover.setFileSize(file.getSize());
        facultyCover.setMediaType(file.getContentType());
        facultyCover.setPreview(generateImagePreview(filePath));

        avatarRepository.save(facultyCover);
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }


    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public Avatar findFacultyCover(Long facultyId) {
        if (avatarRepository.findByFacultyId(facultyId) != null) {
            return avatarRepository.findByFacultyId(facultyId);
        }
        return new Avatar();
    }
}
