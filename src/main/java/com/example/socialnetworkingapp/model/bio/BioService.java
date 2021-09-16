package com.example.socialnetworkingapp.model.bio;

import com.example.socialnetworkingapp.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.Part;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BioService {

    private final BioRepository bioRepository;

    public Bio addBio(Bio bio) {
        return this.bioRepository.save(bio);
    }

    public Bio updateBio(Bio bio) {
        return this.bioRepository.save(bio);
    }

    public void deleteBio(Bio bio) {
        this.bioRepository.delete(bio);
    }
}