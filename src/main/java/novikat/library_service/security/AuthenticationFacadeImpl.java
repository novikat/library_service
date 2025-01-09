package novikat.library_service.security;

import novikat.library_service.domain.models.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade{

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String getUsername() {
        return this.getAuthentication().getName();
    }

    @Override
    public Boolean evaluateByUsername(Account account) {
        boolean equals = this.getUsername().equals(account.getUsername());
        return equals;
    }
}
