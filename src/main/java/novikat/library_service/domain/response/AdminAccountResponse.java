package novikat.library_service.domain.response;

import novikat.library_service.domain.enums.Role;

public record AdminAccountResponse(
        String username,
        String email,
        String firstName,
        String lastName,
        Role role
) {
}
