package com.example.socialnetworkingapp.filesystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import javax.persistence.Access;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

@Repository
public class FileSysRepository {

    private final String RESOURCES_DIR;

    @Autowired
    public FileSysRepository() {
        this.RESOURCES_DIR = Objects.requireNonNull(FileSysRepository.class.getResource("/")).getPath();
    }

    public String save(byte[] content, String imageName) throws Exception {
        Path newFile = Paths.get(RESOURCES_DIR + new Date().getTime() + "-" + imageName);
        Files.createDirectories(newFile.getParent());

        Files.write(newFile, content);

        return newFile.toAbsolutePath()
                .toString();
    }

    public FileSystemResource findInFileSystem(String location) {
        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            // Handle access or file not found problems.
            throw new RuntimeException();
        }
    }
}