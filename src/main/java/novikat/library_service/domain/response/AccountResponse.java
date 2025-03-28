package novikat.library_service.domain.response;

import java.util.UUID;

public record AccountResponse(
        UUID id,
        String username,
        String email,
        String firstName,
        String lastName
) {
}
