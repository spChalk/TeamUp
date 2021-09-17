package com.example.socialnetworkingapp.model.experience;

import com.example.socialnetworkingapp.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    public Experience addExperience(Experience experience) {
        return this.experienceRepository.save(experience);
    }

    public Experience updateExperience(Experience experience) {
        return this.experienceRepository.save(experience);
    }

    public void deleteExperience(Long id) {
        this.experienceRepository.deleteById(id);
    }

    public Optional<Experience> findExperienceById(Long id) {
        return this.experienceRepository.findById(id);
    }

    public Experience setVisible(Experience experience) {
        Experience xp = this.experienceRepository.findById(experience.getId()).orElseThrow(
                () -> new IllegalStateException("Experience with id " + experience.getId().toString() + " does not exist!")
        );
        xp.setVisible(true);
        return this.experienceRepository.save(xp);
    }

    public Experience hide(Experience experience) {
        Experience xp = this.experienceRepository.findById(experience.getId()).orElseThrow(
                () -> new IllegalStateException("Experience with id " + experience.getId().toString() + " does not exist!")
        );
        xp.setVisible(false);
        return this.experienceRepository.save(xp);
    }
}