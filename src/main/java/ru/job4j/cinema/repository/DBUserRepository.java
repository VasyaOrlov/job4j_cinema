package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.*;
import java.util.Optional;

/**
 * DBUserRepository - реализует интерфейс UserRepository
 * Описывает модель хранилища пользователей в базе данных
 */
@Repository
@ThreadSafe
public class DBUserRepository implements UserRepository {

    private static final String ADD = "insert into users (username, password, "
            + "email) values (?, ?, ?)";
    private static final Logger LOG = LoggerFactory.getLogger(DBUserRepository.class.getName());
    private final BasicDataSource pool;

    public DBUserRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * метод добавляет пользователя в базу данных
     * @param user - пользователь
     * @return - Optional с пользователем или null
     */
    @Override
    public Optional<User> add(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
                return Optional.of(user);
            }
        } catch (SQLException e) {
            LOG.error("Ошибка соединения при добавлении пользователя", e);
        }
        return Optional.empty();
    }
}
