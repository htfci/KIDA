package dev.htfci.kidaapi;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    private final FileStorageService fileStorageService;


    public FileController(FileStorageService fss) {
        this.fileStorageService = fss;
    }


    @RequestMapping(method = RequestMethod.POST, path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> save(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid or empty file.");
        }
        FileMetadata metadata = fileStorageService.extractMetadata(file);
        System.out.println(metadata);

        // Save metadata to DB (LATER!)

        // Send content to AI Service (aiServiceClient.extractDocument(metadata))

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(metadata.secureFilename())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
