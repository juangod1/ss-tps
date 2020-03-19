package gameOfLife;

import util.Util;
import java.util.ArrayList;

public class Board3D extends Board {
    private boolean[][][] board;
    private double currentRadius;

    private int[][] neighborDirections = {
            {-1,-1,-1},{-1,0,-1},{-1,1,-1},
            {0,-1,-1},{0,0,-1},{0,1,-1},
            {1,-1,-1},{1,0,-1},{1,1,-1},

            {-1,-1,0},{-1,0,0},{-1,1,0},
            {0,-1,0},       {0,1,0},
            {1,-1,0},{1,0,0},{1,1,0},

            {-1,-1,1},{-1,0,1},{-1,1,1},
            {0,-1,1},{0,0,1},{0,1,1},
            {1,-1,1},{1,0,1},{1,1,1}
    };

    // initial state is a vector of the coordinates of the living cells
    Board3D(ArrayList<ArrayList<Integer>> initialState, int boardHeight, int boardWidth, int boardDepth, int minAlive, int maxAlive, int becomeAlive){
        this.rule = new Rule(minAlive, maxAlive, becomeAlive);
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.boardDepth = boardDepth;
        this.currentAlive = initialState.size();
        this.currentRadius = 0;

        board = new boolean[boardHeight][boardWidth][boardDepth];
        double auxRadius;

        for(ArrayList<Integer> cell : initialState){
            board[cell.get(0)][cell.get(1)][cell.get(2)] = true;
            auxRadius = Math.sqrt(Math.pow(cell.get(0)-((double)boardHeight/2),2)+Math.pow(cell.get(1)-((double)boardWidth/2),2)+Math.pow(cell.get(2)-((double)boardDepth/2),2));
            if (auxRadius > currentRadius)
                currentRadius = auxRadius;
        }
    }

    double getCurrentRadius() {
        return currentRadius;
    }

    void iterate(){
        boolean[][][] clone = Util.clone3dArray(board,boardHeight,boardWidth,boardDepth);
        double auxRadius;
        currentAlive = 0;
        currentRadius = 0;
        for(int i=0; i < boardHeight; i++){
            for(int j=0; j < boardWidth; j++){
                for(int k=0; k < boardDepth; k++){
                    if(cellWillLive(i,j,k)){
                        clone[i][j][k] = true;
                        currentAlive++;
                        auxRadius = Math.sqrt(Math.pow(i-((double)boardHeight/2),2)+Math.pow(j-((double)boardWidth/2),2)+Math.pow(k-((double)boardDepth/2),2));
                        if (auxRadius > currentRadius)
                            currentRadius = auxRadius;
                    } else {
                        clone[i][j][k] = false;
                    }
                }
            }
        }

        board = clone;
    }

    private boolean cellWillLive(int row, int col, int depth){
        int rowAux, colAux, depthAux, liveNeighbors=0;
        for(int[] direction : neighborDirections){
            rowAux = row;
            colAux = col;
            depthAux = depth;
            rowAux += direction[0];
            colAux += direction[1];
            depthAux += direction[2];

            if(board[Util.naturalModulus(rowAux,boardHeight)]
                    [Util.naturalModulus(colAux,boardWidth)]
                    [Util.naturalModulus(depthAux,boardDepth)]){
                liveNeighbors++;
            }
        }
        return rule.cellLives(board[row][col][depth], liveNeighbors);
    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();

        boardString.append(currentAlive);
        boardString.append("\n\n");

        for(int i=0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                for (int k = 0; k < boardDepth; k++) {
                    if (board[i][j][k]) {
                        boardString.append(j).append(" ").append(i).append(" ").append(k).append(" 0.25").append("\n");
                    }
                }
            }
        }

        return  boardString.toString();
    }
}
