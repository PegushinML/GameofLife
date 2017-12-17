import cell.Cell;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Thread.currentThread;

public class MultithreadedGame extends AbstractGame {
    private volatile boolean[] activity;
    private Set<Thread> threads;

    public MultithreadedGame(int threadNum) {
        this.activity = new boolean[threadNum];
        this.threads = initThreads(threadNum);
    }

    private Set<Thread> initThreads(int threadNum) {
        Set<Thread> threadSet = new HashSet<>();
        for (int i = 0; i < threadNum; i++) {
            int finalI = i;
            threadSet.add(new Thread(() -> {
                while(!currentThread().interrupted()) {
                    if (activity[finalI]) {
                        if (!getCurrentState().updateFinished()) {
                            Cell[] cellRow = getCurrentState().updateCellRow();
                            getNextState().putRow(cellRow);
                        } else {
                            activity[finalI] = false;
                        }
                    }
                }
            }));
        }
        return threadSet;
    }
    @Override
    public List<String> play(String inputFile) {
        readData(inputFile);
        startThreads();
        while(!isGameOver()) {
            startActivity();
            while (checkActivity()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            goToNextStep();
        }

        for(Thread thread : threads) {
            thread.interrupt();
        }

        return getCurrentState().getResult();
    }

    private void startThreads() {
        for(Thread thread : threads) {
            thread.start();
        }
    }

    private void startActivity() {
        for(int i = 0; i < activity.length; i++) {
            activity[i] = true;
        }
    }

    private boolean checkActivity() {
        for(boolean bool : activity) {
            if(bool) {
                return true;
            }
        }
        return false;
    }
}
