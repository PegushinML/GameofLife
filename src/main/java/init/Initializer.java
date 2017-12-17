package init;

import cell.Cell;
import cell.Field;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Initializer {
    private final int stepsAmount;
    private final Field initialField;

    private Initializer(int stepsAmount, Field initialField) {
        this.stepsAmount = stepsAmount;
        this.initialField = initialField;
    }

    public static Initializer initGame(File input) {
        try {
            Scanner in = new Scanner(input);
            int n = in.nextInt();
            int m = in.nextInt();
            System.out.println("N is " + n + " and M is " + m);

            Field gameField = new Field(n);
            for(int i = 0; i < n; i++) {
                char[] chars = in.next().toCharArray();
                Cell[] cellRow = new Cell[n];
                for(int j = 0; j < n; j++) {
                    cellRow[j] = new Cell(chars[j] == '1', i, j);
                }
                gameField.putRow(cellRow);
            }
            return new Initializer(m, gameField);
        } catch (FileNotFoundException e) {
            System.out.println("Incorrect inputFile passed");
        }
        return null;
    }

    public Field getInitialField() {
        return this.initialField;
    }

    public int getSteps() {
        return this.stepsAmount;
    }
}
