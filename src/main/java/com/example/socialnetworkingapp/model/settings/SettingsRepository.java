package com.example.socialnetworkingapp.model.settings;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<AccountSettings, Long> {


}
