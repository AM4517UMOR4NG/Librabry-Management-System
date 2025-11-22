package com.example.library_management_utspbold.dto;

import java.util.List;
import java.util.Objects;

/**
 * Immutable description for responsibilities, permissions, and notes that belong to a role.
 */
public record RolePolicy(
        String roleName,
        String description,
        List<String> duties,
        List<String> permissions,
        List<String> optionalNotes
) {
    public RolePolicy {
        Objects.requireNonNull(roleName, "roleName must not be null");
        Objects.requireNonNull(description, "description must not be null");
        duties = List.copyOf(duties);
        permissions = List.copyOf(permissions);
        optionalNotes = optionalNotes == null ? List.of() : List.copyOf(optionalNotes);
    }
}
