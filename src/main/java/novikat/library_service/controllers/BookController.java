package novikat.library_service.controllers;

import novikat.library_service.facades.BookFacade;
import novikat.library_service.models.Book;
import novikat.library_service.models.request.AddAuthorToBookRequest;
import novikat.library_service.models.request.AddCategoryToBookRequest;
import novikat.library_service.models.request.CreateBookRequest;
import novikat.library_service.models.response.BookDetailedResponse;
import novikat.library_service.models.response.BookWithAuthorsResponse;
import novikat.library_service.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookFacade bookFacade;

    public BookController(BookFacade bookFacade) {
        this.bookFacade = bookFacade;
    }

    @GetMapping("/all")
    public Page<BookWithAuthorsResponse> getAllBooksWithAuthors(@PageableDefault(value = 20, page = 0) Pageable pageable){
        return this.bookFacade.getAllBooksWithAuthors(pageable);
    }

    @PostMapping("/add")
    public BookDetailedResponse addBook(@RequestBody final CreateBookRequest request){
        return this.bookFacade.addBook(request);
    }

    @PutMapping("/add_author")
    public BookDetailedResponse addAuthorToBook(@RequestBody final AddAuthorToBookRequest request){
        return this.bookFacade.addAuthorToBook(request);
    }

    @PutMapping("/add_category")
    public BookDetailedResponse addCategoryToBook(@RequestBody final AddCategoryToBookRequest request){
        return this.bookFacade.addCategoryToBook(request);
    }
}
