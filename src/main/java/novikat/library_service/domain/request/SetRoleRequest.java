package novikat.library_service.domain.request;

import novikat.library_service.domain.enums.Role;
import org.springframework.lang.NonNull;

public record SetRoleRequest (
        @NonNull
        String username,
        @NonNull
        Role role
){
}
