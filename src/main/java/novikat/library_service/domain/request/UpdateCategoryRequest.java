package novikat.library_service.domain.request;

import org.springframework.lang.NonNull;

import java.util.UUID;

public record UpdateCategoryRequest(
        @NonNull
        UUID id,
        @NonNull
        String name
) {
}
