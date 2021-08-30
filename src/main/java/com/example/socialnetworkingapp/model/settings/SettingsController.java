package com.example.socialnetworkingapp.model.settings;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settings")
@AllArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping("/{id}")
    public Settings getSettingById(@PathVariable("id") Long id){
        return settingsService.findSettingById(id);
    }
}