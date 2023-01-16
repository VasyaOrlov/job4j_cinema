package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.MemoryHallRepository;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.MemoryHallService;
import ru.job4j.cinema.service.SessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;

class SessionControllerTest {
    SessionService sessionService;
    HallService hallService = new MemoryHallService(new MemoryHallRepository());
    SessionController sessionController;

    @BeforeEach
    public void init() {
        sessionService = mock(SessionService.class);
        sessionController = new SessionController(sessionService, hallService);
    }

    @Test
    public void whenCinemaTest() {
        List<Session> sessions = Arrays.asList(
                new Session(1, "film1", 1),
                new Session(2, "film2", 2)
        );
        Model model = new ConcurrentModel();
        HttpSession httpsession = mock(HttpSession.class);
        User user = new User(1, "email", "pass", "name");

        when(sessionService.findAll()).thenReturn(sessions);
        when(httpsession.getAttribute("user")).thenReturn(user);

        String rsl = sessionController.cinema(model, httpsession);
        assertThat(sessions).isEqualTo(model.getAttribute("movies"));
        assertThat(rsl).isEqualTo("cinema");
        assertThat(model.getAttribute("user")).isEqualTo(user);
    }

    @Test
    public void whenSelectRowTest() {
        Session session = new Session(1, "film1", 1);
        User user = new User(1, "email", "pass", "name");
        Model model = new ConcurrentModel();
        HttpSession httpSession = mock(HttpSession.class);
        int id = 1;

        when(sessionService.findById(id)).thenReturn(Optional.of(session));
        when(httpSession.getAttribute("user")).thenReturn(user);

        String rsl = sessionController.selectRow(id, model, httpSession);
        List<Integer> rows = hallService.findById(session.getHallId()).get().getRows();
        assertThat(rsl).isEqualTo("selectRow");
        assertThat(model.getAttribute("rows"))
                .isEqualTo(rows);
        assertThat(model.getAttribute("user")).isEqualTo(user);
        verify(httpSession).setAttribute("movie", session);
    }

    @Test
    public void whenAddRowTest() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession httpSession = mock(HttpSession.class);

        when(req.getParameter("row")).thenReturn("1");

        String rsl = sessionController.addRow(req, httpSession);
        assertThat(rsl).isEqualTo("redirect:/formSelectCell");
        verify(httpSession).setAttribute("row", Integer.parseInt(req.getParameter("row")));
    }

    @Test
    public void whenSelectCellTest() {
        Session session = new Session(1, "film1", 1);
        User user = new User(1, "email", "pass", "name");
        Model model = new ConcurrentModel();
        HttpSession httpSession = mock(HttpSession.class);

        when(httpSession.getAttribute("user")).thenReturn(user);
        when(httpSession.getAttribute("movie")).thenReturn(session);

        String rsl = sessionController.selectCell(model, httpSession);
        List<Integer> cells = hallService.findById(session.getHallId()).get().getCells();
        assertThat(rsl).isEqualTo("selectCell");
        assertThat(model.getAttribute("cells"))
                .isEqualTo(cells);
        assertThat(model.getAttribute("user")).isEqualTo(user);
    }

    @Test
    public void whenAddCellTest() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession httpSession = mock(HttpSession.class);

        when(req.getParameter("cell")).thenReturn("1");

        String rsl = sessionController.addCell(req, httpSession);
        assertThat(rsl).isEqualTo("redirect:/createdTicket");
        verify(httpSession).setAttribute("cell", Integer.parseInt(req.getParameter("cell")));
    }

    @Test
    public void whenAddSessionTest() {
        Model model = new ConcurrentModel();
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User(1, "email", "pass", "name");

        when(httpSession.getAttribute("user")).thenReturn(user);

        String rsl = sessionController.addSession(model, httpSession);
        assertThat(rsl).isEqualTo("addSession");
        assertThat(model.getAttribute("user")).isEqualTo(user);
        assertThat(model.getAttribute("halls")).isEqualTo(hallService.findAll());
    }

    @Test
    public void whenCreatedSessionTest() {
        Session session = new Session(1, "film1", 1);

        String rsl = sessionController.createSession(session);
        assertThat(rsl).isEqualTo("redirect:/cinema");
    }
}