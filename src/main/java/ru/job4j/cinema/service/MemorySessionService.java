package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;
import java.util.List;
import java.util.Optional;

/**
 * MemorySessionService - класс реализующий интерфейс SessionService.
 * Класс реализует бизнесс логику работы фильмов в приложении.
 */
@Service
@ThreadSafe
public class MemorySessionService implements SessionService {
    private final SessionRepository sessionRepository;

    public MemorySessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    /**
     * метод получения всех фильмов
     * @return возвращает список всех фильмов
     */
    @Override
    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    /**
     * метод получения фильма по id
     * @param id - индификатор фильма
     * @return - возвращает Optional с фильмом с данным id или null
     */
    @Override
    public Optional<Session> findById(int id) {
        return sessionRepository.findById(id);
    }
}
