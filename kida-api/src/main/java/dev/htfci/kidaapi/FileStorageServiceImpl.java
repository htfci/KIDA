package dev.htfci.kidaapi;

import jakarta.annotation.PostConstruct;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Tika tika = new Tika();
    private final FileStorageProperties properties;
    private final Path dirPath;


    public FileStorageServiceImpl(FileStorageProperties properties) {
        this.properties = properties;
        this.dirPath = properties.uploadDir().toAbsolutePath().normalize();
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(dirPath);
            System.out.println("Uploads directory path: " + dirPath);
        } catch (IOException ex) {
            throw new IllegalStateException("Could not create upload directory: " + dirPath, ex);
        }
    }

    @Override
    public FileDto extract(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or empty file.");
        }
        String sanitizedFilename = FileUtils.sanitize(file.getOriginalFilename());

        int dotIndex = sanitizedFilename.lastIndexOf('.');
        if (dotIndex <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported file: files without an extension (e.g., README) or dotfiles (e.g., .gitignore) are not accepted.");
        }
        String fileExtension = sanitizedFilename.substring(dotIndex + 1).toLowerCase();

        if (!properties.allowedExtensions().contains(fileExtension)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "File extension: '." + fileExtension + "' is not allowed. Please upload one of the following: " + String.join(", ", properties.allowedExtensions()));
        }

        String detectedMimeType;
        try (InputStream in = file.getInputStream()) {
            detectedMimeType = tika.detect(in, sanitizedFilename);
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT, "Could not inspect file contents. The file may be corrupted.");
        }

        if (!properties.allowedMimeTypes().contains(detectedMimeType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File content type not allowed: " + detectedMimeType);
        }

        String uuid = UUID.randomUUID().toString();
        String secureFilename = uuid.concat(".").concat(fileExtension);
        Path storagePath = dirPath.resolve(secureFilename);

        try (InputStream in = file.getInputStream()) {
            Files.copy(in, storagePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new UncheckedIOException("Failed to save file content.", ex);
        }
        return new FileDto(sanitizedFilename, secureFilename, detectedMimeType, file.getSize(), storagePath);
    }

    // todo: save to db instead of FileDto
    @Override
    public FileDto save(MultipartFile file) {
        Objects.requireNonNull(file);
        return extract(file);
    }

    @Override
    public String readContent(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filename cannot be null or blank");
        }
        Path file = dirPath.resolve(filename).normalize();

        try {
            return tika.parseToString(file);
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not parse text from file", ex);
        } catch (TikaException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT, "Could not parse text from file", ex);
        }
    }
}
