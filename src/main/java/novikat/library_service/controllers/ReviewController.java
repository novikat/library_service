package novikat.library_service.controllers;

import novikat.library_service.domain.request.AddCommentRequest;
import novikat.library_service.domain.response.CommentResponse;
import novikat.library_service.facades.ReviewFacade;
import novikat.library_service.services.AccountService;
import novikat.library_service.services.BookService;
import novikat.library_service.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/review")
public class ReviewController{
    private ReviewFacade reviewFacade;
    private ReviewService reviewService;
    private AccountService accountService;
    private BookService bookService;

    public ReviewController(ReviewFacade reviewFacade,
                            ReviewService reviewService,
                            AccountService accountService,
                            BookService bookService) {
        this.reviewFacade = reviewFacade;
        this.reviewService = reviewService;
        this.accountService = accountService;
        this.bookService = bookService;
    }

    @PostMapping()
    public CommentResponse create(@RequestBody final AddCommentRequest request){
        return this.reviewFacade.create(request);
    }

    @DeleteMapping()
    public void delete(@RequestParam final @NonNull UUID id){
        this.reviewService.deleteAnyReview(id);
    }

    @GetMapping()
    public Page<CommentResponse> findAllByBookId(@RequestParam final @NonNull UUID bookId,
                                                      @PageableDefault(value = 20, page = 0) Pageable pageable){
        return this.reviewFacade.findAllByBookId(bookId, pageable);
    }
}
