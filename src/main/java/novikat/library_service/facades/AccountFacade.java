package novikat.library_service.facades;

import novikat.library_service.domain.enums.Role;
import novikat.library_service.domain.models.Account;
import novikat.library_service.domain.projection.BookWithAuthorsProjection;
import novikat.library_service.domain.request.*;
import novikat.library_service.domain.response.AccountResponse;
import novikat.library_service.domain.response.AuthorShortResponse;
import novikat.library_service.domain.response.BookWithAuthorsResponse;
import novikat.library_service.security.AuthenticationFacade;
import novikat.library_service.services.AccountService;
import novikat.library_service.services.BookService;
import novikat.library_service.services.KeycloakService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
@Component
public class AccountFacade {
    private AccountService accountService;
    private AuthenticationFacade authenticationFacade;
    private KeycloakService keycloakService;
    private BookService bookService;

    public AccountFacade(AccountService accountService, AuthenticationFacade authenticationFacade, KeycloakService keycloakService, BookService bookService) {
        this.accountService = accountService;
        this.authenticationFacade = authenticationFacade;
        this.keycloakService = keycloakService;
        this.bookService = bookService;
    }

    public List<AccountResponse> findAll(){
        return this.accountService.findAll().stream()
                .map(account -> new AccountResponse(
                        account.getId(),
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
                account.getId(),
                account.getUsername(),
                account.getEmail(),
                account.getFirstName(),
                account.getLastName()
        );
    }

    @Transactional
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
                account.getId(),
                account.getUsername(),
                account.getEmail(),
                account.getFirstName(),
                account.getLastName()
        );
    }

    @Transactional
    public AccountResponse update(UpdateAccountRequest request) {
        Account account = this.accountService.findById(request.id());
        if(this.authenticationFacade.evaluateByUsername(account)){
            this.keycloakService.updateUser(account.getUsername(), request.username(), request.email());
            Account saved = this.accountService.update(request);

            return new AccountResponse(
                    saved.getId(),
                    saved.getUsername(),
                    saved.getEmail(),
                    saved.getFirstName(),
                    saved.getLastName()
            );
        }
        else{
            throw new RuntimeException("Error update: authentication username doesn`t match specified in request");
        }
    }

    public Page<BookWithAuthorsResponse> getFavoriteBooks(UUID accountId) {
        List<BookWithAuthorsProjection> projections = this.accountService
                .findFavoriteBooks(accountId);

        List<BookWithAuthorsResponse> favorites = new ArrayList<>();

        for (BookWithAuthorsProjection projection: projections){
            Optional<BookWithAuthorsResponse> optional = favorites.stream()
                    .filter(book -> book.bookId().equals(projection.bookId())).findFirst();
            if(optional.isEmpty()){
                Set<AuthorShortResponse> authors = new HashSet<>();
                authors.add(new AuthorShortResponse(
                        projection.authorId(),
                        projection.authorFirstName(),
                        projection.authorLastName()
                ));
                favorites.add(new BookWithAuthorsResponse(
                        projection.bookId(),
                        projection.title(),
                        authors
                ));
            }
            else{
                optional.get().authors().add(new AuthorShortResponse(
                        projection.authorId(),
                        projection.authorFirstName(),
                        projection.authorLastName()
                ));
            }
        }

        return new PageImpl<>(favorites);
    }

    public AccountResponse findById(UUID id) {
        Account account = this.accountService.findById(id);
        return new AccountResponse(
                account.getId(),
                account.getUsername(),
                account.getEmail(),
                account.getFirstName(),
                account.getLastName()
        );
    }
    @Transactional
    public void addFavoriteBook(EditFavoriteRequest request){
        Account account = this.accountService.findById(request.accountId());
        if(this.authenticationFacade.evaluateByUsername(account)){
            this.accountService.addFavoriteBook(account, request.bookId());
        }
        else{
            throw new RuntimeException("Error: authenticated user doesn`t match the specified in request");
        }
    }

    @Transactional
    public void removeFavoriteBook(EditFavoriteRequest request){
        Account account = this.accountService.findById(request.accountId());
        if(this.authenticationFacade.evaluateByUsername(account)){
            this.accountService.removeFavoriteBook(account, request.bookId());
        }
        else{
            throw new RuntimeException("Error: authenticated user doesn`t match the user specified in request");
        }
    }

    @Transactional
    public void setRole(SetRoleRequest request) {
        Account account = this.accountService.findById(request.id());
        this.keycloakService.setRole(account.getUsername(), request.role());
    }
}
