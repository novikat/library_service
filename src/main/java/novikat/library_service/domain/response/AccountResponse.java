package novikat.library_service.domain.response;

public record AccountResponse(
        String username,
        String email,
        String firstName,
        String lastName
) {
}
