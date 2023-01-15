package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Hall;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * MemoryHallRepository - реализует интерфейс HallRepository.
 * Описывает модель хранилища списка залов для показа фильмов
 */
@Repository
@ThreadSafe
public class MemoryHallRepository implements HallRepository {

    private final Map<Integer, Hall> halls = new ConcurrentHashMap<>();

    private final AtomicInteger size = new AtomicInteger(3);

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

    /**
     * метод добавляет в хранилище холл
     * @param hall - холл
     * @return - возвращает Optional с hall или с null
     */
    @Override
    public Optional<Hall> add(Hall hall) {
        int id = size.incrementAndGet();
        hall.setId(id);
        Optional<Hall> rsl = Optional.ofNullable(halls.putIfAbsent(hall.getId(), hall));
        if (rsl.isPresent()) {
            size.decrementAndGet();
        }
        return Optional.of(hall);
    }
}
