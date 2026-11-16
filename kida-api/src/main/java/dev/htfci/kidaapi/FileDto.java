package dev.htfci.kidaapi;

import java.nio.file.Path;

public record FileDto(String sanitizedFilename, String secureFilename, String mimeType, long size, Path filePath) {}


