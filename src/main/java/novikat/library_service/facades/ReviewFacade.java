package novikat.library_service.facades;

import novikat.library_service.domain.models.Review;
import novikat.library_service.domain.request.AddCommentRequest;
import novikat.library_service.domain.response.CommentResponse;
import novikat.library_service.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ReviewFacade {
    private ReviewService reviewService;

    public ReviewFacade(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public CommentResponse create(AddCommentRequest request) {
        Review review = this.reviewService.create(request);
        return new CommentResponse(
                review.getId(),
                review.getBook().getId(),
                review.getAccount().getUsername(),
                review.getCreationUtc(),
                review.getTextValue()
        );
    }

    public Page<CommentResponse> findAllByBookId(UUID bookId, Pageable pageable) {
        List<CommentResponse> comments = this.reviewService.findAllByBookId(bookId).stream()
                .map(commentProjection -> new CommentResponse(
                        commentProjection.id(),
                        commentProjection.bookId(),
                        commentProjection.username(),
                        commentProjection.date(),
                        commentProjection.text()
                ))
                .toList();
        return new PageImpl<>(comments);
    }
}
