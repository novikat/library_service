package novikat.library_service.domain.request;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

public record UpdateBookRequest(
        @NonNull
        UUID id,
        String title,
        List<UUID> authors,
        List<UUID> categories
) {
}
