package novikat.library_service.security;

import novikat.library_service.domain.models.Account;
import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuthentication();
    String getUsername();
    Boolean evaluateByUsername(Account account);
}
