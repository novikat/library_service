package novikat.library_service.domain.response;

import java.util.UUID;

public record AuthorResponse (
        UUID id,
        String firstName,
        String lastName,
        String biography
){
}
