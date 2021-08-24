package com.example.socialnetworkingapp.account;

import com.example.socialnetworkingapp.security.AccountPermission;
import com.google.common.collect.Sets;


import java.util.Set;

public enum AppUserRole {
    USER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(
            AccountPermission.USER_READ,
            AccountPermission.POST_READ,
            AccountPermission.USER_WRITE,
            AccountPermission.POST_WRITE));

    private final Set<AccountPermission> permissions;

    AppUserRole(Set<AccountPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<AccountPermission> getPermissions() {
        return permissions;
    }
}
