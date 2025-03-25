package novikat.library_service.services;

import novikat.library_service.clients.FileLoadClient;
import novikat.library_service.domain.models.Author;
import novikat.library_service.domain.models.Book;
import novikat.library_service.domain.models.Book_;
import novikat.library_service.domain.models.Category;
import novikat.library_service.domain.request.CreateBookRequest;
import novikat.library_service.domain.request.UpdateBookRequest;
import novikat.library_service.repositories.BookRepository;
import novikat.library_service.utils.BookSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    AuthorService authorService;
    @Mock
    CategoryService categoryService;
    @Mock
    BookRepository bookRepository;
    @Mock
    FileLoadClient fileLoadClient;
    @InjectMocks
    BookServiceImpl bookService;

    @Test
    void create(){
        UUID authorId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        CreateBookRequest request = new CreateBookRequest(
                "Dandelion wine",
                null,
                List.of(authorId),
                List.of(categoryId)
        );
        List<Author> authors = List.of(
                Author.Builder.builder()
                        .firstName("Ray")
                        .lastName("Bradbury")
                        .build());
        List<Category> categories = List.of(
                Category.Builder.builder()
                        .name("novel")
                        .build()
        );
        Book book = Book.Builder.builder()
                .title("Dandelion wine")
                .build();

        when(this.authorService.findByIdIn(any())).thenReturn(authors);
        when(this.categoryService.findAllByIdIn(any())).thenReturn(categories);
        when(this.bookRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = this.bookService.create(request);

        verify(this.authorService, times(1)).findByIdIn(any());
        verify(this.categoryService, times(1)).findAllByIdIn(any());
        verify(this.bookRepository, times(1)).save(any());
        assertThat(result)
                .usingRecursiveComparison()
                .comparingOnlyFields(
                        Book_.TITLE
                )
                .isEqualTo(book);
        assertThat(result.getAuthors())
                .hasSameElementsAs(authors);
        assertThat(result.getCategories())
                .hasSameElementsAs(categories);
    }

    @Test
    void deleteAllBookCategoryByBookId(){
        UUID bookId = UUID.randomUUID();
        List<Category> categories = new ArrayList<>();
        categories.add(
                Category.Builder.builder()
                    .name("novel")
                    .build());
        Book book = Book.Builder.builder()
                .id(bookId)
                .title("Dandelion wine")
                .categories(categories)
                .build();
        Book expected = Book.Builder.builder()
                .id(bookId)
                .title("Dandelion wine")
                .build();
        AtomicReference<Book> result = new AtomicReference<>();

        when(this.bookRepository.findById(any())).thenReturn(Optional.of(book));
        when(this.bookRepository.save(any())).thenAnswer(invocation -> {
            result.set(invocation.getArgument(0));
            return invocation.getArgument(0);
        });

        this.bookService.deleteAllBookCategoryByBookId(bookId);

        verify(this.bookRepository, times(1)).findById(any());
        verify(this.bookRepository, times(1)).save(any());
        assertThat(result.get().getCategories()).isEmpty();
        assertThat(result.get())
                .usingRecursiveComparison()
                .comparingOnlyFields(Book_.ID, Book_.TITLE)
                .isEqualTo(expected);
    }

    @Test
    void delete(){
        UUID bookId = UUID.randomUUID();
        Book book = Book.Builder.builder()
                .id(bookId)
                .title("Dandelion wine")
                .categories(new ArrayList<>())
                .build();

        when(this.bookRepository.findById(any())).thenReturn(Optional.of(book));
        when(this.bookRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        doNothing().when(this.bookRepository).deleteById(any());
        doNothing().when(this.fileLoadClient).delete(any());

        this.bookService.delete(bookId);

        verify(this.bookRepository, times(1)).findById(any());
        verify(this.bookRepository, times(1)).save(any());
        verify(this.bookRepository, times(1)).deleteById(any());
        verify(this.fileLoadClient, times(1)).delete(any());
    }

    @Test
    void findAll(){
        String titleLike = "title";
        String authorLastNameLike = "lastname";
        List<UUID> categoriesIn = List.of(UUID.randomUUID());
        Pageable pageable = PageRequest.of(0, 20);
        boolean visibleDeleted = false;
        Specification<Book> expected = Specification
                .where(BookSpecification.titleLike(titleLike))
                .and(BookSpecification.authorLastNameLike(authorLastNameLike))
                .and(BookSpecification.categoryIdIn(categoriesIn))
                .and(BookSpecification.isNotDeleted());
        AtomicReference<Specification<Book>> specification = new AtomicReference<>();

        when(this.bookRepository.findAll(any(Specification.class), any(Pageable.class))).thenAnswer(invocation -> {
            specification.set(invocation.getArgument(0));
            return new PageImpl<>(new ArrayList<>());
        });
        when(this.bookRepository.findBookAuthorsByBooksIn(any())).thenReturn(new ArrayList<>());

        this.bookService.findAll(titleLike, authorLastNameLike, categoriesIn, pageable, visibleDeleted);

        verify(bookRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(bookRepository, times(1)).findBookAuthorsByBooksIn(any());
        assertThat(specification.get())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void update(){
        UUID bookId = UUID.randomUUID();
        String title = "title";
        String newTitle = "newTitle";
        UUID authorId = UUID.randomUUID();
        Author author = Author.Builder.builder()
                .id(authorId)
                .firstName("firstName")
                .lastName("lastName")
                .build();
        UUID categoryId = UUID.randomUUID();
        Category category = Category.Builder.builder()
                .id(categoryId)
                .name("category")
                .build();
        UUID newAuthorId = UUID.randomUUID();
        Author newAuthor = Author.Builder.builder()
                .id(newAuthorId)
                .firstName("newFirstName")
                .lastName("newLastName")
                .build();
        UUID newCategoryId = UUID.randomUUID();
        Category newCategory = Category.Builder.builder()
                .id(newCategoryId)
                .name("newCategory")
                .build();
        UpdateBookRequest request = new UpdateBookRequest(
                bookId,
                newTitle,
                List.of(newAuthorId),
                List.of(newCategoryId));
        Book book = Book.Builder.builder()
                .id(bookId)
                .title(title)
                .authors(new ArrayList<>(List.of(author)))
                .categories(new ArrayList<>(List.of(category)))
                .build();

        when(this.bookRepository.findById(any())).thenReturn(Optional.of(book));
        when(this.authorService.findByIdIn(any())).thenReturn(List.of(newAuthor));
        when(this.categoryService.findAllByIdIn(any())).thenReturn(List.of(newCategory));
        when(this.bookRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = this.bookService.update(request);

        verify(this.bookRepository, times(1)).findById(any());
        verify(this.authorService, times(1)).findByIdIn(any());
        verify(this.categoryService, times(1)).findAllByIdIn(any());
        assertThat(result.getTitle()).isEqualTo(newTitle);
        assertThat(result.getCategories()).hasSameElementsAs(List.of(newCategory));
        assertThat(result.getAuthors()).hasSameElementsAs(List.of(newAuthor));
    }
}
