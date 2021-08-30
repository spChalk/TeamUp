package com.example.socialnetworkingapp.model.settings;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SettingsService {

    private final SettingsRepository settingsRepository;

    public Settings findSettingById(Long id) {
        return this.settingsRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("Setting with id: " + id + " does not exist!"));
    }
}