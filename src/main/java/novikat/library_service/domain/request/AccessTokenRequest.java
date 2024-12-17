package novikat.library_service.domain.request;

public record AccessTokenRequest(
        String username,
        String password
) {
}
