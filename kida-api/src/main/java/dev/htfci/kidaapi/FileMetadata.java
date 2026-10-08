package dev.htfci.kidaapi;

import java.nio.file.Path;

public record FileMetadata(String name, String secureFilename, String mimeType, long size, Path targetStoragePath) {}
