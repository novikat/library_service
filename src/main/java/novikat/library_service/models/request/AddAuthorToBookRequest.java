package novikat.library_service.models.request;

import org.springframework.lang.NonNull;

import java.util.UUID;

public record AddAuthorToBookRequest (
        @NonNull
        UUID bookId,
        @NonNull
        UUID authorId
){
}
