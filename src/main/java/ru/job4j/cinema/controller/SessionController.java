package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.SessionService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static ru.job4j.cinema.util.GetUser.getUser;

/**
 * SessionController - класс контроллер
 * Отвечает за работу с сеансами в кинотеатре
 */
@Controller
@ThreadSafe
public class SessionController {
    private final SessionService sessionService;
    private final HallService hallService;

    public SessionController(SessionService sessionService, HallService hallService) {
        this.sessionService = sessionService;
        this.hallService = hallService;
    }

    /**
     * метод возвращает вид со списком всех сеансов на фильмы
     * @param model - модель с данными
     * @return - вид всех сеансов
     */
    @GetMapping("/cinema")
    public String cinema(Model model, HttpSession httpSession) {
        model.addAttribute("movies", sessionService.findAll());
        getUser(model, httpSession);
        return "cinema";
    }

    /**
     * метод возвращает вид с выбором ряда на сеанс
     * @param id - индификатор сеанса
     * @param model - модель данных
     * @param httpSession - объект связанный с работой пользователя
     * @return - вид с выбором ряда
     */
    @GetMapping("/formSelectRow/{id}")
    public String selectRow(@PathVariable("id") int id, Model model,
                            HttpSession httpSession) {
        httpSession.setAttribute("movie", sessionService.findById(id).orElse(new Session()));
        model.addAttribute("rows",
                hallService.findById(sessionService.findById(id)
                                .orElse(new Session()).getHallId())
                        .orElse(new Hall()).getRows());
        getUser(model, httpSession);
        return "selectRow";
    }

    /**
     * метод записывает данные о выбранном ряде и перенаправляет на выбор места
     * @param request - данные запроса от пользователя
     * @param httpSession - объект связанный с работой пользователя
     * @return - перенаправляет на url/formSelectCell
     */
    @PostMapping("/addRow")
    public String addRow(HttpServletRequest request, HttpSession httpSession) {
        httpSession.setAttribute("row", Integer.parseInt(request.getParameter("row")));
        return "redirect:/formSelectCell";
    }

    /**
     * метод возвращает вид с выбором места на сеанс
     * @param model - модель данных
     * @param httpSession - объект связанный с работой пользователя
     * @return - возвращает вид с выбором места
     */
    @GetMapping("/formSelectCell")
    public String selectCell(Model model, HttpSession httpSession) {
        model.addAttribute("cells",
                hallService.findById(((Session) httpSession.getAttribute("movie")).getHallId())
                        .orElse(new Hall()).getCells());
        getUser(model, httpSession);
        return "selectCell";
    }

    /**
     * метод записывает данные о выбранном месте и вызывает метод покупки билета
     * @param request - данные запроса от пользователя
     * @param httpSession - объект связанный с работой пользователя
     * @return - перенаправляет на url/createdTicket
     */
    @PostMapping("/addCell")
    public String addCell(HttpServletRequest request, HttpSession httpSession) {
        httpSession.setAttribute("cell", Integer.parseInt(request.getParameter("cell")));
        return "redirect:/createdTicket";
    }

    @GetMapping("/addSession")
    public String addSession(Model model, HttpSession httpSession) {
        getUser(model, httpSession);
        model.addAttribute("halls", hallService.findAll());
        System.out.println(hallService.findAll());
        return "addSession";
    }

    @PostMapping("/createSession")
    public String createSession(@ModelAttribute Session session) {
        sessionService.add(session);
        return "redirect:/cinema";
    }
}
