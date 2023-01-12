package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Ticket - описывает модель билета на сеанс в кино
 */
public class Ticket {
    private int id;
    private int row;
    private int cell;
    private int sessionId;
    private int userId;
    public Ticket() {
    }

    public Ticket(int id, int row, int cell, int sessionId, int userId) {
        this.id = id;
        this.row = row;
        this.cell = cell;
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
