package ru.hogwarts.school.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;



@Service
@Transactional
public class StudentCoverService {
    @Value("${books.cover.dir.path}")
    private String coversDir;
    @Autowired
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;


    public StudentCoverService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadCover(Long studentId, MultipartFile file) throws IOException {
        Student student = studentService.getStudentById(studentId);

        Path filePath = Path.of(coversDir, studentId + "." + file.getOriginalFilename());
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(inputStream, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(outputStream, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar studentCover = findStudentCover(studentId);
        studentCover.setStudent(student);
        studentCover.setFilePath(filePath.toString());
        studentCover.setFileSize(file.getSize());
        studentCover.setMediaType(file.getContentType());
        studentCover.setPreview(generateImagePreview(filePath));

        avatarRepository.save(studentCover);
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

    public Avatar findStudentCover(Long studentId) {
        if (avatarRepository.findByStudentId(studentId) != null) {
            return avatarRepository.findByStudentId(studentId);
        }
        return new Avatar();
    }

    public List<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
