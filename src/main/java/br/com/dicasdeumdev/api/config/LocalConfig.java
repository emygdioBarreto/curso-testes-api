package br.com.dicasdeumdev.api.config;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    private final UserRepository repository;

    public LocalConfig(UserRepository repository) {
        this.repository = repository;
    }

    @Bean
    public List<User> startDB() {
        User u1 = new User(null, "Enygdio", "emygdio.barreto@gmail.com", "paladinoT8");
        User u2 = new User(null, "Flavia", "flavia.coelho@gmail.com", "popa!2111");

        return repository.saveAll(List.of(u1,u2));
    }
}
