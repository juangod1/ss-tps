package gameOfLife;

import util.Util;

public class Board3D extends Board {
    private boolean[][][] board;

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
    Board3D(int[][] initialState, int boardHeight, int boardWidth, int boardDepth, int minAlive, int maxAlive, int becomeAlive){
        this.rule = new Rule(minAlive, maxAlive, becomeAlive);
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.boardDepth = boardDepth;
        this.currentAlive = initialState.length;

        board = new boolean[boardHeight][boardWidth][boardDepth];

        for(int[] cell : initialState){
            board[cell[0]][cell[1]][cell[2]] = true;
        }
    }

    int getCurrentAlive() {
        return currentAlive;
    }

    void iterate(){
        boolean[][][] clone = Util.clone3dArray(board,boardHeight,boardWidth,boardDepth);
        currentAlive = 0;
        for(int i=0; i < boardHeight; i++){
            for(int j=0; j < boardWidth; j++){
                for(int k=0; k < boardDepth; k++){
                    if(cellWillLive(i,j,k)){
                        clone[i][j][k] = true;
                        currentAlive++;
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
