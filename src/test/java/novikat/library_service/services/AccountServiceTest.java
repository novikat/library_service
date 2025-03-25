package novikat.library_service.services;

import novikat.library_service.domain.models.Account;
import novikat.library_service.domain.models.Account_;
import novikat.library_service.domain.models.Book;
import novikat.library_service.domain.request.CreateAccountRequest;
import novikat.library_service.domain.request.UpdateAccountRequest;
import novikat.library_service.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private BookService bookService;
    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void create(){
        CreateAccountRequest request = new CreateAccountRequest(
                "username",
                "email@gmail.com",
                "password",
                "firstName",
                "lastName"
        );
        Account expected = Account.Builder.builder()
                .username(request.username().toLowerCase())
                .email(request.email())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();
        when(this.accountRepository.existsByUsername(anyString())).thenReturn(false);
        when(this.accountRepository.existsByEmail(anyString())).thenReturn(false);
        when(this.accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Account result = this.accountService.create(request);
        verify(this.accountRepository, times(1)).existsByUsername(anyString());
        verify(this.accountRepository, times(1)).existsByEmail(anyString());
        verify(this.accountRepository, times(1)).save(any(Account.class));
        assertThat(result)
                .usingRecursiveComparison()
                .comparingOnlyFields(
                        Account_.USERNAME,
                        Account_.EMAIL,
                        Account_.FIRST_NAME,
                        Account_.LAST_NAME
                )
                .isEqualTo(expected);
    }

    @Test
    void create_shouldThrowException_onExistsByUsername(){
        CreateAccountRequest request = new CreateAccountRequest(
                "username",
                "email@gmail.com",
                "password",
                "firstName",
                "lastName"
        );

        when(this.accountRepository.existsByUsername(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> this.accountService.create(request));
        verify(this.accountRepository, times(1)).existsByUsername(anyString());
        verify(this.accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    void create_shouldThrowException_onExistsByEmail(){
        CreateAccountRequest request = new CreateAccountRequest(
                "username",
                "email@gmail.com",
                "password",
                "firstName",
                "lastName"
        );

        when(this.accountRepository.existsByUsername(anyString())).thenReturn(false);
        when(this.accountRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> this.accountService.create(request));
        verify(this.accountRepository, times(1)).existsByUsername(anyString());
        verify(this.accountRepository, times(1)).existsByEmail(anyString());
        verify(this.accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    void update(){
        UUID id = UUID.randomUUID();
        Account expected = Account.Builder.builder()
                .id(id)
                .username("new_username")
                .email("newemail@gmail.com")
                .firstName("newFirstName")
                .lastName("newLastName")
                .build();
        Account account = Account.Builder.builder()
                .id(id)
                .username("username")
                .email("email@gmail.com")
                .firstName("firstName")
                .lastName("lastName")
                .build();
        UpdateAccountRequest request = new UpdateAccountRequest(
                id,
                "new_username",
                "newemail@gmail.com",
                "newFirstName",
                "newLastName"
        );
        when(this.accountRepository.findById(any(UUID.class))).thenReturn(Optional.of(account));
        when(this.accountRepository.existsByUsername(anyString())).thenReturn(false);
        when(this.accountRepository.existsByEmail(anyString())).thenReturn(false);
        when(this.accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Account result = this.accountService.update(request);

        verify(this.accountRepository, times(1)).findById(any(UUID.class));
        verify(this.accountRepository, times(1)).existsByUsername(anyString());
        verify(this.accountRepository, times(1)).existsByEmail(anyString());
        verify(this.accountRepository, times(1)).save(any(Account.class));
        assertThat(result)
                .usingRecursiveComparison()
                .comparingOnlyFields(
                        Account_.ID,
                        Account_.USERNAME,
                        Account_.EMAIL,
                        Account_.FIRST_NAME,
                        Account_.LAST_NAME
                )
                .isEqualTo(expected);
    }

    @Test
    void update_shouldThrowException_onExistByUsername(){
        UUID id = UUID.randomUUID();
        Account account = Account.Builder.builder()
                .id(id)
                .username("username")
                .email("email@gmail.com")
                .firstName("firstName")
                .lastName("lastName")
                .build();
        UpdateAccountRequest request = new UpdateAccountRequest(
                id,
                "new_username",
                "newemail@gmail.com",
                "newFirstName",
                "newLastName"
        );
        when(this.accountRepository.findById(any(UUID.class))).thenReturn(Optional.of(account));
        when(this.accountRepository.existsByUsername(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> this.accountService.update(request));
        verify(this.accountRepository, times(1)).findById(any(UUID.class));
        verify(this.accountRepository, times(1)).existsByUsername(anyString());
        verify(this.accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    void update_shouldThrowException_onExistByEmail(){
        UUID id = UUID.randomUUID();
        Account account = Account.Builder.builder()
                .id(id)
                .username("username")
                .email("email@gmail.com")
                .firstName("firstName")
                .lastName("lastName")
                .build();
        UpdateAccountRequest request = new UpdateAccountRequest(
                id,
                "new_username",
                "newemail@gmail.com",
                "newFirstName",
                "newLastName"
        );
        when(this.accountRepository.findById(any(UUID.class))).thenReturn(Optional.of(account));
        when(this.accountRepository.existsByUsername(anyString())).thenReturn(false);
        when(this.accountRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> this.accountService.update(request));
        verify(this.accountRepository, times(1)).findById(any(UUID.class));
        verify(this.accountRepository, times(1)).existsByUsername(anyString());
        verify(this.accountRepository, times(1)).existsByEmail(anyString());
        verify(this.accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    void addFavoriteBook(){
        Account account = Account.Builder.builder()
                .id(UUID.randomUUID())
                .favoriteBooks(new ArrayList<>()).build();
        Book book = Book.Builder.builder().id(UUID.randomUUID()).build();
        AtomicReference<Account> result = new AtomicReference<>();

        when(this.bookService.findById(any())).thenReturn(book);
        when(this.accountRepository.save(any())).thenAnswer(invocation -> {
            result.set(invocation.getArgument(0));
            return invocation.getArgument(0);
        });

        this.accountService.addFavoriteBook(account, book.getId());

        verify(this.bookService, times(1)).findById(any());
        verify(this.accountRepository, times(1)).save(any());
        assertThat(result.get().getFavoriteBooks()).contains(book);
    }

    @Test
    void removeFavoriteBook(){
        Book book = new Book();
        Account account = Account.Builder.builder()
                .favoriteBooks(new ArrayList<>(List.of(book))).build();
        AtomicReference<Account> result = new AtomicReference<>();

        when(this.bookService.findById(any())).thenReturn(book);
        when(this.accountRepository.save(any())).thenAnswer(invocation -> {
            result.set(invocation.getArgument(0));
            return invocation.getArgument(0);
        });

        this.accountService.removeFavoriteBook(account, UUID.randomUUID());

        verify(this.bookService, times(1)).findById(any());
        verify(this.accountRepository, times(1)).save(any());
        assertThat(result.get().getFavoriteBooks()).isEmpty();
    }
}
