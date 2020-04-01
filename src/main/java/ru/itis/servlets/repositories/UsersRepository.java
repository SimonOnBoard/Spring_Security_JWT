package ru.itis.servlets.repositories;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.itis.servlets.models.User;

import java.util.Optional;
public interface UsersRepository extends CrudRepository<Long, User> {
    Optional<User> findByCode(String key);
    Optional<User> findByLogin(String login);
}
