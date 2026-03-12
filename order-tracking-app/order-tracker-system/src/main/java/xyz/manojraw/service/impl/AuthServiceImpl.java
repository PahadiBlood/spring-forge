package xyz.manojraw.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xyz.manojraw.dto.auth.AuthLoginRequest;
import xyz.manojraw.dto.auth.AuthRegisterRequest;
import xyz.manojraw.dto.auth.AuthResponse;
import xyz.manojraw.entity.User;
import xyz.manojraw.exception.ApiException;
import xyz.manojraw.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl {
    private final UserRepository userRepository;

    public AuthResponse register(AuthRegisterRequest authRequest) {
        if (userRepository.existsByEmail(authRequest.email())) {
            throw new ApiException("User already exists", HttpStatus.CONFLICT);
        }
        User user = new User();
        user.setFullName(authRequest.fullName());
        user.setEmail(authRequest.email());
        user.setPassword(authRequest.password());

        userRepository.save(user);
        String token = generateRandomToken(user);
        return new AuthResponse(token);
    }


    public AuthResponse login(AuthLoginRequest authRequest) {
        return new AuthResponse(generateRandomToken(
                userRepository
                        .findByEmail(authRequest.email())
                        .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND))
        ));
    }

    private String generateRandomToken(User user) {
        //this is only for testing purpose use jwt based auth in real project
        return user.getId() + "";
    }
}
