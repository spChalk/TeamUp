package com.example.socialnetworkingapp.model.bio;

import com.example.socialnetworkingapp.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.Part;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BioService {

    private final BioRepository bioRepository;

    public Bio findBioByAccountId(Long id) {
        Optional<Bio> bio = this.bioRepository.findBioByAccountId(id);
        return bio.orElse(null);
    }

    public Bio addBio(Bio bio) {
        return this.bioRepository.save(bio);
    }

    public Bio updateBio(Bio bio) {
        return this.bioRepository.save(bio);
    }

    public void deleteBioByAccountId(Long id) {
        this.bioRepository.deleteBioByAccountId(id);
    }
}