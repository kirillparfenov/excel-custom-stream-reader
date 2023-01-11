package parser.row;

import parser.cell.Cell;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private Integer index;
    private List<Cell> cells = new ArrayList<>();

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public List<Cell> getCells() {
        return cells;
    }

    @Override
    public String toString() {
        return "Row{" +
                "index=" + index +
                ", cells=" + cells +
                '}';
    }
}
