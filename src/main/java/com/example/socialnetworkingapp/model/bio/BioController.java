package com.example.socialnetworkingapp.model.bio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bio")
public class BioController {

    private final BioService bioService;

    @Autowired
    public BioController(BioService bioService) {
        this.bioService = bioService;
    }

    @GetMapping("/{id}")
    public String getBioById(@PathVariable("id") Long id){
        return bioService.findBioById(id).getDesc();
    }

    @PostMapping("/add")
    public ResponseEntity<Bio> addBio(@RequestBody Bio bio){
        Bio newBio = bioService.addBio(bio);
        return new ResponseEntity<>(newBio, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Bio> updateBio(@RequestBody Bio bio){
        Bio newBio = bioService.updateBio(bio);
        return new ResponseEntity<>(newBio, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBioById(@PathVariable("id") Long id){
        bioService.deleteBio(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}