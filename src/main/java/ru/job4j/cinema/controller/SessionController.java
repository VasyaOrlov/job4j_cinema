package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.service.SessionService;

/**
 * SessionController - класс контроллер
 * Отвеччает за работу к саенсами в кинотеатре
 */
@Controller
@ThreadSafe
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/cinema")
    public String cinema(Model model) {
        model.addAttribute("movies", sessionService.findAll());
        return "cinema";
    }
}
