package com.example.socialnetworkingapp.mapper;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.settings.AccountSettings;
import com.example.socialnetworkingapp.model.settings.SettingsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SettingsMapper {

    @Mapping(target = "authorFirstName", expression = "java(mapFirstName(accountSettings.getUser()))")
    @Mapping(target = "authorLastName", expression = "java(mapLastName(accountSettings.getUser()))")
    @Mapping(target = "authorEmail", expression = "java(mapEmail(accountSettings.getUser()))")
    SettingsResponse SettingsToSettingsResponse(AccountSettings accountSettings);

    default String mapFirstName(Account account){
        return account.getFirstName();
    }
    default String mapLastName(Account account){
        return account.getLastName();
    }
    default String mapEmail(Account account) {
        return account.getUsername();
    }
}
