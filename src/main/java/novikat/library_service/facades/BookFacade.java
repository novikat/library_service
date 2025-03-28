package novikat.library_service.facades;

import novikat.library_service.domain.models.Book;
import novikat.library_service.domain.projection.BookWithAuthorsProjection;
import novikat.library_service.domain.request.CreateBookRequest;
import novikat.library_service.domain.request.UpdateBookRequest;
import novikat.library_service.domain.response.AuthorShortResponse;
import novikat.library_service.domain.response.BookDetailedResponse;
import novikat.library_service.domain.response.BookWithAuthorsResponse;
import novikat.library_service.domain.response.CategoryResponse;
import novikat.library_service.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Component
public class BookFacade {
    private BookService bookService;

    public BookFacade(BookService bookService) {
        this.bookService = bookService;
    }

    public BookDetailedResponse create(CreateBookRequest request){
        Book savedBook = this.bookService.create(request);

        return new BookDetailedResponse(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getAuthors().stream()
                        .map(author -> new AuthorShortResponse(
                                author.getId(),
                                author.getFirstName(),
                                author.getLastName()
                        ))
                        .collect(Collectors.toList()),
                savedBook.getCategories().stream()
                        .map(category -> new CategoryResponse(
                                category.getId(),
                                category.getName()
                        ))
                        .collect(Collectors.toList())
        );
    }

    public BookDetailedResponse findById(UUID id) {
        Book book = this.bookService.findById(id);
        List<CategoryResponse> categories = book.getCategories().stream()
                .map(category -> new CategoryResponse(
                        category.getId(),
                        category.getName()
                ))
                .collect(Collectors.toList());
        List<AuthorShortResponse> authors = book.getAuthors().stream()
                .map(author -> new AuthorShortResponse(
                        author.getId(),
                        author.getFirstName(),
                        author.getLastName()
                ))
                .collect(Collectors.toList());

        return new BookDetailedResponse(
                book.getId(),
                book.getTitle(),
                authors,
                categories
        );
    }

    public Page<BookWithAuthorsResponse> findAll(String titleLike,
                                                 String authorLastNameLike,
                                                 List<UUID> categoriesIn,
                                                 Pageable pageable,
                                                 boolean visibleDeleted) {
        List<BookWithAuthorsProjection> projections = this.bookService
                .findAll(titleLike,
                        authorLastNameLike,
                        categoriesIn,
                        pageable,
                        visibleDeleted);

        List<BookWithAuthorsResponse> books = new ArrayList<>();

        for (BookWithAuthorsProjection projection: projections){
            Optional<BookWithAuthorsResponse> optional = books.stream()
                    .filter(book -> book.bookId().equals(projection.bookId())).findFirst();
            if(optional.isEmpty()){
                List<AuthorShortResponse> authors = new ArrayList<>();
                authors.add(new AuthorShortResponse(
                        projection.authorId(),
                        projection.authorFirstName(),
                        projection.authorLastName()
                ));
                books.add(new BookWithAuthorsResponse(
                        projection.bookId(),
                        projection.title(),
                        authors
                ));
            } else {
                optional.get().authors().add(new AuthorShortResponse(
                        projection.authorId(),
                        projection.authorFirstName(),
                        projection.authorLastName()
                ));
            }
        }

        return new PageImpl<>(books);
    }
    public BookDetailedResponse update(UpdateBookRequest request) {
        Book savedBook = this.bookService.update(request);

        return new BookDetailedResponse(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getAuthors().stream()
                        .map(person -> new AuthorShortResponse(
                                person.getId(),
                                person.getFirstName(),
                                person.getLastName()
                        ))
                        .collect(Collectors.toList()),
                savedBook.getCategories().stream()
                        .map(category -> new CategoryResponse(
                                category.getId(),
                                category.getName()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
