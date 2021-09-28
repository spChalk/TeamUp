package com.example.socialnetworkingapp.model.bio;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void deleteBioById(Long bioId) {
        this.bioRepository.deleteById(bioId);
    }
}