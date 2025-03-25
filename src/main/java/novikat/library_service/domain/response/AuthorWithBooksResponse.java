package novikat.library_service.domain.response;

import java.util.List;
import java.util.UUID;

public record AuthorWithBooksResponse(
        UUID id,
        String firstName,
        String lastName,
        String biography,
        List<BookShortResponse> books
) {
}
