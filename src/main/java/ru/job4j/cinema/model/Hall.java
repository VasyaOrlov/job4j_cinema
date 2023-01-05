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

    private List<List<Integer>> places;

    public Hall() {
    }

    public Hall(int id,  int rows, int cells) {
        this.id = id;
        this.rows = unitRows(rows);
        this.cells = unitCells(cells);
        this.places = unitPlaces(rows, cells);
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

    private List<List<Integer>> unitPlaces(int rows, int cells) {
        var rsl = new ArrayList<List<Integer>>();
        for (int i = 1; i <= rows; i++) {
            var temp = new ArrayList<Integer>();
            for (int j = 1; j <= cells; j++) {
                temp.add(j);
            }
            rsl.add(temp);
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

    public List<List<Integer>> getPlaces() {
        return places;
    }

    public void setPlaces(List<List<Integer>> places) {
        this.places = places;
    }
}
