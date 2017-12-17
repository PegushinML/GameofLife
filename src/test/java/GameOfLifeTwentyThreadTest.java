import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class GameOfLifeTwentyThreadTest {
    private GameOfLife gameOfLife;
    public static Writer output;

    @BeforeAll
    public static void initWriter() throws IOException {
        output = new FileWriter(new File("results/twenty.csv"));
        output.write("fileName;time\n");
    }

    @BeforeEach
    public void before() {
        gameOfLife = new MultithreadedGame(20);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "100", "1000"})
    public void testGame(String name) throws Exception {
        testOneGame("resources/input" + name + ".txt", "resources/output" + name + ".txt");
    }

    @AfterAll
    public static void close() throws IOException {
        output.close();
    }

    private void testOneGame(String inputFile, String expectedOutputFile) throws IOException {
        long startTime = System.nanoTime();
        List<String> result = gameOfLife.play(inputFile);
        long elapsedTime = System.nanoTime() - startTime;
        output.write(inputFile + ";" + elapsedTime + "\n");
        assertEquals(readFile(expectedOutputFile), result);
    }

    private static List<String> readFile(String fileName) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();

        Scanner scan = new Scanner(new File(fileName));
        while (scan.hasNextLine()) {
            lines.add(scan.nextLine());
        }
        scan.close();

        return lines;
    }
}
