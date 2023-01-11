package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;
import java.util.Optional;

/**
 * DBTicketService - реализует интерфейс TicketService
 * Класс реализует бизнес логику билетов на сеансы
 */
@Service
@ThreadSafe
public class DBTicketService implements TicketService {

    private final TicketRepository ticketRepository;

    public DBTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    /**
     * метод добавления билета
     * @param ticket - билет
     * @return - возвращает Optional с билетом или null
     */
    @Override
    public Optional<Ticket> add(Ticket ticket) {
        return ticketRepository.add(ticket);
    }
}
