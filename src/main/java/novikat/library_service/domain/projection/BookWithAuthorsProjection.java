package novikat.library_service.domain.projection;

import java.util.UUID;

public record BookWithAuthorsProjection(
        UUID bookId,
        String title,
        UUID authorId,
        String authorFirstName,
        String authorLastName
) {
}
