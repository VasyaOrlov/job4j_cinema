package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Hall;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MemoryHallRepository - реализует интерфейс HallRepository.
 * Описывает модель хранилища списка залов для показа фильмов
 */
@Repository
@ThreadSafe
public class MemoryHallRepository implements HallRepository {

    private final Map<Integer, Hall> halls = new ConcurrentHashMap<>();

    public MemoryHallRepository() {
        halls.put(1, new Hall(1, 6, 10));
        halls.put(2, new Hall(2, 8, 10));
        halls.put(3, new Hall(3, 6, 15));
    }

    /**
     * @return - возвращает список всех залов
     */
    @Override
    public List<Hall> findAll() {
        return halls.values().stream().toList();
    }

    /**
     * @param id - индификатор зала
     * @return - возвращает Optional с залом с данным id или null
     */
    @Override
    public Optional<Hall> findById(int id) {
        return Optional.ofNullable(halls.get(id));
    }
}
