package novikat.library_service.services;

import novikat.library_service.domain.enums.Role;

public interface KeycloakService {
    void createUser(String username, String email, String password);
    void setRole(String username, Role role);
}
