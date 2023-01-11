package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;
import java.util.Optional;

/**
 * TicketRepository - интерфейс хранилищ билетов
 */
public interface TicketRepository {

    Optional<Ticket> add(Ticket ticket);
}
