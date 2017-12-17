import java.util.List;

public class SinglethreadedGame extends AbstractGame {
    @Override
    public List<String> play(String inputFile) {
        readData(inputFile);
        while (!isGameOver()) {
            while (!getCurrentState().updateFinished()) {
                getNextState().putRow(getCurrentState().updateCellRow());
            }
            goToNextStep();
        }
        return getCurrentState().getResult();
    }
}
