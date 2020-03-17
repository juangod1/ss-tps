package gameOfLife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameOfLife {
    public static void main(String[] args) throws IOException {
        //generate2d();
        //generate3d();
    }

    private static void generate2d() throws IOException {
        int board = 0;
        Random random = new Random();
        ArrayList<ArrayList<ArrayList<Integer>>> states2d = new ArrayList<>();
        double limit = 0.20;

        while (board < 40) {
            states2d.add(new ArrayList<>());
            for (int row=496; row<504; row++) {
                for (int col=496; col<504; col++) {
                    if (random.nextDouble() > limit) {
                        ArrayList<Integer> point = new ArrayList<>();
                        point.add(row);
                        point.add(col);
                        states2d.get(board).add(point);
                    }
                }
            }
            if (board % 10 == 9)
                limit += 0.15;
            board++;
        }

        board = 0;
        int M;
        for (ArrayList<ArrayList<Integer>> state : states2d) {
            M = 2;
            gameOfLife.FilePrinter.print2d(state, 1000,1000, M, M+2, M+1, 300, board);
            M = 3;
            gameOfLife.FilePrinter.print2d(state, 1000,1000, M, M+2, M+1, 300, board+1);
            M = 1;
            gameOfLife.FilePrinter.print2d(state, 1000,1000, M, M+2, M+1, 300, board+2);
            board+=3;
        }
    }

    private static void generate3d() throws IOException {
        int[][] board1 = {
                {49,49,49},{49,50,49},{49,51,49},
                {50,49,49},{50,50,49},{50,51,49},
                {51,49,49},{51,50,49},{51,51,49},

                {49,49,50},{49,50,50},{49,51,50},
                {50,49,50},{50,50,50},{50,51,50},
                {51,49,50},{51,50,50},{51,51,50},

                {49,49,51},{49,50,51},{49,51,51},
                {50,49,51},{50,50,51},{50,51,51},
                {51,49,51},{51,50,51},{51,51,51}
        };

        int[][] board2 = {
                /*{49,49,49},*/{49,50,49},{49,51,49},
                /*{50,49,49},*/{50,50,49},{50,51,49},
                /*{51,49,49},{51,50,49},*/{51,51,49},
/*
                {49,49,50},{49,50,50},{49,51,50},
                {50,49,50},{50,50,50},{50,51,50},
                {51,49,50},{51,50,50},{51,51,50},*/

                {49,49,51},/*{49,50,51},{49,51,51},*/
                {50,49,51},{50,50,51},/*{50,51,51},*/
                {51,49,51},{51,50,51},/*{51,51,51}*/
        };

        int[][] board3 = {
                {49,49,49}/*,{49,50,49}*/,{49,51,49},
                {50,49,49}/*,{50,50,49}*/,{50,51,49},
                {51,49,49}/*,{51,50,49}*/,{51,51,49},

                {49,49,50}/*,{49,50,50}*/,{49,51,50},
                {50,49,50}/*,{50,50,50}*/,{50,51,50},
                {51,49,50}/*,{51,50,50}*/,{51,51,50},

                {49,49,51}/*,{49,50,51}*/,{49,51,51},
                {50,49,51}/*,{50,50,51}*/,{50,51,51},
                {51,49,51}/*,{51,50,51}*/,{51,51,51}
        };
        int minAlive, maxAlive, becomeAlive;

        minAlive=2;
        maxAlive=3;
        becomeAlive=3;
        FilePrinter.print3d(board1,100,100,100, minAlive, maxAlive, becomeAlive, 50, 1);
        FilePrinter.print3d(board2,100,100,100, minAlive, maxAlive, becomeAlive, 50, 2);
        FilePrinter.print3d(board3,100,100,100, minAlive, maxAlive, becomeAlive, 50, 3);

        minAlive=1;
        maxAlive=6;
        becomeAlive=4;
        FilePrinter.print3d(board1,100,100,100, minAlive, maxAlive, becomeAlive, 50, 4);
        FilePrinter.print3d(board2,100,100,100, minAlive, maxAlive, becomeAlive, 50, 5);
        FilePrinter.print3d(board3,100,100,100, minAlive, maxAlive, becomeAlive, 50, 6);

        minAlive=4;
        maxAlive=5;
        becomeAlive=4;
        FilePrinter.print3d(board1,100,100,100, minAlive, maxAlive, becomeAlive, 50, 7);
        FilePrinter.print3d(board2,100,100,100, minAlive, maxAlive, becomeAlive, 50, 8);
        FilePrinter.print3d(board3,100,100,100, minAlive, maxAlive, becomeAlive, 50, 9);
    }
}
