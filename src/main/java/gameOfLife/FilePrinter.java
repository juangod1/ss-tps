package gameOfLife;

import java.io.FileWriter;
import java.io.IOException;

public class FilePrinter {
    public static void print3d(int[][] initialStateint, int boardHeight, int boardWidth, int boardDepth, int minAlive, int maxAlive, int becomeAlive, int generations) throws IOException {
        Board3D board = new Board3D(initialStateint, boardHeight, boardWidth, boardDepth, minAlive, maxAlive, becomeAlive);
        FileWriter f = new FileWriter("./output3d");
        FileWriter table = new FileWriter("./table3d");
        int generation = 0;

        while(generations--!=0) {
            f.append(board.toString());
            table.append(String.valueOf(generation)).append(", ").append(String.valueOf(board.getCurrentAlive())).append("\n");
            board.iterate();
        }

        f.close();
        table.close();
    }

    public static void print2d(int[][] initialStateint, int boardHeight, int boardWidth, int minAlive, int maxAlive, int becomeAlive, int generations, int iteration) throws IOException {
        Board2D board = new Board2D(initialStateint, boardHeight, boardWidth, minAlive, maxAlive, becomeAlive);
        FileWriter f = new FileWriter("./output2d" + iteration);
        FileWriter table = new FileWriter("./table2d" + iteration);
        int generation = 0;

        while(generations--!=0) {
            f.append(board.toString());
            table.append(String.valueOf(generation)).append(", ").append(String.valueOf(board.getCurrentAlive())).append("\n");
            generation++;
            board.iterate();
        }
        f.close();
        table.close();
    }
}
