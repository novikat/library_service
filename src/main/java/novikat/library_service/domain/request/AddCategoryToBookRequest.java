package novikat.library_service.domain.request;

import org.springframework.lang.NonNull;

import java.util.UUID;

public record AddCategoryToBookRequest(
        @NonNull
        UUID bookId,
        @NonNull
        UUID categoryId
) {
}
