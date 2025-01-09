package novikat.library_service.domain.response;

import java.util.Set;
import java.util.UUID;

public record AuthorWithBooksResponse(
        UUID id,
        String firstName,
        String lastName,
        String biography,
        Set<BookShortResponse> books
) {
}
