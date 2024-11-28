package novikat.library_service.repositories;

import novikat.library_service.models.Book;
import novikat.library_service.models.projection.BookWithAuthorsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    @Query("""
            select new novikat.library_service.models.projection.BookWithAuthorsProjection(
                b.id,
                b.title,
                a.id,
                a.firstName,
                a.lastName)
            from Book b 
                left join BookAuthor ba on b.id = ba.book.id
                left join Author a on ba.author.id = a.id
            """)
    Page<BookWithAuthorsProjection> findAllBooksWithAuthors(Pageable pageable);

    Page<Book> findAll(Specification<Book> filters, Pageable pageable);

    @Query("""
            select new novikat.library_service.models.projection.BookWithAuthorsProjection(
                b.id,
                b.title,
                a.id,
                a.firstName,
                a.lastName)
            from Book b 
                left join BookAuthor ba on b.id = ba.book.id
                left join Author a on ba.author.id = a.id
            where b in ?1
            """)
    List<BookWithAuthorsProjection> findBookAuthorsByBooksIn(List<Book> books);


}
