package novikat.library_service.models.request;

import org.springframework.lang.NonNull;

import java.util.UUID;

public record UpdateAuthorRequest(
        @NonNull UUID id,
        String firstName,
        String lastName,
        String biography
        ) {
}
