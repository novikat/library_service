package novikat.library_service.domain.request;

import java.util.List;
import java.util.UUID;

public record CreateBookRequest (
        String title,
        String description,
        List<UUID> authors,
        List<UUID> categories
){}