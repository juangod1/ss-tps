package gameOfLife;

import java.io.FileWriter;
import java.io.IOException;

public class FilePrinter {
    public static void print3d(int[][] initialStateint, int boardHeight, int boardWidth, int boardDepth, int minAlive, int maxAlive, int generations) throws IOException {
        Board3D board = new Board3D(initialStateint, boardHeight, boardWidth, boardDepth, minAlive, maxAlive);
        FileWriter f = new FileWriter("./output");

        while(generations--!=0){
            f.append(board.toString());
            board.iterate();
        }
        f.close();
    }
}
