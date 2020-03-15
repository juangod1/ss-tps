package gameOfLife;

import java.io.IOException;

public class GameOfLife {
    public static void main(String[] args) throws IOException {
        int[][] initialState3d = {
                {50,50,50},{49,50,50},{49,49,50},{49,50,49},{50,50,49},{49,49,49}
        };
//        int[][] initialState2d = {
//                {50,50},{51,50},{52,50},{53,50},{50,53},{51,53},{52,53},{53,53}
//        };
        int[][] initialState2d = {
                {51,50},{50,51},{51,51},{50,52},{51,52},{50,53}
        };
//        int[][] initialState2d = {
//                {50,51},{50,52},{51,50},{51,53},{52,50},{52,53},{53,51},{53,52}
//        };
//        int[][] initialState2d = {
//                {50,51},{51,51},{52,51}
//        };

//        gameOfLife.FilePrinter.print3d(initialState3d, 100,100,100,4,4,100);
//        gameOfLife.FilePrinter.print2d(initialState2d, 100,100, 0, 1, 3, 200, 0);
//        gameOfLife.FilePrinter.print2d(initialState2d, 100,100, 1, 2, 3, 200, 1);
        gameOfLife.FilePrinter.print2d(initialState2d, 100,100, 2, 3, 3, 200, 2);
//        gameOfLife.FilePrinter.print2d(initialState2d, 100,100, 3, 4, 3, 200, 3);
//        gameOfLife.FilePrinter.print2d(initialState2d, 100,100, 4, 5, 3, 200, 4);
//        gameOfLife.FilePrinter.print2d(initialState2d, 100,100, 5, 6, 3, 200, 5);

    }
}
