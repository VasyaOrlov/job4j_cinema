package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.config.H2Configuration;
import ru.job4j.cinema.config.JdbcConfiguration;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class DBUserRepositoryTest {

    @AfterEach
    public void wipeTable() throws SQLException {
        BasicDataSource pool = new H2Configuration().loadPool();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement("delete from users")) {
            statement.execute();
        }
    }

    /**
     * метод проверяет метод добавления пользователя в хранилище
     */
    @Test
    public void whenAddTest() {
        DBUserRepository repository = new DBUserRepository(new H2Configuration().loadPool());
        User user = new User(1, "email1", "pass1", "name1");
        Optional<User> rsl = repository.add(user);
        assertThat(user).isEqualTo(rsl.get());
    }

    /**
     * метод проверяет поиск пользователя оп емайл и паролю
     */
    @Test
    public void whenFindByEmailAndPasswordTest() {
        DBUserRepository repository = new DBUserRepository(new H2Configuration().loadPool());
        User user = new User(1, "email1", "pass1", "name1");
        repository.add(user);
        assertThat(user).isEqualTo(repository.findUser("email1", "pass1").get());
    }
}