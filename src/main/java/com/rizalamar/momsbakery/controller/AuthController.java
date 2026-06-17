package com.rizalamar.momsbakery.controller;

import com.rizalamar.momsbakery.dto.WebResponse;
import com.rizalamar.momsbakery.dto.auth.AuthResponse;
import com.rizalamar.momsbakery.dto.auth.LoginRequest;
import com.rizalamar.momsbakery.dto.auth.RegisterRequest;
import com.rizalamar.momsbakery.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<WebResponse<String>> register(@RequestBody RegisterRequest request){
        String result = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        WebResponse.<String>builder()
                                .code(HttpStatus.CREATED.value())
                                .data(result)
                                .build()
                );
    }

    @PostMapping("/login")
    public ResponseEntity<WebResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<AuthResponse>builder()
                                .code(HttpStatus.OK.value())
                                .data(response)
                                .build()
                );
    }
}
