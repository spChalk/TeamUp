package com.example.socialnetworkingapp.filesystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file-system")
public class FileSystemImageController {

    private final FileLocationService fileLocationService;

    @Autowired
    public FileSystemImageController(FileLocationService fileLocationService) {
        this.fileLocationService = fileLocationService;
    }

    @PostMapping("/image")
    public Long uploadImage(@RequestParam MultipartFile image) throws Exception {
        return fileLocationService.save(image.getBytes(), image.getOriginalFilename());
    }

    @GetMapping(value = "/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public FileSystemResource downloadImage(@PathVariable Long imageId) throws Exception {
        return fileLocationService.find(imageId);
    }
}