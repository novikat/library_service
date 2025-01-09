package novikat.library_service.services;

import novikat.library_service.domain.models.Account;
import novikat.library_service.domain.projection.BookWithAuthorsProjection;
import novikat.library_service.domain.request.EditFavoriteRequest;
import novikat.library_service.domain.request.CreateAccountRequest;
import novikat.library_service.domain.request.UpdateAccountRequest;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    Account findById(UUID id);
    List<Account> findAll();
    Account create(CreateAccountRequest request);
    Account update(UpdateAccountRequest request);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void addFavoriteBook(Account account, UUID bookId);
    void removeFavoriteBook(Account account, UUID bookId);
    List<BookWithAuthorsProjection> findFavoriteBooks(UUID accountId);
}
