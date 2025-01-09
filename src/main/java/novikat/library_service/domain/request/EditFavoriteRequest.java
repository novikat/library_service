package novikat.library_service.domain.request;

import java.util.UUID;

public record EditFavoriteRequest(
        UUID accountId,
        UUID bookId
) {
}
