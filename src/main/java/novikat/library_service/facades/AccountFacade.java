package novikat.library_service.facades;

import novikat.library_service.domain.enums.Role;
import novikat.library_service.domain.models.Account;
import novikat.library_service.domain.request.AdminCreateAccountRequest;
import novikat.library_service.domain.request.CreateAccountRequest;
import novikat.library_service.domain.request.UpdateAccountRequest;
import novikat.library_service.domain.response.AccountResponse;
import novikat.library_service.services.AccountService;
import novikat.library_service.services.KeycloakService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class AccountFacade {
    private AccountService accountService;
    private KeycloakService keycloakService;

    public AccountFacade(AccountService accountService, KeycloakService keycloakService) {
        this.accountService = accountService;
        this.keycloakService = keycloakService;
    }

    public List<AccountResponse> findAll(){
        return this.accountService.findAll().stream()
                .map(account -> new AccountResponse(
                        account.getUsername(),
                        account.getEmail(),
                        account.getFirstName(),
                        account.getLastName()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountResponse create(CreateAccountRequest request) {
        Account account = this.accountService.create(request);
        this.keycloakService.createUser(request.username(), request.email(), request.password());
        this.keycloakService.setRole(request.username(), Role.USER);
        return new AccountResponse(
                account.getUsername(),
                account.getEmail(),
                account.getFirstName(),
                account.getLastName()
        );
    }

    public AccountResponse create(AdminCreateAccountRequest request){
        Account account = this.accountService.create(
                new CreateAccountRequest(
                        request.username(),
                        request.email(),
                        request.password(),
                        request.firstName(),
                        request.lastName()
                )
        );
        this.keycloakService.createUser(request.username(), request.email(), request.password());
        this.keycloakService.setRole(request.username(), request.role());
        return new AccountResponse(
                account.getUsername(),
                account.getEmail(),
                account.getFirstName(),
                account.getLastName()
        );
    }

    public AccountResponse update(UpdateAccountRequest request) {
        Account account = this.accountService.update(request);

        return new AccountResponse(
                account.getUsername(),
                account.getEmail(),
                account.getFirstName(),
                account.getLastName()
        );
    }
}
