package novikat.library_service.facades;

import novikat.library_service.models.Book;
import novikat.library_service.models.projection.BookWithAuthorsProjection;
import novikat.library_service.models.request.AddAuthorToBookRequest;
import novikat.library_service.models.request.AddCategoryToBookRequest;
import novikat.library_service.models.request.CreateBookRequest;
import novikat.library_service.models.response.*;
import novikat.library_service.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
@Component
public class BookFacade {
    private BookService bookService;

    public BookFacade(BookService bookService) {
        this.bookService = bookService;
    }

    public Page<BookWithAuthorsResponse> getAllBooksWithAuthors(Pageable pageable){
        Page<BookWithAuthorsProjection> projections = this.bookService.getAllBooksWithAuthors(pageable);

        List<BookWithAuthorsResponse> books = new ArrayList<>();

        for (BookWithAuthorsProjection projection: projections){
            Optional<BookWithAuthorsResponse> optional = books.stream().filter(book -> book.bookId().equals(projection.bookId())).findFirst();
            if(optional.isEmpty()){
                Set<AuthorShortResponse> authors = new HashSet<>();
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
            }
            else{
                optional.get().authors().add(new AuthorShortResponse(
                        projection.authorId(),
                        projection.authorFirstName(),
                        projection.authorLastName()
                ));
            }
        }

        return new PageImpl<>(books);
    }

    public BookDetailedResponse addBook(CreateBookRequest request){
        Book savedBook = this.bookService.addBook(request);

        return new BookDetailedResponse(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getAuthors().stream()
                        .map(author -> new AuthorShortResponse(
                                author.getId(),
                                author.getFirstName(),
                                author.getLastName()
                        ))
                        .collect(Collectors.toSet()),
                savedBook.getCategories().stream()
                        .map(category -> new CategoryResponse(
                                category.getId(),
                                category.getName()
                        ))
                        .collect(Collectors.toSet())
        );
    }

    public BookDetailedResponse addAuthorToBook(AddAuthorToBookRequest request){
        Book savedBook = this.bookService.addAuthorToBook(request);

        return new BookDetailedResponse(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getAuthors().stream()
                        .map(person -> new AuthorShortResponse(
                                person.getId(),
                                person.getFirstName(),
                                person.getLastName()
                        ))
                        .collect(Collectors.toSet()),
                savedBook.getCategories().stream()
                        .map(category -> new CategoryResponse(
                                category.getId(),
                                category.getName()
                        ))
                        .collect(Collectors.toSet())
        );
    }

    public BookDetailedResponse addCategoryToBook(AddCategoryToBookRequest request){
        Book savedBook = this.bookService.addCategoryToBook(request);

        return new BookDetailedResponse(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getAuthors().stream()
                        .map(person -> new AuthorShortResponse(
                                person.getId(),
                                person.getFirstName(),
                                person.getLastName()
                        ))
                        .collect(Collectors.toSet()),
                savedBook.getCategories().stream()
                        .map(category -> new CategoryResponse(
                                category.getId(),
                                category.getName()
                        ))
                        .collect(Collectors.toSet())
        );
    }

    public BookDetailedResponse findBookById(UUID id) {
        Book book = this.bookService.findBookById(id);
        Set<CategoryResponse> categories = this.bookService.findBookCategories(id).stream()
                .map(category -> new CategoryResponse(
                        category.getId(),
                        category.getName()
                ))
                .collect(Collectors.toSet());
        Set<AuthorShortResponse> authors = this.bookService.findBookAuthors(id).stream()
                .map(author -> new AuthorShortResponse(
                        author.getId(),
                        author.getFirstName(),
                        author.getLastName()
                ))
                .collect(Collectors.toSet());

        return new BookDetailedResponse(
                book.getId(),
                book.getTitle(),
                authors,
                categories
        );
    }

    public Page<BookWithAuthorsResponse> findAll(String titleLike,
                                                 String authorLastNameLike,
                                                 Set<UUID> categoriesIn,
                                                 Pageable pageable) {
        List<BookWithAuthorsProjection> projections = this.bookService
                .findAll(titleLike,
                        authorLastNameLike,
                        categoriesIn,
                        pageable);

        List<BookWithAuthorsResponse> books = new ArrayList<>();

        for (BookWithAuthorsProjection projection: projections){
            Optional<BookWithAuthorsResponse> optional = books.stream()
                    .filter(book -> book.bookId().equals(projection.bookId())).findFirst();
            if(optional.isEmpty()){
                Set<AuthorShortResponse> authors = new HashSet<>();
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
            }
            else{
                optional.get().authors().add(new AuthorShortResponse(
                        projection.authorId(),
                        projection.authorFirstName(),
                        projection.authorLastName()
                ));
            }
        }

        return new PageImpl<>(books);
    }
}
