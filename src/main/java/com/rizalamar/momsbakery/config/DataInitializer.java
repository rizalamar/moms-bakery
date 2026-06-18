package com.rizalamar.momsbakery.config;

import com.rizalamar.momsbakery.domain.Account;
import com.rizalamar.momsbakery.domain.Role;
import com.rizalamar.momsbakery.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(!accountRepository.existsByUsername("admin")){
            Account account = Account.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("Admin12345"))
                    .fullName("Admin Bakery")
                    .email("admin@bakery.com")
                    .phone("081345855825")
                    .role(Role.ADMIN)
                    .active(true)
                    .build();
            accountRepository.save(account);
        }
    }
}
