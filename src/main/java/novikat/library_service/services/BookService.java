package novikat.library_service.services;

import novikat.library_service.domain.models.Book;
import novikat.library_service.domain.projection.BookWithAuthorsProjection;
import novikat.library_service.domain.request.CreateBookRequest;
import novikat.library_service.domain.request.UpdateBookRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public interface BookService {

//    Page<BookWithAuthorsProjection> getAllBooksWithAuthors(Pageable pageable);

    Book create(CreateBookRequest request);

    void delete(UUID id);

    Book findById(UUID id);
    void deleteAllBookCategoryByBookId(UUID bookId);


    List<BookWithAuthorsProjection> findAll(String titleLike,
                                            String authorLastNameLike,
                                            List<UUID> categoriesIn,
                                            Pageable pageable,
                                            boolean visibleDeleted);
    Book update(UpdateBookRequest request);
    ResponseEntity<ByteArrayResource> downloadFile(UUID bookId);
    ResponseEntity<?> uploadFile(MultipartFile file, UUID bookId);
    void deleteFile(UUID bookId);
}
