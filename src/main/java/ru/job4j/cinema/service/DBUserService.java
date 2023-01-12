package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;

/**
 * DBUserService - реализует интерфейс UserService
 * Класс реализует логику работу пользователей кинотеатра
 */
@Service
@ThreadSafe
public class DBUserService implements UserService {

    private final UserRepository userRepository;

    public DBUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * метод добавляет пользователя в базу данных
     * @param user - пользователь
     * @return - Optional c пользователем или null
     */
    @Override
    public Optional<User> add(User user) {
        return userRepository.add(user);
    }

    /**
     * метод ищет пользователя в базе данных по почте и паролю
     * @param email - почта
     * @param password - пароль
     * @return - возвращает Optional с пользователем или null
     */
    @Override
    public Optional<User> findUser(String email, String password) {
        return userRepository.findUser(email, password);
    }
}
