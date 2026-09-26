package com.example.demo.enums;

import java.util.Set;

public enum Role {
    ADMIN(Set.of(Permissions.NOTE_DELETE,Permissions.NOTE_UPDATE,Permissions.NOTE_CREATE,Permissions.USER_DELETE,Permissions.USER_READ,Permissions.NOTE_READ))
    ,USER(Set.of(Permissions.NOTE_DELETE,Permissions.NOTE_UPDATE,Permissions.NOTE_READ,Permissions.NOTE_CREATE));

    private final Set<Permissions> permissions;

    Role(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }
}

