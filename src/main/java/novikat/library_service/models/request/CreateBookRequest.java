package novikat.library_service.models.request;

import java.util.Set;
import java.util.UUID;

public record CreateBookRequest (
        String title,
        String description,
        Set<UUID> authors,
        Set<UUID> categories
){}