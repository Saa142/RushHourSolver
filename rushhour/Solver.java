package rushhour;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Solver
 */
public class Solver {
    public static void solveFromFile(String inputPath, String outputPath) throws IOException {
        PrintWriter writer = new PrintWriter(outputPath, "UTF-8");
        Algorithm algorithm = new Algorithm();
        Board board = new Board(inputPath);
        String s;
        s = algorithm.BFS(board); // In this statement we can change the algorithm and see the differences.
        writer.write(s);
        writer.close();
    }
}