package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;
import java.util.List;
import java.util.Optional;

/**
 * SessionRepository - интерфейс хранилищ фильмов
 */
public interface SessionRepository {
    List<Session> findAll();

    Optional<Session> findById(int id);
}
