package novikat.library_service.services;

import novikat.library_service.domain.models.Author;
import novikat.library_service.domain.models.Book;
import novikat.library_service.domain.models.Category;
import novikat.library_service.domain.projection.BookWithAuthorsProjection;
import novikat.library_service.domain.request.EditAuthorBookRequest;
import novikat.library_service.domain.request.EditCategoryBookRequest;
import novikat.library_service.domain.request.CreateBookRequest;
import novikat.library_service.domain.request.UpdateBookRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public interface BookService {

//    Page<BookWithAuthorsProjection> getAllBooksWithAuthors(Pageable pageable);

    Book create(CreateBookRequest request);

    void delete(UUID id);

    Book findById(UUID id);

    Set<Category> findBookCategories(UUID id);
    void deleteAllBookCategoryByBookId(UUID bookId);

    Set<Author> findBookAuthors(UUID id);

    List<BookWithAuthorsProjection> findAll(String titleLike,
                                            String authorLastNameLike,
                                            Set<UUID> categoriesIn,
                                            Pageable pageable,
                                            boolean visibleDeleted);

    Book update(UpdateBookRequest request);
}
