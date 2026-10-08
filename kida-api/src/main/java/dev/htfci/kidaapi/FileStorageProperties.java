package dev.htfci.kidaapi;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;
import java.util.List;

@ConfigurationProperties(prefix = "kida.storage")
public record FileStorageProperties(Path uploadDir, List<String> allowedExtensions, List<String> allowedMimeTypes) {}
