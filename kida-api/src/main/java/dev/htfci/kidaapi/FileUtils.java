package dev.htfci.kidaapi;

import java.nio.file.Path;

public final class FileUtils {


    private FileUtils() {
        throw new AssertionError("Utility class cannot be instantiated");
    }


    public static String sanitize(String originalFilename) {
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new IllegalArgumentException("Invalid filename");
        }
        Path filename = Path.of(originalFilename).getFileName();
        if (filename == null) {
            throw new IllegalArgumentException("Filename represents a relative directory or root: " + originalFilename);
        }
        return filename.toString();
    }

}
