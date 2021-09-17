package com.example.socialnetworkingapp.model.education;

import com.example.socialnetworkingapp.model.experience.Experience;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/education")
@AllArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @PostMapping("/add")
    public ResponseEntity<Education> addEducation(@RequestBody Education education){
        Education newEd = educationService.addEducation(education);
        return new ResponseEntity<>(newEd, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Education> updateEducation(@RequestBody Education education){
        Education newEducation = educationService.updateEducation(education);
        return new ResponseEntity<>(newEducation, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEducationById(@PathVariable("id") Long id){
        educationService.deleteEducation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/show")
    public ResponseEntity<Education> setEducationVisibility(@RequestBody Education education) {
        return new ResponseEntity<>(this.educationService.setVisible(education), HttpStatus.OK);
    }

    @PostMapping("/hide")
    public ResponseEntity<Education> hideEducation(@RequestBody Education education) {
        return new ResponseEntity<>(this.educationService.hide(education), HttpStatus.OK);
    }
}