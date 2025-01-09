package novikat.library_service.domain.response;

import java.time.Instant;
import java.util.UUID;

public record CommentResponse(
        UUID id,
        UUID bookId,
        String username,
        Instant date,
        String text
) {
}
