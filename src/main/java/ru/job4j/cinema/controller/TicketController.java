package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.SessionService;

/**
 * TicketController - класс контроллер.
 * Отвечает за работу с билетом
 */
@Controller
@ThreadSafe
public class TicketController {

    private final SessionService sessionService;
    private final HallService hallService;

    public TicketController(SessionService sessionService, HallService hallService) {
        this.sessionService = sessionService;
        this.hallService = hallService;
    }

    @GetMapping("/formSelectRow/{id}")
    public String selectRow(@PathVariable("id") int id, Model model) {
        model.addAttribute("ticket", new Ticket(0, 0, 0, id));
        model.addAttribute("movieId", id);
        model.addAttribute("rows",
                hallService.findById(sessionService.findById(id)
                                .orElse(new Session()).getHallId())
                        .orElse(new Hall()).getRows());
        return "selectRow";
    }
}
