package novikat.library_service.domain.request;

import jakarta.validation.constraints.Email;
import org.springframework.lang.NonNull;

public record CreateAccountRequest(
        @NonNull
        String username,
        @NonNull
        @Email
        String email,
        @NonNull
        String password,
        String firstName,
        String lastName
) {
}
