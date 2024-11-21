package novikat.library_service.models.response;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name
) {
}
