package com.example.socialnetworkingapp.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountService;
import com.example.socialnetworkingapp.model.post.Post;
import com.example.socialnetworkingapp.model.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class FileDBService {

    private FileDBRepository fileDBRepository;
    private AccountService accountService;
    private PostService postService;

    public FileDB postStore(MultipartFile file, Long postId) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        this.accountService.findAccountByEmail(user);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = new FileDB(fileName, file.getContentType(), user, file.getBytes());
        FileDB saved = fileDBRepository.save(fileDB);
        String fileURL = "https://localhost:8443/api/files/" + saved.getId();

        /* The file is going to a post with id = postId. */
        if (postId != null) {

            Post p = this.postService.findPostById(postId);
            if(saved.getType().contains("image")) {
                p.setImagePath(fileURL);
            } else if(saved.getType().contains("video")) {
                p.setVideoPath(fileURL);
            } else if(saved.getType().contains("audio")) {
                p.setSoundPath(fileURL);
            }
            this.postService.updatePost(p);
        }
        return saved;
    }

    public FileDB userStore(MultipartFile file) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = new FileDB(fileName, file.getContentType(), user, file.getBytes());
        FileDB saved = fileDBRepository.save(fileDB);
        String fileURL = "https://localhost:8443/api/files/" + saved.getId();

        /* The file is an image and is going to a user profile. */
        if(saved.getType().contains("image")) {
            /* If the file is an image, add it to user's photo */
            Account acc = accountService.findAccountByEmail(user);
            acc.setImageUrl(fileURL);
            this.accountService.updateAccount(acc);
        }
        return saved;
    }

    public FileDB adminStore(MultipartFile file, String userEmail) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        FileDB fileDB = new FileDB(fileName, file.getContentType(), userEmail, file.getBytes());
        FileDB saved = fileDBRepository.save(fileDB);
        String fileURL = "https://localhost:8443/api/files/" + saved.getId();

        /* The file is an image and is going to a user profile. */
        if(saved.getType().contains("image")) {
            /* If the file is an image, add it to user's photo */
            Account acc = accountService.findAccountByEmail(userEmail);
            acc.setImageUrl(fileURL);
            this.accountService.updateAccount(acc);
        }
        return saved;
    }

    public FileDB storeBytes(byte[] file, String fileName, String cType) throws IOException {
        return fileDBRepository.save(new FileDB(fileName, cType, null, file));
    }

    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    public void moveFile(String source, String target) {
        File fileToMove = new File(source);
        fileToMove.renameTo(new File(target));
    }

    public Set<String> listDir(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                if (!Files.isDirectory(file)) {
                    fileList.add(file.getFileName().toString());
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return fileList;
    }

    public void fileCleanup() throws IOException {

        File XMLdirectory = new File("./exported_XML/");
        if (!XMLdirectory.exists()) { XMLdirectory.mkdir(); }
        File JSONdirectory = new File("./exported_JSON/");
        if (!JSONdirectory.exists()) { JSONdirectory.mkdir(); }

        /* Utility method to move all dangling files to the corresponding folders */
        Set<String> files = this.listDir(".");
        for (String filename: files) {
            if(filename.contains("EXP_")) {
                if (filename.contains(".xml"))
                    this.moveFile(filename, "./exported_XML/" + filename);
                if (filename.contains(".json"))
                    this.moveFile(filename, "./exported_JSON/" + filename);
            }
        }
    }

    public void deleteFile(String id) {
        this.fileDBRepository.deleteById(id);
    }
}