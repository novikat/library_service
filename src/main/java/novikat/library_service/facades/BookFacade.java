package novikat.library_service.facades;

import novikat.library_service.models.Book;
import novikat.library_service.models.projection.BookWithAuthorsProjection;
import novikat.library_service.models.request.AddAuthorToBookRequest;
import novikat.library_service.models.request.AddCategoryToBookRequest;
import novikat.library_service.models.request.CreateBookRequest;
import novikat.library_service.models.response.AuthorShortResponse;
import novikat.library_service.models.response.BookDetailedResponse;
import novikat.library_service.models.response.BookWithAuthorsResponse;
import novikat.library_service.models.response.CategoryResponse;
import novikat.library_service.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

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
}
