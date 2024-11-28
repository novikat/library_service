package novikat.library_service.models.request;

import org.springframework.lang.NonNull;

public record CreateAuthorRequest (
        @NonNull String firstName,
        @NonNull String lastName,
        String biography
){
}
