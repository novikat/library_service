package novikat.library_service.domain.request;

import org.springframework.lang.NonNull;

public record CreateAccountRequest(
        @NonNull
        String username,
        @NonNull
        String email,
        @NonNull
        String password,
        String firstName,
        String lastName
) {
}
