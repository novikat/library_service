package novikat.library_service.domain.response;

import java.util.UUID;

public record BookShortResponse(
        UUID id,
        String title
) {
}
