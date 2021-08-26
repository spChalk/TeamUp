package com.example.socialnetworkingapp.model.settings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SettingsResponse {
    private String settingName;
    private String settingValue;
    private String authorFirstName;
    private String authorLastName;
    private String authorEmail;
}