package com.example.socialnetworkingapp.model.settings;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SettingsRepository extends JpaRepository<AccountSettings, Long> {
}
