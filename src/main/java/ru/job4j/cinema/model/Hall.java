package ru.job4j.cinema.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Hall - описывает модель зала
 */
public class Hall {
    private int id;
    /**
     * rows - список рядов
     */
    private List<Integer> rows;
    /**
     * places - список мест
     */
    private List<Integer> cells;

    public Hall() {
    }

    public Hall(int id,  int rows, int cells) {
        this.id = id;
        this.rows = unitRows(rows);
        this.cells = unitCells(cells);
    }

    private List<Integer> unitRows(int rows) {
        var rsl = new ArrayList<Integer>();
        for (int i = 1; i <= rows; i++) {
            rsl.add(i);
        }
        return rsl;
    }

    private List<Integer> unitCells(int places) {
        var rsl = new ArrayList<Integer>();
        for (int i = 1; i <= places; i++) {
            rsl.add(i);
        }
        return rsl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getRows() {
        return rows;
    }

    public List<Integer> getCells() {
        return cells;
    }
}
