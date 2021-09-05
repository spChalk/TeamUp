package com.example.socialnetworkingapp.model.account;

import com.example.socialnetworkingapp.security.AccountPermission;
import com.google.common.collect.Sets;


import java.util.Set;

public enum AccountRole {
    USER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(
            AccountPermission.USER_READ,
            AccountPermission.POST_READ,
            AccountPermission.USER_WRITE,
            AccountPermission.POST_WRITE));

    private final Set<AccountPermission> permissions;

    AccountRole(Set<AccountPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<AccountPermission> getPermissions() {
        return permissions;
    }
}
