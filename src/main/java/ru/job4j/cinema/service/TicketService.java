package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;
import java.util.Optional;

/**
 * TicketService - интерфейс описывает логику работу с билетами
 */
public interface TicketService {

    Optional<Ticket> add(Ticket ticket);
}
