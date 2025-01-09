package novikat.library_service.domain.request;

import jakarta.validation.constraints.Email;
import org.springframework.lang.NonNull;

import java.util.UUID;

public record UpdateAccountRequest(

        @NonNull
        UUID id,
        String username,
        @Email
        String email,
        String firstName,
        String lastName
) {
}
