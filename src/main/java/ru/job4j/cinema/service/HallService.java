package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Hall;
import java.util.List;
import java.util.Optional;

/**
 * HallService - интерфейс описывает работу логику работы залов кинотеатра
 */
public interface HallService {

    List<Hall> findAll();

    Optional<Hall> findById(int id);
    Optional<Hall> add(Hall hall);
}
