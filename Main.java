import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class starts gameProcess and makes report in report.txt
 */
public class Main {
    static GameProcess gameProcess;
    public static void main(String[] args) {
        long n = 100; //number of trials
        long numberOfWonGame = 0; //number of games where RRH winned
        long unsolvable = 0; //number of unsolvable maps
        long start; //initial time for running of program
        long totalTime = 0; //time of runnning n programs
        String toFile = ""; //output string that I will write to report.txt
        long time; //time of running 1 program
        long totalPathLength = 0; //sum of all path lengths
        for (int i = 0; i < n; i++) {
            start = System.nanoTime();
            gameProcess = new GameProcess();
            gameProcess.startGame();
            time = System.nanoTime() - start;
            toFile = toFile + "\nGame #" + i + "\n" + gameProcess.generatedMap + "Game winned: " + gameProcess.winningMap + " Time: " + Long.toString(time);
            toFile = toFile + " Path length: " + gameProcess.pathLength + "\n";
            totalTime += time;
            totalPathLength += gameProcess.pathLength;
            if (gameProcess.badMap) unsolvable++;
            if (gameProcess.winningMap) numberOfWonGame++;
        }
        toFile = toFile + "\nWon game: "+ numberOfWonGame + ", Unsolvable maps generated: " + unsolvable + "\n";
        toFile = toFile + "Algorithm can not solve %: " + (double)(n - numberOfWonGame - unsolvable) / (double)(n) * 100 + "\n";
        toFile = toFile + "Won game % : "+ ((double)(numberOfWonGame) / (double)(n))*100 + ", Bad maps generated %: "
                + ((double)(unsolvable) / (double)(n))*100 + ", Average path length: " + ((double)(totalPathLength) / (double)(n)) +"\n";
        writeFile(toFile + "Average time in nanoseconds: " + totalTime/n);
    }

    /**
     * Method writes line in report.txt
     * @param line what we need to write
     */
    private static void writeFile(String line) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("report.txt"));
            writer.write(line);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}