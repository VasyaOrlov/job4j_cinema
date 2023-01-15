package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * DBTicketRepository - реализует интерфейс TicketRepository
 * Описывает модель хранилища списка билетов в базе данных
 */
@Repository
@ThreadSafe
public class DBTicketRepository implements TicketRepository {

    private static final String ADD = "insert into tickets (session_id, row_ses, "
            + "cell, user_id) values (?, ?, ?, ?)";
    private static final Logger LOG = LoggerFactory.getLogger(DBTicketRepository.class.getName());
    private final BasicDataSource pool;


    public DBTicketRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * метод добавляет билет в базу данных
     * @param ticket - билет
     * @return - возвращает Optional с добавленным билетом или с null
     */
    @Override
    public Optional<Ticket> add(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUserId());
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ticket.setId(rs.getInt(1));
                }
                return Optional.of(ticket);
            }
        } catch (SQLException e) {
            LOG.error("Ошибка соединения при добавлении билета:", e);
        }
        return Optional.empty();
    }
}
