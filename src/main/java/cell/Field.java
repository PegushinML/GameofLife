package cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Field {
    private Cell[][] currentState;
    private volatile Queue<Cell[]> cellRowQueue;

    public Field(int n) {
        this.currentState = new Cell[n][n];
        this.cellRowQueue = new ConcurrentLinkedQueue<>();
    }

    public void putRow(Cell[] cellRow) {
        if(cellRow != null) {
            this.currentState[cellRow[0].getRow()] = cellRow;
            this.cellRowQueue.offer(cellRow);
        } else {
            System.out.println("Nothing to update");
        }
    }

    public Cell[] updateCellRow() {
        Cell[] row = this.cellRowQueue.poll();
        if(row != null) {
            Cell[] newGen = new Cell[row.length];
            for(int i = 0; i < row.length; i++) {
                Cell cell = row[i];
                int lifeAround = 0;
                for (int k = cell.getRow() - 1; k < cell.getRow() + 2; k++) {
                    for (int j = cell.getColumn() - 1; j < cell.getColumn() + 2; j++) {
                        if (!cell.equals(currentState[getCorrectIndex(k)][getCorrectIndex(j)]) && currentState[getCorrectIndex(k)][getCorrectIndex(j)].isAlive()) {
                            lifeAround++;
                        }
                    }
                }

                if (!cell.isAlive() && lifeAround == 3) {
                    newGen[i] = cell.born();
                } else if (cell.isAlive()) {
                    if (lifeAround == 2 || lifeAround == 3) {
                        newGen[i] = cell;
                    } else {
                        newGen[i] = cell.die();
                    }
                } else
                    newGen[i] = cell;
            }
            return newGen;
        }
        return null;
    }


    public List<String> getResult() {
        List<String> result = new ArrayList<>();
        for(int i = 0; i < this.currentState.length; i++) {
            StringBuilder builder = new StringBuilder();
            for(int j = 0; j < this.currentState.length; j++) {
                if(this.currentState[i][j].isAlive()) {
                    builder.append(1);
                } else {
                    builder.append(0);
                }
            }
            result.add(builder.toString());
        }
        return result;
    }

    public boolean updateFinished() {
        return cellRowQueue.isEmpty();
    }

    public int getSize() {
        return this.currentState.length;
    }

    private int getCorrectIndex(int index) {
        if(index < 0) {
            return this.currentState.length - 1;
        }
        if(index == this.currentState.length) {
            return 0;
        }
        return index;
    }
}
