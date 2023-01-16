package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.*;

class TicketControllerTest {
    TicketService ticketService;
    TicketController ticketController;

    @BeforeEach
    public void init() {
        ticketService = mock(TicketService.class);
        ticketController = new TicketController(ticketService);
    }

    @Test
    public void whenCreatedTicketSuccessTest() {
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User(1, "email", "pass", "name");
        Ticket ticket = new Ticket(1, 1, 1, 1, 1);
        Session session = new Session(1, "film", 1);
        Optional<Ticket> optionalTicket = Optional.of(ticket);

        when(ticketService.add(any(Ticket.class))).thenReturn(optionalTicket);
        when(httpSession.getAttribute("row")).thenReturn(ticket.getRow());
        when(httpSession.getAttribute("cell")).thenReturn(ticket.getCell());
        when(httpSession.getAttribute("movie")).thenReturn(session);
        when(httpSession.getAttribute("user")).thenReturn(user);

        String rsl = ticketController.createdTicket(httpSession);
        assertThat(rsl).isEqualTo("redirect:/ticketSuccess");
    }

    @Test
    public void whenCreatedTicketFailTest() {
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User(1, "email", "pass", "name");
        Ticket ticket = new Ticket(1, 1, 1, 1, 1);
        Session session = new Session(1, "film", 1);

        when(httpSession.getAttribute("row")).thenReturn(ticket.getRow());
        when(httpSession.getAttribute("cell")).thenReturn(ticket.getCell());
        when(httpSession.getAttribute("movie")).thenReturn(session);
        when(httpSession.getAttribute("user")).thenReturn(user);
        when(ticketService.add(any(Ticket.class))).thenReturn(Optional.empty());

        String rsl = ticketController.createdTicket(httpSession);
        assertThat(rsl).isEqualTo("redirect:/ticketFail");
    }

    @Test
    public void whenTicketSuccessTest() {
        Model model = new ConcurrentModel();
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User(1, "email", "pass", "name");
        Ticket ticket = new Ticket(1, 1, 1, 1, 1);
        Session session = new Session(1, "film", 1);

        when(ticketService.add(any(Ticket.class))).thenReturn(Optional.of(ticket));
        when(httpSession.getAttribute("row")).thenReturn(ticket.getRow());
        when(httpSession.getAttribute("cell")).thenReturn(ticket.getCell());
        when(httpSession.getAttribute("movie")).thenReturn(session);
        when(httpSession.getAttribute("user")).thenReturn(user);

        String rsl = ticketController.ticketSuccess(model, httpSession);
        assertThat(rsl).isEqualTo("ticketSuccess");
        assertThat(model.getAttribute("user")).isEqualTo(user);
        assertThat(model.getAttribute("row")).isEqualTo(ticket.getRow());
        assertThat(model.getAttribute("cell")).isEqualTo(ticket.getCell());
        assertThat(model.getAttribute("movie")).isEqualTo(session);
    }

    @Test
    public void whenTicketFailTest() {
        Model model = new ConcurrentModel();
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User(1, "email", "pass", "name");

        when(httpSession.getAttribute("user")).thenReturn(user);

        String rsl = ticketController.ticketFail(model, httpSession);
        assertThat(rsl).isEqualTo("ticketFail");
        assertThat(model.getAttribute("user")).isEqualTo(user);
    }

}