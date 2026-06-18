package com.rizalamar.momsbakery.config;

import com.rizalamar.momsbakery.domain.Account;
import com.rizalamar.momsbakery.domain.Category;
import com.rizalamar.momsbakery.domain.CategoryType;
import com.rizalamar.momsbakery.domain.Role;
import com.rizalamar.momsbakery.repository.AccountRepository;
import com.rizalamar.momsbakery.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedAdmin();
    }

    private void seedAdmin(){
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

    private void seedCategory(){
        if(categoryRepository.count() == 0){
            Arrays.stream(CategoryType.values()).forEach(
                    type -> {
                        Category category = Category.builder()
                                .name(type)
                                .description("Category for " + type.name().toLowerCase())
                                .build();
                        categoryRepository.save(category);
                        System.out.println(">>> Database Seeded: Categories Created Successfully.");
                    }
            );
        }
    }
}
