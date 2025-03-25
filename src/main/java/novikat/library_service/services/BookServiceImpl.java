package novikat.library_service.services;

import novikat.library_service.clients.FileLoadClient;
import novikat.library_service.domain.models.*;
import novikat.library_service.domain.projection.BookWithAuthorsProjection;
import novikat.library_service.domain.request.EditAuthorBookRequest;
import novikat.library_service.domain.request.EditCategoryBookRequest;
import novikat.library_service.domain.request.CreateBookRequest;
import novikat.library_service.domain.request.UpdateBookRequest;
import novikat.library_service.repositories.BookRepository;
import novikat.library_service.utils.BookSpecification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private CategoryService categoryService;
    private AuthorService authorService;
    private FileLoadClient fileLoadClient;

    public BookServiceImpl(BookRepository bookRepository, CategoryService categoryService, AuthorService authorService, FileLoadClient fileLoadClient) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.fileLoadClient = fileLoadClient;
    }

    /*  @Override
    public Page<BookWithAuthorsProjection> getAllBooksWithAuthors(Pageable pageable) {
        return this.bookRepository.findAllBooksWithAuthors(pageable);
    }*/

    @Override
    @Transactional
    public Book create(CreateBookRequest request) {
        List<Author> authors = this.authorService.findByIdIn(request.authors());
        List<Category> categories = this.categoryService.findAllByIdIn(request.categories());

        Book book = Book.Builder.builder()
                .title(request.title())
                .authors(authors)
                .categories(categories)
                .build();
        return this.bookRepository.save(book);
    }

    @Override
    public Book findById(UUID id) {
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with id `" + id + "` doesn`t exist"));
    }

    @Override
    @Transactional
    public void deleteAllBookCategoryByBookId(UUID bookId) {
        Book book = this.findById(bookId);
        book.getCategories().clear();
        this.bookRepository.save(book);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.deleteAllBookCategoryByBookId(id);
        this.bookRepository.deleteById(id);
        this.deleteFile(id);
    }

    @Override
    public List<BookWithAuthorsProjection> findAll(String titleLike,
                                                   String authorLastNameLike,
                                                   List<UUID> categoriesIn,
                                                   Pageable pageable,
                                                   boolean visibleDeleted) {
        Specification<Book> filters = Specification
                .where(Objects.isNull(titleLike) || titleLike.isBlank()
                        ? null
                        : BookSpecification.titleLike(titleLike))
                .and(Objects.isNull(authorLastNameLike) || authorLastNameLike.isBlank()
                        ? null
                        : BookSpecification.authorLastNameLike(authorLastNameLike))
                .and(Objects.isNull(categoriesIn) || categoriesIn.isEmpty()
                        ? null
                        : BookSpecification.categoryIdIn(categoriesIn))
                .and(visibleDeleted
                        ? null
                        : BookSpecification.isNotDeleted());

        Page<Book> filtered = this.bookRepository.findAll(filters, pageable);
        return this.bookRepository.findBookAuthorsByBooksIn(filtered.stream().toList());
    }

    @Override
    @Transactional
    public Book update(UpdateBookRequest request) {
        Book book = this.findById(request.id());
        if(!Objects.isNull(request.authors()) && request.authors().size() > 0 ){
            List<Author> authors = this.authorService.findByIdIn(request.authors());
            book.getAuthors().clear();
            book.getAuthors().addAll(authors);
        }
        if(!Objects.isNull(request.categories())){
            List<Category> categories = this.categoryService.findAllByIdIn(request.categories());
            book.getCategories().clear();
            book.getCategories().addAll(categories);
        }
        if(!Objects.isNull(request.title()) && !request.title().isBlank()){
            book.setTitle(request.title());
        }
        return this.bookRepository.save(book);
    }
    @Override
    public ResponseEntity<ByteArrayResource> downloadFile(UUID bookId) {
        return this.fileLoadClient.download(bookId);
    }
    @Override
    public ResponseEntity<?> uploadFile(MultipartFile file, UUID bookId) {
        return this.fileLoadClient.upload(file, bookId);
    }
    @Override
    public void deleteFile(UUID bookId) {
        this.fileLoadClient.delete(bookId);
    }
}