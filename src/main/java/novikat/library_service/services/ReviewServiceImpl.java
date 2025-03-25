package novikat.library_service.services;

import novikat.library_service.domain.models.Account;
import novikat.library_service.domain.models.Book;
import novikat.library_service.domain.models.Review;
import novikat.library_service.domain.projection.CommentWithUsernameProjection;
import novikat.library_service.domain.request.AddCommentRequest;
import novikat.library_service.repositories.ReviewRepository;
import novikat.library_service.security.AuthenticationFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class ReviewServiceImpl implements ReviewService{
    private ReviewRepository reviewRepository;
    private AccountService accountService;
    private BookService bookService;
    private AuthenticationFacade authenticationFacade;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             AccountService accountService,
                             BookService bookService,
                             AuthenticationFacade authenticationFacade) {
        this.reviewRepository = reviewRepository;
        this.accountService = accountService;
        this.bookService = bookService;
        this.authenticationFacade = authenticationFacade;
    }


    @Override
    @Transactional
    public Review create(AddCommentRequest request) {
        Account account = this.accountService.findById(request.accountId());
        if(this.authenticationFacade.evaluateByUsername(account)) {
            Book book = this.bookService.findById(request.bookId());
            Review review = Review.Builder.builder()
                    .book(book)
                    .account(account)
                    .creationUtc(Instant.now())
                    .textValue(request.text())
                    .build();
            return this.reviewRepository.save(review);
        }
        else {
            throw new RuntimeException("Error: authenticated user doesn`t match specified in request");
        }
    }

    @Override
    @Transactional
    public void deleteAnyReview(UUID id) {
        this.reviewRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteOwnReview(UUID id) {
        Optional<Review> reviewOptional = this.reviewRepository.findById(id);
        reviewOptional.ifPresent(review -> {
            if(this.authenticationFacade.evaluateByUsername(review.getAccount())){
                this.reviewRepository.deleteById(id);
            }
            else{
                throw new RuntimeException("Error: authenticated user doesn`t match specified in request");
            }
        });
    }

    @Override
    public List<CommentWithUsernameProjection> findAllByBookId(UUID bookId) {
        return this.reviewRepository.findAllByBookId(bookId);
    }
}
