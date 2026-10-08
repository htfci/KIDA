package dev.htfci.kidaapi;


import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileStorageService {
    public FileMetadata extractMetadata(MultipartFile file);
    public FileMetadata save(FileMetadata metadata);
    public InputStream getFile();
}

