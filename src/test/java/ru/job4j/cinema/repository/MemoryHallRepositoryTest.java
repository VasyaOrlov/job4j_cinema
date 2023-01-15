package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.Hall;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

class MemoryHallRepositoryTest {

    /**
     * метод проверки добавления зала в хранилище
     */
    @Test
    public void whenAddTest() {
        MemoryHallRepository rep = new MemoryHallRepository();
        Hall hall = new Hall(0, 4, 4);
        Optional<Hall> opHall = rep.add(hall);
        assertThat(hall.getCells()).isEqualTo(opHall.get().getCells());
        assertThat(hall.getRows()).isEqualTo(opHall.get().getRows());
    }

    /**
     * метод проверки поиска по id зала в хранилище
     */
    @Test
    public void whenFindByIdTest() {
        MemoryHallRepository repository = new MemoryHallRepository();
        Hall hall = new Hall(0, 4, 4);
        Optional<Hall> opHall = repository.add(hall);
        int id = opHall.get().getId();
        hall.setId(id);
        assertThat(hall).isEqualTo(repository.findById(id).get());
    }

    /**
     * метод проверки поиска всех залов в хранилище
     */
    @Test
    public void whenFindAllTest() {
        MemoryHallRepository repository = new MemoryHallRepository();
        List<Hall> list = List.of(
                repository.findById(1).get(),
                repository.findById(2).get(),
                repository.findById(3).get()
        );
        assertThat(repository.findAll()).isEqualTo(list);
    }
}