package ru.job4j.cinema.service;

import ru.job4j.cinema.model.User;
import java.util.Optional;

/**
 * UserService - интерфейс описывает логику работу с пользователями
 */
public interface UserService {
    Optional<User> add(User user);
    Optional<User> findUser(String email, String password);
}
