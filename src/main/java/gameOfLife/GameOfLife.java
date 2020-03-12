package gameOfLife;

import java.io.IOException;

public class GameOfLife {
    public static void main(String[] args) throws IOException {
        int[][] initialState = {
                {0,0,0},{1,0,0},{2,0,0}
        };

        FilePrinter.print3d(initialState, 4,4,4,2,3,20);
    }
}
