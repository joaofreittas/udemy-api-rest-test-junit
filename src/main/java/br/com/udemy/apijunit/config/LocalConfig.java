package br.com.udemy.apijunit.config;

import br.com.udemy.apijunit.domain.user.User;
import br.com.udemy.apijunit.infrastructure.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
@RequiredArgsConstructor
public class LocalConfig {

    private final UserRepository userRepository;

    @Bean
    public void startDB() {
        User user1 = User.builder()
            .email("joao1@gmail.com")
            .password("1234")
            .id(1)
            .name("Joao Lucas")
            .build();

        User user2 = User.builder()
            .email("joao2@gmail.com")
            .password("1234")
            .id(2)
            .name("Pedro")
            .build();

        userRepository.saveAll(List.of(user1, user2));
    }

}
