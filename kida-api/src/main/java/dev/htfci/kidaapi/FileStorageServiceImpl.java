package dev.htfci.kidaapi;

import jakarta.annotation.PostConstruct;
import org.apache.tika.Tika;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Tika tika = new Tika();
    private final FileStorageProperties properties;
    private final Path storageLocation;


    public FileStorageServiceImpl(FileStorageProperties fsp) {
        this.properties = fsp;
        this.storageLocation = properties.uploadDir().toAbsolutePath().normalize();
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(storageLocation);
            System.out.println("Uploads directory location: " + storageLocation);
        } catch (IOException ex) {
            throw new IllegalStateException("Could not create upload directory: " + storageLocation, ex);
        }
    }

    @Override
    public FileMetadata extractMetadata(MultipartFile file) {
        Objects.requireNonNull(file, "File must not be null.");

        if (!StringUtils.hasText(file.getOriginalFilename())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filename.");
        }
        var sanitizedFilename = Path.of(file.getOriginalFilename()).getFileName().toString();

        int dotIndex = sanitizedFilename.lastIndexOf('.');
        if (dotIndex <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported file: files without an extension (e.g., README) or dotfiles (e.g., .gitignore) are not accepted.");
        }
        String extension = sanitizedFilename.substring(dotIndex + 1).toLowerCase();

        if (!properties.allowedExtensions().contains(extension)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "File extension: '." + extension + "' is not allowed. Please upload one of the following: " + String.join(", ", properties.allowedExtensions()));
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
        String secureFilename = uuid.concat(".").concat(extension);
        Path storagePath = storageLocation.resolve(secureFilename);

        try (InputStream in = file.getInputStream()) {
            var bytesRead = Files.copy(in, storagePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new UncheckedIOException("Failed to save file content.", ex);
        }
        return new FileMetadata(sanitizedFilename, secureFilename, detectedMimeType, file.getSize(), storagePath);
    }

    @Override
    public FileMetadata save(FileMetadata metadata) {
        return null;
    }

    @Override
    public InputStream getFile() {
        return null;
    }
}
