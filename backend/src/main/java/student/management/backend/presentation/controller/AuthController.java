package student.management.backend.presentation.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.management.backend.application.usecase.LoginUseCase;
import student.management.backend.presentation.dto.LoginRequest;
import student.management.backend.presentation.dto.TokenResponse;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final LoginUseCase loginUseCase;

    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody @Valid LoginRequest request
    ) {
        String token = loginUseCase.execute(
                request.usuario(),
                request.senha()
        );

        return ResponseEntity.ok(new TokenResponse(token));
    }
}