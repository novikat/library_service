package novikat.library_service.services;


import novikat.library_service.models.Author;
import novikat.library_service.models.Book;
import novikat.library_service.models.Category;
import novikat.library_service.models.projection.BookWithAuthorsProjection;
import novikat.library_service.models.request.AddAuthorToBookRequest;
import novikat.library_service.models.request.AddCategoryToBookRequest;
import novikat.library_service.models.request.CreateBookRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public interface BookService {

    Page<BookWithAuthorsProjection> getAllBooksWithAuthors(Pageable pageable);

    Book addBook(CreateBookRequest request);
    Book addAuthorToBook(AddAuthorToBookRequest request);

    Book addCategoryToBook(AddCategoryToBookRequest request);

    void deleteBook(UUID id);

    Book findBookById(UUID id);

    Set<Category> findBookCategories(UUID id);
    void deleteAllBookCategoryByBookId(UUID bookId);

    Set<Author> findBookAuthors(UUID id);

    List<BookWithAuthorsProjection> findAll(String titleLike,
                                            String authorLastNameLike,
                                            Set<UUID> categoriesIn,
                                            Pageable pageable);

}
