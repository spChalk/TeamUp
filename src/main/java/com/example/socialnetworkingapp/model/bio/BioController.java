package com.example.socialnetworkingapp.model.bio;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bio")
@AllArgsConstructor
public class BioController {

    private final BioService bioService;

    @GetMapping("/acc_ref/{id}")
    public ResponseEntity<Bio> getBioByAccountId(@PathVariable("id") Long id){
        return new ResponseEntity<>(bioService.findBioByAccountId(id), HttpStatus.OK);
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
    public ResponseEntity<?> deleteBioByAccountId(@PathVariable("id") Long id){
        bioService.deleteBioByAccountId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}