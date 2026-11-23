package ru.group.lab3;

import org.springframework.data.repository.CrudRepository;
import ru.group.lab3.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}