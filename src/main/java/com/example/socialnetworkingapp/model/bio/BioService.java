package com.example.socialnetworkingapp.model.bio;

import com.example.socialnetworkingapp.account.Account;
import com.example.socialnetworkingapp.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BioService {

    private final BioRepository bioRepository;

    public Bio findBioById(Long id) {
        return this.bioRepository.findBioById(id).
                orElseThrow( () -> new UserNotFoundException("User by id "+ id + "was not found !"));
    }

    public Bio addBio(Bio bio) {
        return this.bioRepository.save(bio);
    }

    public Bio updateBio(Bio bio) {
        return this.bioRepository.save(bio);
    }

    public void deleteBio(Long id) {
        bioRepository.deleteBioById(id);
    }
}