package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.config.H2Configuration;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import static org.assertj.core.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

class DBTicketRepositoryTest {
    private BasicDataSource pool;
    private DBSessionRepository sessionRepository;
    private DBUserRepository userRepository;


    @BeforeEach
    void init() {
        pool = new H2Configuration().loadPool();
        sessionRepository = new DBSessionRepository(pool);
        userRepository = new DBUserRepository(pool);
    }

    @AfterEach
    public void wipeTable() throws SQLException {
        BasicDataSource pool = new H2Configuration().loadPool();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement1 = connection.prepareStatement("delete from tickets");
             PreparedStatement statement2 = connection.prepareStatement("delete from users");
             PreparedStatement statement3 = connection.prepareStatement("delete from sessions")
             ) {
            statement1.execute();
            statement2.execute();
            statement3.execute();
        }
    }

    /**
     * метод проверки добавления билета в хранилище
     */
    @Test
    public void whenAddTest() {

        DBTicketRepository repository =
                new DBTicketRepository(pool);

        Session session = new Session(1, "session", 1);
        Optional<Session> optionalSession = sessionRepository.add(session);

        User user = new User(1, "email1", "pass1", "name1");
        Optional<User> optionalUser = userRepository.add(user);

        Ticket ticket = new Ticket(1, 4, 4,
                optionalSession.get().getId(), optionalUser.get().getId());
        Optional<Ticket> rsl = repository.add(ticket);

        ticket.setId(rsl.get().getId());
        assertThat(ticket).isEqualTo(rsl.get());
    }
}