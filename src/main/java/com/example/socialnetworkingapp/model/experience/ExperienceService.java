package com.example.socialnetworkingapp.model.experience;

import com.example.socialnetworkingapp.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    public List<Experience> findExperienceById(Long id) {
        return this.experienceRepository.findAllByUserId(id).orElseThrow( () -> new UserNotFoundException("User by id "+ id + "was not found !"));
    }

    public Experience addExperience(Experience experience) {
        return this.experienceRepository.save(experience);
    }

    public Experience updateExperience(Experience experience) {
        return this.experienceRepository.save(experience);
    }

    public void deleteExperience(Long id) {
        this.experienceRepository.deleteById(id);
    }

    public void deleteALlExperience(Long id) {
        this.experienceRepository.deleteByUserId(id);
    }
}