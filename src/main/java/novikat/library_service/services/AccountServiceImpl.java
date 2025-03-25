package novikat.library_service.services;

import novikat.library_service.domain.models.Account;
import novikat.library_service.domain.models.Book;
import novikat.library_service.domain.projection.BookWithAuthorsProjection;
import novikat.library_service.domain.request.EditFavoriteRequest;
import novikat.library_service.domain.request.CreateAccountRequest;
import novikat.library_service.domain.request.UpdateAccountRequest;
import novikat.library_service.repositories.AccountRepository;
import novikat.library_service.security.AuthenticationFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;
    private BookService bookService;

    public AccountServiceImpl(AccountRepository accountRepository, BookService bookService) {
        this.accountRepository = accountRepository;
        this.bookService = bookService;
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

    public Account findByUsername(String username) {
        return this.accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username " + username + " has not been found"));
    }

    @Override
    public Account findById(UUID id) {
        return this.accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " has not been found"));
    }

    @Override
    @Transactional
    public Account create(CreateAccountRequest request) {
        if(this.existsByUsername(request.username().toLowerCase())){
            throw new RuntimeException("Username is already taken");
        }
        if(this.existsByEmail(request.email())){
            throw new RuntimeException("Email is already taken");
        }

        Account account = Account.Builder.builder()
                .username(request.username().toLowerCase())
                .email(request.email())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();

        return this.accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account update(UpdateAccountRequest request) {
        Account account = this.findById(request.id());
        Optional.ofNullable(request.username()).ifPresent(username -> {
            if(!this.existsByUsername(username.toLowerCase())){
                account.setUsername(username.toLowerCase());
            }
            else{
                throw new RuntimeException("Username is already taken");
            }
        });
        Optional.ofNullable(request.email()).ifPresent(email -> {
            if(!this.existsByEmail(email)){
                account.setEmail(email);
            }
            else{
                throw new RuntimeException("Email is already taken");
            }
        });
        Optional.ofNullable(request.firstName()).ifPresent(account::setFirstName);
        Optional.ofNullable(request.lastName()).ifPresent(account::setLastName);

        return this.accountRepository.save(account);
    }

    @Override
    @Transactional
    public void addFavoriteBook(Account account, UUID bookId) {
        Book book = this.bookService.findById(bookId);
        account.getFavoriteBooks().add(book);
        this.accountRepository.save(account);
    }

    @Override
    @Transactional
    public void removeFavoriteBook(Account account, UUID bookId) {
        Book book = this.bookService.findById(bookId);
        account.getFavoriteBooks().remove(book);
        this.accountRepository.save(account);
    }

    @Override
    public List<BookWithAuthorsProjection> findFavoriteBooks(UUID accountId) {
        return this.accountRepository.findFavoriteBooks(accountId);
    }
}
