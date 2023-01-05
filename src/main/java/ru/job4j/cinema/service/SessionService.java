package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;
import java.util.List;
import java.util.Optional;

/**
 * SessionService - интерфейс описывает логику работы фильмов
 */
public interface SessionService {

    List<Session> findAll();

    Optional<Session> findById(int id);
}
