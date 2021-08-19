package com.example.socialnetworkingapp.filesystem;

import com.example.socialnetworkingapp.image.ImageRepository;
import com.example.socialnetworkingapp.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileLocationService {

    private final FileSysRepository fileSystemRepository;
    private final ImageRepository imageDbRepository;

    @Autowired
    public FileLocationService(FileSysRepository fileSystemRepository, ImageRepository imageDbRepository) {
        this.fileSystemRepository = fileSystemRepository;
        this.imageDbRepository = imageDbRepository;
    }

    public Long save(byte[] bytes, String imageName) throws Exception {
        String location = fileSystemRepository.save(bytes, imageName);

        return imageDbRepository.save(new Image(imageName, location))
                .getId();
    }

    public FileSystemResource find(Long imageId) {
        Image image = imageDbRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return fileSystemRepository.findInFileSystem(image.getLocation());
    }
}