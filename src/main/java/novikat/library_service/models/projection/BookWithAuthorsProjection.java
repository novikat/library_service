package novikat.library_service.models.projection;

import java.util.UUID;

public record BookWithAuthorsProjection(
        UUID bookId,
        String title,
        UUID authorId,
        String authorFirstName,
        String authorLastName
) {
}
