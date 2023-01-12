package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.Optional;

/**
 * UserRepository - интерфейс хранилища пользователей
 */
public interface UserRepository {

    Optional<User> add(User user);
    Optional<User> findUser(String email, String password);
}
