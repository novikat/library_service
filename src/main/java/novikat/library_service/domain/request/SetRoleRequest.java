package novikat.library_service.domain.request;

import novikat.library_service.domain.enums.Role;
import org.springframework.lang.NonNull;

import java.util.UUID;

public record SetRoleRequest (
        @NonNull
        UUID id,
        @NonNull
        Role role
){
}
