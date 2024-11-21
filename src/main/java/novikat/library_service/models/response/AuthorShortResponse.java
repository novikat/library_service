package novikat.library_service.models.response;

import java.util.UUID;

public record AuthorShortResponse(
        UUID id,
        String firstName,
        String lastName
) {
}
