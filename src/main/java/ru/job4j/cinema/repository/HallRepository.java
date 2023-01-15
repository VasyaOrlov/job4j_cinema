package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Hall;

import java.util.List;
import java.util.Optional;

/**
 * HallRepository - интерфейс хранилища кинозалов
 */
public interface HallRepository {
    List<Hall> findAll();
    Optional<Hall> findById(int id);
    Optional<Hall> add(Hall hall);
}
