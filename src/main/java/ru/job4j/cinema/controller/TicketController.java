package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.SessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public String selectRow(@PathVariable("id") int id, Model model,
                            HttpSession httpSession) {
        httpSession.setAttribute("movieId", id);
        model.addAttribute("rows",
                hallService.findById(sessionService.findById(id)
                                .orElse(new Session()).getHallId())
                        .orElse(new Hall()).getRows());
        return "selectRow";
    }

    @PostMapping("/addRow")
    public String addRow(HttpSession httpSession) {

        return "redirect:/formSelectCell";
    }

    @GetMapping("/formSelectCell")
    public String selectCell(Model model, HttpSession httpSession) {
        model.addAttribute("cells",
                hallService.findById(sessionService.findById((int) httpSession.getAttribute("movieId"))
                                .orElse(new Session()).getHallId())
                        .orElse(new Hall()).getCells());
        return "selectCell";
    }

    @PostMapping("/addCell")
    public String addCell(HttpSession httpSession) {

        return "createdTicket";
    }

    @GetMapping("/createdTicket")
    public String createdTicket() {
        return "";
    }
}
