package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.config.JdbcConfiguration;
import ru.job4j.cinema.model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class DBSessionRepositoryTest {

    @AfterEach
    public void wipeTable() throws SQLException {
        BasicDataSource pool = new JdbcConfiguration().loadPool();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement("delete from sessions")) {
            statement.execute();
        }
    }

    /**
     * метод проверки добавления фильма в хранилище
     */
    @Test
    public void whenAddTest() {
        DBSessionRepository repository = new DBSessionRepository(new JdbcConfiguration().loadPool());
        Session session = new Session(1, "session", 1);
        Optional<Session> rsl = repository.add(session);
        assertThat(rsl.get()).isEqualTo(session);
    }

    /**
     * метод проверки поиска фильма по id в хранилище
     */
    @Test void whenFindByIdTest() {
        DBSessionRepository repository = new DBSessionRepository(new JdbcConfiguration().loadPool());
        Session session = new Session(1, "session", 1);
        Optional<Session> rsl = repository.add(session);
        assertThat(rsl.get()).isEqualTo(repository.findById(rsl.get().getId()).get());
    }

    /**
     * метод проверки поиска всех фильмов в хранилище
     */
    @Test
    public void whenFindAllTest() {
        DBSessionRepository repository = new DBSessionRepository(new JdbcConfiguration().loadPool());
        Session session1 = new Session(1, "session1", 1);
        Session session2 = new Session(1, "session2", 1);
        Session session3 = new Session(1, "session3", 1);
        Optional<Session> rsl1 = repository.add(session1);
        Optional<Session> rsl2 = repository.add(session2);
        Optional<Session> rsl3 = repository.add(session3);
        List<Session> rsl = List.of(rsl1.get(), rsl2.get(), rsl3.get());
        assertThat(rsl).isEqualTo(repository.findAll());
    }
}