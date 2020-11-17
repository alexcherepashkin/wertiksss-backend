package ua.alexch.wertiksss.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import ua.alexch.wertiksss.model.Role;

/**
 * Utility class for processing {@link Role}.
 */
public class RoleUtils {

    private RoleUtils() {
    }

    public static Set<Role> convertToRoleEnums(Set<String> roles) {
        Set<String> roleNames = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        return roles.stream()
                .filter(roleNames::contains)
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }

    public static Set<String> convertToRoleNames(Set<Role> roles) {
        return roles.stream()
                .map(Role::name)
                .collect(Collectors.toSet());
    }
}
