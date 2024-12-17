package novikat.library_service.domain.request;

import novikat.library_service.domain.enums.Role;
import org.springframework.lang.NonNull;

public record AdminCreateAccountRequest(
        @NonNull
        String username,
        @NonNull
        String email,
        @NonNull
        String password,
        String firstName,
        String lastName,
        @NonNull
        Role role
) {
}
