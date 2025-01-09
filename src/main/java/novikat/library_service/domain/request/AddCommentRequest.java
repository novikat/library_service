package novikat.library_service.domain.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;

import java.util.UUID;

public record AddCommentRequest(
        @NonNull
        UUID bookId,
        @NonNull
        UUID accountId,
        @NonNull
        @NotBlank
        String text,
        Integer rating
) {
}
