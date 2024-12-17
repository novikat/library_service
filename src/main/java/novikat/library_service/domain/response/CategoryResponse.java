package novikat.library_service.domain.response;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name
) {
}
