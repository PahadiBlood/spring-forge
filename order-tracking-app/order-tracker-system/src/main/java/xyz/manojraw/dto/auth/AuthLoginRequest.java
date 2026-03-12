package xyz.manojraw.dto.auth;

public record AuthLoginRequest(
        String email,
        String password
) {
}
