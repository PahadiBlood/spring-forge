package xyz.manojraw.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.manojraw.dto.auth.AuthLoginRequest;
import xyz.manojraw.dto.auth.AuthRegisterRequest;
import xyz.manojraw.dto.auth.AuthResponse;
import xyz.manojraw.service.impl.AuthServiceImpl;

@Tag(name = "Auth", description = "Handles auth operations")

@CrossOrigin(
        origins = "*",
        allowedHeaders = "Content-Type",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS}
)

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRegisterRequest authRequest) {
        return ResponseEntity.ok(authService.register(authRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthLoginRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }
}
