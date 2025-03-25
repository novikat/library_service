package novikat.library_service.controllers;

import novikat.library_service.domain.request.UpdateBookRequest;
import novikat.library_service.domain.request.CreateBookRequest;
import novikat.library_service.domain.response.BookDetailedResponse;
import novikat.library_service.domain.response.BookWithAuthorsResponse;
import novikat.library_service.facades.BookFacade;
import novikat.library_service.services.BookService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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
                                              @RequestParam(required = false) List<UUID> categoriesIn,
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
    @GetMapping("/file")
    public ResponseEntity<ByteArrayResource> download(@RequestParam final UUID bookId){
        return this.bookService.downloadFile(bookId);
    }
    @PostMapping("/file")
    public ResponseEntity<?> upload(@RequestParam final MultipartFile file, @RequestParam final UUID bookId) throws IOException {
        return new ResponseEntity<>(this.bookService.uploadFile(file, bookId), HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public void deleteFile(@RequestParam final UUID bookId){
        this.bookService.deleteFile(bookId);
    }
}
