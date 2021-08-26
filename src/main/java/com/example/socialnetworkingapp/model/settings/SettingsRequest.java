package com.example.socialnetworkingapp.model.settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingsRequest {
    private String settingName;
    private String settingValue;
}