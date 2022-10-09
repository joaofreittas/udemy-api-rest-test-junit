package br.com.udemy.apijunit.infrastructure.repositories;

import br.com.udemy.apijunit.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
