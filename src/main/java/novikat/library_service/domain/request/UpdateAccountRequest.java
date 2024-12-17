package novikat.library_service.domain.request;

import org.springframework.lang.NonNull;

public record UpdateAccountRequest(
        @NonNull
        String username,
        String email,
        String firstName,
        String lastName
) {
}
