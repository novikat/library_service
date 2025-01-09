package novikat.library_service.domain.projection;

import java.time.Instant;
import java.util.UUID;

public record CommentWithUsernameProjection (
        UUID id,
        UUID bookId,
        String username,
        Instant date,
        String text
){
}
