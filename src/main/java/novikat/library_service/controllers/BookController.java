package novikat.library_service.controllers;

import novikat.library_service.domain.request.UpdateBookRequest;
import novikat.library_service.domain.request.CreateBookRequest;
import novikat.library_service.domain.response.BookDetailedResponse;
import novikat.library_service.domain.response.BookWithAuthorsResponse;
import novikat.library_service.facades.BookFacade;
import novikat.library_service.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookFacade bookFacade;

    private BookService bookService;

    public BookController(BookFacade bookFacade, BookService bookService) {
        this.bookFacade = bookFacade;
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public Page<BookWithAuthorsResponse> findAll(@RequestParam(required = false) String titleLike,
                                              @RequestParam(required = false) String authorLastNameLike,
                                              @RequestParam(required = false) Set<UUID> categoriesIn,
                                              @PageableDefault (value = 20, page = 0) Pageable pageable
                                              ){
        return this.bookFacade.findAll(titleLike, authorLastNameLike, categoriesIn, pageable, false);
    }

    @GetMapping()
    public BookDetailedResponse findById(@RequestParam UUID id){
        return this.bookFacade.findById(id);
    }

    @PostMapping()
    public BookDetailedResponse create(@RequestBody final CreateBookRequest request){
        return this.bookFacade.create(request);
    }
    @DeleteMapping()
    public void delete(@RequestParam final UUID id){
        this.bookService.delete(id);
    }

    @PutMapping()
    public BookDetailedResponse update(@RequestBody final UpdateBookRequest request){
        return this.bookFacade.update(request);
    }
}
