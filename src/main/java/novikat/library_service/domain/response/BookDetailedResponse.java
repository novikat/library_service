package novikat.library_service.domain.response;

import java.util.List;
import java.util.UUID;

public record BookDetailedResponse(
        UUID bookId,
        String title,
        List<AuthorShortResponse> authors,
        List<CategoryResponse> categories
) {
}
