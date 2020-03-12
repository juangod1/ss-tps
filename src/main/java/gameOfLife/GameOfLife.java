package gameOfLife;

import java.io.IOException;

public class GameOfLife {
    public static void main(String[] args) throws IOException {
        int[][] initialState = {
                {50,50,50},{49,50,50},{49,49,50},{49,50,49},{50,50,49},{49,49,49}
        };

        gameOfLife.FilePrinter.print3d(initialState, 100,100,100,4,4,100);
    }
}
