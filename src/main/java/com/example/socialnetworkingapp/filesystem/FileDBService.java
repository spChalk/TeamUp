package com.example.socialnetworkingapp.filesystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class FileDBService {

    private FileDBRepository fileDBRepository;
    private AccountService accountService;

    public FileDB store(MultipartFile file, String ownerMail) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = new FileDB(fileName, file.getContentType(), ownerMail, file.getBytes());

        FileDB saved = fileDBRepository.save(fileDB);

        /* If the file is an image, add it to user's photo */
        if(saved.getType().contains("image")) {
            Account acc = accountService.findAccountByEmail(ownerMail);
            acc.setImageUrl("http://localhost:8081/api/files/" + saved.getId());
            accountService.updateAccount(acc);
        }
        return saved;
    }

    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    public String getFileIdByOwnerEmail(String email) {
        return fileDBRepository.findIdByOwnerEmail(email).getId();
    }
}