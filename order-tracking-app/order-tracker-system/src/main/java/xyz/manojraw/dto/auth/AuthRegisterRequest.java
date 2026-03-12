package xyz.manojraw.dto.auth;

public record AuthRegisterRequest(
        String fullName,
        String email,
        String password
) {
}
