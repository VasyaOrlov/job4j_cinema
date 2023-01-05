package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Ticket - описывает модель билета на сеанс в кино
 */
public class Ticket {
    private int id;
    private int row;
    private int place;
    private int sessionId;

    public Ticket() {
    }

    public Ticket(int id, int row, int place, int sessionId) {
        this.id = id;
        this.row = row;
        this.place = place;
        this.sessionId = sessionId;
    }

    public Ticket(int id, int row, int place) {
        this.id = id;
        this.row = row;
        this.place = place;
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

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
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
