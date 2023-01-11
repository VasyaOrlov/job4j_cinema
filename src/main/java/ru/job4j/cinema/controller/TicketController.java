package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * TicketController - класс контроллер.
 * Отвечает за работу с билетом
 */
@Controller
@ThreadSafe
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * метод сохраняет билет в базу данных и сообщает о результате операции
     * @param model - модель данных
     * @param httpSession - объект связанный с работой пользователя
     * @return - возвращает вид с результатом операции
     */
    @GetMapping("/createdTicket")
    public String createdTicket(Model model, HttpSession httpSession) {
        int row = (int) httpSession.getAttribute("row");
        int cell = (int) httpSession.getAttribute("cell");
        int idSession = ((Session) httpSession.getAttribute("movie")).getId();
        Ticket ticket = new Ticket(0, row, cell, idSession);
        Optional<Ticket> rsl = ticketService.add(ticket);
        if (rsl.isEmpty()) {
            return "ticketFail";
        }
        model.addAttribute("row", row);
        model.addAttribute("cell", cell);
        model.addAttribute("movie", httpSession.getAttribute("movie"));
        return "ticketSuccess";
    }
}
