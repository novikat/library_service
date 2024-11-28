package novikat.library_service.services;

import novikat.library_service.models.Author;
import novikat.library_service.models.Book;
import novikat.library_service.models.Category;
import novikat.library_service.models.projection.BookWithAuthorsProjection;
import novikat.library_service.models.request.AddAuthorToBookRequest;
import novikat.library_service.models.request.AddCategoryToBookRequest;
import novikat.library_service.models.request.CreateBookRequest;
import novikat.library_service.repositories.BookRepository;
import novikat.library_service.utils.BookSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private CategoryService categoryService;
    private AuthorService authorService;

    public BookServiceImpl(BookRepository bookRepository, CategoryService categoryService, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    @Override
    public Page<BookWithAuthorsProjection> getAllBooksWithAuthors(Pageable pageable) {
        return this.bookRepository.findAllBooksWithAuthors(pageable);
    }

    @Override
    @Transactional
    public Book addBook(CreateBookRequest request) {
        Set<Author> authors = this.authorService.getAuthorsByIdIn(request.authors());
        Set<Category> categories = this.categoryService.getCategoriesByIdIn(request.categories());

        Book book = new Book();
        book.setTitle(request.title());
        book.setAuthors(authors);
        book.setCategories(categories);

        return this.bookRepository.save(book);
    }

    @Override
    public Book findBookById(UUID id) {
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with id `" + id + "` doesn`t exist"));
    }

    @Override
    @Transactional
    public Book addAuthorToBook(AddAuthorToBookRequest request) {
        Book book = this.findBookById(request.bookId());
        Author author = this.authorService.getAuthorById(request.authorId());

        book.getAuthors().add(author);

        return this.bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book addCategoryToBook(AddCategoryToBookRequest request) {
        Book book = this.findBookById(request.bookId());
        Category category = this.categoryService.findById(request.categoryId());

        book.getCategories().add(category);

        return this.bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteAllBookCategoryByBookId(UUID bookId) {
        Book book = this.findBookById(bookId);

        book.getCategories().clear();

        this.bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(UUID id) {
        this.deleteAllBookCategoryByBookId(id);
        this.bookRepository.deleteById(id);
    }

    @Override
    public Set<Category> findBookCategories(UUID id) {
        return this.findBookById(id).getCategories();
    }

    @Override
    public Set<Author> findBookAuthors(UUID id) {
        return this.findBookById(id).getAuthors();
    }

    @Override
    public List<BookWithAuthorsProjection> findAll(String titleLike,
                                                   String authorLastNameLike,
                                                   Set<UUID> categoriesIn,
                                                   Pageable pageable) {
        Specification<Book> filters = Specification
                .where(Objects.isNull(titleLike) || titleLike.isBlank()
                        ? null
                        : BookSpecification.titleLike(titleLike))
                .and(Objects.isNull(authorLastNameLike) || authorLastNameLike.isBlank()
                        ? null
                        : BookSpecification.authorLastNameLike(authorLastNameLike))
                .and(Objects.isNull(categoriesIn) || categoriesIn.isEmpty()
                        ? null
                        : BookSpecification.categoryIdIn(categoriesIn));

        Page<Book> filtered = this.bookRepository.findAll(filters, pageable);

        return this.bookRepository.findBookAuthorsByBooksIn(filtered.stream().toList());
    }
}

