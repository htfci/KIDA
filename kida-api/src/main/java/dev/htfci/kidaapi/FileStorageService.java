package dev.htfci.kidaapi;

import org.springframework.web.multipart.MultipartFile;


public interface FileStorageService {
    FileDto extract(MultipartFile file);
    FileDto save(MultipartFile file);
    String readContent(String filename);
}

