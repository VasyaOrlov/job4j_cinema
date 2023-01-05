package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MemorySessionRepository - реализует интерфейс SessionRepository.
 * Описывает модель хранилища списка фильмов
 */
@Repository
@ThreadSafe
public class MemorySessionRepository implements SessionRepository {
    private final Map<Integer, Session> sessions = new ConcurrentHashMap<>();

    private MemorySessionRepository() {
        sessions.put(1, new Session(1, "Ужасы на улице", 1));
        sessions.put(2, new Session(2, "Комедия в офисе", 2));
        sessions.put(3, new Session(3, "Фантастика во дворе", 3));
    }

    /**
     * @return возвращает список всех фильмов
     */
    @Override
    public List<Session> findAll() {
        return sessions.values().stream().toList();
    }

    /**
     * @param id - индификатор фильма
     * @return - возвращает Optional с фильмом с данным id или null
     */
    @Override
    public Optional<Session> findById(int id) {
        return Optional.ofNullable(sessions.get(id));
    }
}
