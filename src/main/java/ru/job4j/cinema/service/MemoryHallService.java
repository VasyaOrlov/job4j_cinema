package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;
import java.util.List;
import java.util.Optional;

/**
 * MemoryHallService - реализует интерфейс HallService.
 * Класс реализует логику работу залов кинотеатра.
 */
@Service
@ThreadSafe
public class MemoryHallService implements HallService {

    private final HallRepository hallRepository;

    public MemoryHallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    /**
     * @return - восвращает список фильмов
     */
    @Override
    public List<Hall> findAll() {
        return null;
    }

    /**
     * @param id - индификатор зала
     * @return - возвращает Optional с залом с данным id или null
     */
    @Override
    public Optional<Hall> findById(int id) {
        return hallRepository.findById(id);
    }
}
