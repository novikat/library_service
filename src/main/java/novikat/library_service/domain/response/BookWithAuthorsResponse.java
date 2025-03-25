package novikat.library_service.domain.response;

import java.util.List;
import java.util.UUID;

public record BookWithAuthorsResponse(
        UUID bookId,
        String title,
        List<AuthorShortResponse> authors
) {
}
