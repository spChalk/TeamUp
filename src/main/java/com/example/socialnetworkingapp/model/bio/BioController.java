package com.example.socialnetworkingapp.model.bio;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bio")
@AllArgsConstructor
public class BioController {

    private final BioService bioService;

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

    @DeleteMapping("/delete/{bioId}")
    public ResponseEntity<HttpStatus> deleteBio(@PathVariable("bioId") Long bioId) {
        this.bioService.deleteBioById(bioId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}