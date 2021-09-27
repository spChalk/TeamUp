package com.example.socialnetworkingapp.model.account;

import com.example.socialnetworkingapp.model.experience.Experience;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NetworkConverter extends StdConverter<List<Account>, List<NetworkEntity>> {

    @Override
    public List<NetworkEntity> convert(List<Account> accounts) {

        List<NetworkEntity> accs = new ArrayList<>();
        for (Account account : accounts) {
            Experience currXp = null;
            for(Experience xp: account.getExperience()) {
                if(Objects.equals(xp.getEndDate(), "")) {
                    currXp = xp;
                    break;
                }
            }
            String headline = null, company = null;
            if(currXp != null) {
                headline = currXp.getHeadline();
                company = currXp.getCompany();
            }
            accs.add(new NetworkEntity(account.getFirstName(),
                    account.getLastName(), account.getEmail(), account.getImageUrl(),
                    headline, company));
        }
        return accs;
    }
}