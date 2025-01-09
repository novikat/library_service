package novikat.library_service.services;

import novikat.library_service.domain.models.Review;
import novikat.library_service.domain.projection.CommentWithUsernameProjection;
import novikat.library_service.domain.request.AddCommentRequest;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    Review create(AddCommentRequest request);
    void deleteAnyReview(UUID id);
    void deleteOwnReview(UUID id);
    List<CommentWithUsernameProjection> findAllByBookId(UUID bookId);
}
