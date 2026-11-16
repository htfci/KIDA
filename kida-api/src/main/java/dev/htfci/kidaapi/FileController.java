package dev.htfci.kidaapi;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    private final FileStorageService fileStorageService;


    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }


    @RequestMapping(method = RequestMethod.POST, path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> save(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is null or empty.");
        }
        // todo: Save metadata to DB
        FileDto savedFile = fileStorageService.save(file);
        System.out.println(savedFile);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(savedFile.secureFilename()).toUri();
        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<String> getBy(UUID fileId) {
        return null;
    }

    public ResponseEntity<String> deleteBy(UUID uuid) {
        return null;
    }
}
