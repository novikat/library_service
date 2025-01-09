package novikat.library_service.repositories;

import novikat.library_service.domain.models.Account;
import novikat.library_service.domain.projection.BookWithAuthorsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Account> findByUsername(String username);

    @Query("""
            select new novikat.library_service.domain.projection.BookWithAuthorsProjection(
                book.id,
                book.title,
                author.id,
                author.firstName,
                author.lastName)
            from AccountBook account_book 
                left join Book book on account_book.book.id = book.id
                left join BookAuthor book_author on book.id = book_author.book.id
                left join Author author on book_author.author.id = author.id
            where account_book.account.id = ?1
            """)
    List<BookWithAuthorsProjection> findFavoriteBooks(UUID accountId);
}
