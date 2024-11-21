package novikat.library_service.models.response;

import java.util.UUID;

public record AuthorResponse (
        UUID id,
        String firstName,
        String lastName,
        String biography
){
}
