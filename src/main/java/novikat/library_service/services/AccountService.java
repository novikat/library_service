package novikat.library_service.services;

import novikat.library_service.domain.enums.Role;
import novikat.library_service.domain.models.Account;
import novikat.library_service.domain.request.CreateAccountRequest;
import novikat.library_service.domain.request.UpdateAccountRequest;

import java.util.List;
public interface AccountService {
    List<Account> findAll();
    Account findByUsername(String username);

    Account create(CreateAccountRequest request);
    Account update(UpdateAccountRequest request);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
