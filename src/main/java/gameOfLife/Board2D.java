package gameOfLife;

import util.Util;

import java.util.Arrays;

public class Board2D extends Board {
    private boolean[][] board;

    private int[][] neighborDirections = {
            {-1,-1},{-1,0},{-1,1},
            {0,-1},{0,1},
            {1,-1},{1,0},{1,1}
    };

    // initial state is a vector of the coordinates of the living cells
    Board2D(int[][] initialState, int boardHeight, int boardWidth, int minAlive, int maxAlive, int becomeAlive){
        this.rule = new Rule(minAlive, maxAlive, becomeAlive);
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.currentAlive = initialState.length;

        board = new boolean[boardHeight][boardWidth];

        for(int[] cell : initialState){
            board[cell[0]][cell[1]] = true;
        }
    }

    int getCurrentAlive() {
        return currentAlive;
    }

    void iterate() {
        boolean[][] clone = Arrays.stream(board).map(boolean[]::clone).toArray(boolean[][]::new);
        currentAlive = 0;

        for(int i=0; i < boardHeight; i++){
            for(int j=0; j < boardWidth; j++){
                if(cellWillLive(i,j)){
                    clone[i][j] = true;
                    currentAlive++;
                } else {
                    clone[i][j] = false;
                }
            }
        }
        board = clone;
    }

    private boolean cellWillLive(int row, int col){
        int rowAux, colAux, liveNeighbors=0;
        for(int[] direction : neighborDirections){
            rowAux = row;
            colAux = col;
            rowAux += direction[0];
            colAux += direction[1];

            if(board[Util.naturalModulus(rowAux,boardHeight)][Util.naturalModulus(colAux,boardWidth)]){
                liveNeighbors++;
            }
        }
        return rule.cellLives(board[row][col], liveNeighbors);
    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();

        boardString.append(currentAlive);
        boardString.append("\n\n");

        for(int i=0; i < boardHeight; i++){
            for(int j=0; j < boardWidth; j++){
                if (board[i][j]) {
                    boardString.append(j).append(" ").append(i).append(" 0.25").append("\n");
                }
            }
        }

        return boardString.toString();
    }
}
