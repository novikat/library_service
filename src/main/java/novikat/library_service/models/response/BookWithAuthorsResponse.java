package novikat.library_service.models.response;

import java.util.Set;
import java.util.UUID;

public record BookWithAuthorsResponse(
        UUID bookId,
        String title,
        Set<AuthorShortResponse> authors
) {
}
