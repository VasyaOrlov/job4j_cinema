package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * класс DBSessionRepository реализует интерфейс SessionRepository
 * класс реализует хранилище сеансов на фильм в базе данных
 */
@Repository
@ThreadSafe
public class DBSessionRepository implements SessionRepository {

    private static final String FIND_ALL = "SELECT * FROM sessions";
    private static final String FIND_BY_ID = "SELECT * FROM sessions "
            + "WHERE id = ?";
    private static final String ADD = "insert into sessions (name, hall_id)"
            + "values (?, ?)";
    private static final Logger LOG = LoggerFactory.getLogger(DBSessionRepository.class.getName());
    private final BasicDataSource pool;

    public DBSessionRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * метод находит все сеансы в базе данных
     * @return - список всех сеансов
     */
    @Override
    public List<Session> findAll() {
        List<Session> rsl = new ArrayList<>();
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(FIND_ALL)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rsl.add(new Session(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("hall_id")
                    ));
                }
            }
        } catch (SQLException e) {
            LOG.error("Ошибка соединения при поиске всех сеансов", e);
        }
        return rsl;
    }

    /**
     * метод находит сеанс по id в базе данных
     * @param id -индификатор сеанса
     * @return - Optional с сеансом или null
     */
    @Override
    public Optional<Session> findById(int id) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Session(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("hall_id")
                    ));
                }
            }
        } catch (SQLException e) {
            LOG.error("Ошибка соединения при поиске сеанса по id", e);
        }
        return Optional.empty();
    }

    /**
     * Метод добавляет сеанс в базу данных
     * @param session - сеанс
     * @return - возвращает Optional с добавленным сеансом или с null
     */
    @Override
    public Optional<Session> add(Session session) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, session.getName());
            ps.setInt(2, session.getHallId());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    session.setId(rs.getInt(1));
                    return Optional.of(session);
                }
            }
        } catch (SQLException e) {
            LOG.error("Ошибка соединения при добавлении сеанса", e);
        }
        return Optional.empty();
    }
}
