package gameOfLife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameOfLife {
    public static void main(String[] args) throws IOException {

        int board = 0;
        Random random = new Random();
        ArrayList<ArrayList<ArrayList<Integer>>> states2d = new ArrayList<>();

        while (board < 5) {
            states2d.add(new ArrayList<>());
            for (int row=496; row<504; row++) {
                for (int col=496; col<504; col++) {
                    if (random.nextBoolean()) {
                        ArrayList<Integer> point = new ArrayList<>();
                        point.add(row);
                        point.add(col);
                        states2d.get(board).add(point);
                    }
                }
            }
            board++;
        }

        board = 0;
        for (ArrayList<ArrayList<Integer>> state : states2d) {
            gameOfLife.FilePrinter.print2d(state, 1000,1000, 2, 3, 1, 300, board);
            gameOfLife.FilePrinter.print2d(state, 1000,1000, 2, 3, 2, 300, board+1);
            gameOfLife.FilePrinter.print2d(state, 1000,1000, 1, 2, 3, 300, board+2);
            gameOfLife.FilePrinter.print2d(state, 1000,1000, 1, 3, 2, 300, board+3);
            gameOfLife.FilePrinter.print2d(state, 1000,1000, 0, 2, 3, 300, board+4);
            gameOfLife.FilePrinter.print2d(state, 1000,1000, 1, 3, 4, 300, board+5);
            board+=6;
        }
    }
}
