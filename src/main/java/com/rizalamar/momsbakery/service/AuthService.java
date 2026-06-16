package com.rizalamar.momsbakery.service;

import com.rizalamar.momsbakery.domain.Account;
import com.rizalamar.momsbakery.dto.auth.AuthResponse;
import com.rizalamar.momsbakery.dto.auth.LoginRequest;
import com.rizalamar.momsbakery.dto.auth.RegisterRequest;
import com.rizalamar.momsbakery.repository.AccountRepository;
import com.rizalamar.momsbakery.security.AccountUserDetails;
import com.rizalamar.momsbakery.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public String register(RegisterRequest request){
        if (accountRepository.existsByUsername(request.username())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        Account account = Account.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .email(request.email())
                .phone(request.phone())
                .role(request.role())
                .active(true)
                .build();
        accountRepository.save(account);
        return "Registration successful";
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request){
        // Verifikasi Username dan Password otomatis oleh Spring
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        Account account = accountRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", account.getRole().name());
        extraClaims.put("fullName", account.getFullName());

        String token = jwtUtil.generateToken(extraClaims, new AccountUserDetails(account));

        return AuthResponse.builder()
                .token(token)
                .username(account.getUsername())
                .role(account.getRole().name())
                .build();
    }
}
