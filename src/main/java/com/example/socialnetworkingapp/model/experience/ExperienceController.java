package com.example.socialnetworkingapp.model.experience;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xp")
@AllArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping("/{id}")
    public List<Experience> getExperienceById(@PathVariable("id") Long id){
        return experienceService.findExperienceById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Experience> addExperience(@RequestBody Experience experience){
        Experience newXp = experienceService.addExperience(experience);
        return new ResponseEntity<>(newXp, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Experience> updateExperience(@RequestBody Experience experience){
        Experience newExperience = experienceService.updateExperience(experience);
        return new ResponseEntity<>(newExperience, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExperienceById(@PathVariable("id") Long id){
        experienceService.deleteExperience(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete_all/{id}")
    public ResponseEntity<?> deleteAllExperienceByUserId(@PathVariable("id") Long id){
        experienceService.deleteALlExperience(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}