import cell.Field;
import init.Initializer;

import java.io.File;

abstract class AbstractGame implements GameOfLife {
    private volatile Field currentState;
    private volatile Field nextState;
    private volatile int steps;

    protected void readData(String filename) {
        Initializer init = Initializer.initGame(new File(filename));
        this.currentState = init.getInitialField();
        this.nextState = new Field(currentState.getSize());
        this.steps = init.getSteps();
    }

    protected Field getCurrentState() {
        return this.currentState;
    }

    protected Field getNextState() {
        return this.nextState;
    }

    protected int stepsLeft() {
        return steps;
    }

    protected void goToNextStep() {
        System.out.println("New step!");
        this.currentState = this.nextState;
        this.nextState = new Field(currentState.getSize());
        this.steps--;
    }

    protected boolean isGameOver() {
        if(this.steps < 1) {
            return true;
        } else {
            return false;
        }
    }
}
