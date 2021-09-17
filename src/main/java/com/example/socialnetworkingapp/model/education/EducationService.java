package com.example.socialnetworkingapp.model.education;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;

    public Education addEducation(Education education) {
        return this.educationRepository.save(education);
    }

    public Education updateEducation(Education education) {
        return this.educationRepository.save(education);
    }

    public void deleteEducation(Long id) {
        this.educationRepository.deleteById(id);
    }

    public Optional<Education> findEducationById(Long id) {
        return this.educationRepository.findById(id);
    }

    public Education setVisible(Education education) {
        Education ed = this.educationRepository.findById(education.getId()).orElseThrow(
                () -> new IllegalStateException("Education with id " + education.getId().toString() + " does not exist!")
        );
        ed.setVisible(true);
        return this.educationRepository.save(ed);
    }

    public Education hide(Education education) {
        Education ed = this.educationRepository.findById(education.getId()).orElseThrow(
                () -> new IllegalStateException("Education with id " + education.getId().toString() + " does not exist!")
        );
        ed.setVisible(false);
        return this.educationRepository.save(ed);
    }
}