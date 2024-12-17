package novikat.library_service.services;

import novikat.library_service.domain.enums.Role;
import novikat.library_service.domain.models.Account;
import novikat.library_service.domain.request.CreateAccountRequest;
import novikat.library_service.domain.request.UpdateAccountRequest;
import novikat.library_service.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll() {
        return this.accountRepository.findAll();
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.accountRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.accountRepository.existsByEmail(email);
    }

    @Override
    public Account findByUsername(String username) {
        return this.accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username " + username + " has not been found"));
    }

    @Override
    @Transactional
    public Account create(CreateAccountRequest request) {
        Account account = new Account();
        //TODO email, username validation
        if(this.existsByUsername(request.username())){
            throw new RuntimeException("Username is already taken");
        }
        if(this.existsByEmail(request.email())){
            throw new RuntimeException("Email is already taken");
        }
        account.setUsername(request.username());
        account.setEmail(request.email());

        Optional.ofNullable(request.firstName()).ifPresent(account::setFirstName);
        Optional.ofNullable(request.lastName()).ifPresent(account::setLastName);

        return this.accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account update(UpdateAccountRequest request) {
        Account account = this.findByUsername(request.username());
        //TODO email validation
        Optional.ofNullable(request.email()).ifPresent(account::setEmail);
        Optional.ofNullable(request.firstName()).ifPresent(account::setFirstName);
        Optional.ofNullable(request.lastName()).ifPresent(account::setLastName);

        return this.accountRepository.save(account);
    }
}
