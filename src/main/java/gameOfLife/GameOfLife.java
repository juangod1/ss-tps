package gameOfLife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameOfLife {
    public static void main(String[] args) throws IOException {
        //generate2d();
        generate3d();
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
            M = 0;
            gameOfLife.FilePrinter.print2d(state, 1000,1000, M, M+2, M+1, 300, board);
            M = 1;
            gameOfLife.FilePrinter.print2d(state, 1000,1000, M, M+2, M+1, 300, board+1);
            M = 2;
            gameOfLife.FilePrinter.print2d(state, 1000,1000, M, M+2, M+1, 300, board+2);
            M = 3;
            gameOfLife.FilePrinter.print2d(state, 1000,1000, M, M+2, M+1, 300, board+3);
            M = 4;
            gameOfLife.FilePrinter.print2d(state, 1000,1000, M, M+2, M+1, 300, board+4);
            M = 5;
            gameOfLife.FilePrinter.print2d(state, 1000,1000, M, M+2, M+1, 300, board+5);
            board+=6;
        }
    }

    private static void generate3d() throws IOException {
        int board = 0;
        Random random = new Random();
        ArrayList<ArrayList<ArrayList<Integer>>> states3d = new ArrayList<>();
        double limit = 0.50;

        while (board < 30) {
            states3d.add(new ArrayList<>());
            for (int row=46; row<54; row++) {
                for (int col=46; col<54; col++) {
                    for (int depth=46; depth<54; depth++) {
                        if (random.nextDouble() > limit) {
                            ArrayList<Integer> point = new ArrayList<>();
                            point.add(row);
                            point.add(col);
                            point.add(depth);
                            states3d.get(board).add(point);
                        }
                    }
                }
            }
            if (board % 10 == 9)
                limit += 0.15;
            board++;
        }

        board = 0;
        int M;
        for (ArrayList<ArrayList<Integer>> state : states3d) {
            M = 0;
            gameOfLife.FilePrinter.print3d(state, 100,100, 100, M, M+2, M+1, 50, board);
            M = 1;
            gameOfLife.FilePrinter.print3d(state, 100,100, 100, M, M+2, M+1, 50, board+1);
            M = 2;
            gameOfLife.FilePrinter.print3d(state, 100,100, 100, M, M+2, M+1, 50, board+2);
            M = 3;
            gameOfLife.FilePrinter.print3d(state, 100,100, 100, M, M+2, M+1, 50, board+3);
            M = 4;
            gameOfLife.FilePrinter.print3d(state, 100,100, 100, M, M+2, M+1, 50, board+4);
            board+=5;
        }
//
//        int minAlive, maxAlive, becomeAlive;
//
//        minAlive=2;
//        maxAlive=3;
//        becomeAlive=3;
//        FilePrinter.print3d(board1,100,100,100, minAlive, maxAlive, becomeAlive, 50, 1);
//        FilePrinter.print3d(board2,100,100,100, minAlive, maxAlive, becomeAlive, 50, 2);
//        FilePrinter.print3d(board3,100,100,100, minAlive, maxAlive, becomeAlive, 50, 3);
//
//        minAlive=1;
//        maxAlive=6;
//        becomeAlive=4;
//        FilePrinter.print3d(board1,100,100,100, minAlive, maxAlive, becomeAlive, 50, 4);
//        FilePrinter.print3d(board2,100,100,100, minAlive, maxAlive, becomeAlive, 50, 5);
//        FilePrinter.print3d(board3,100,100,100, minAlive, maxAlive, becomeAlive, 50, 6);
//
//        minAlive=4;
//        maxAlive=5;
//        becomeAlive=4;
//        FilePrinter.print3d(board1,100,100,100, minAlive, maxAlive, becomeAlive, 50, 7);
//        FilePrinter.print3d(board2,100,100,100, minAlive, maxAlive, becomeAlive, 50, 8);
//        FilePrinter.print3d(board3,100,100,100, minAlive, maxAlive, becomeAlive, 50, 9);
    }
}
