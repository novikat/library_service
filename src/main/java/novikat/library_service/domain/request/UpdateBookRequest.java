package novikat.library_service.domain.request;

import org.springframework.lang.NonNull;

import java.util.Set;
import java.util.UUID;

public record UpdateBookRequest(
        @NonNull
        UUID id,
        String title,
        Set<UUID> authors,
        Set<UUID> categories
) {
}
