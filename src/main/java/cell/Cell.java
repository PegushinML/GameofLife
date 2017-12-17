package cell;

public class Cell {
    private final boolean status;
    private final int row;
    private final int column;

    public Cell(boolean status, int row, int column) {
        this.status = status;
        this.row = row;
        this.column = column;
    }


    int getRow() {
        return this.row;
    }

    int getColumn() {
        return this.column;
    }

    boolean isAlive() {
        return this.status;
    }

    Cell born() {
        return new Cell(true, this.row, this.column);
    }

    Cell die() {
        return new Cell(false, this.row, this.column);
    }
}
