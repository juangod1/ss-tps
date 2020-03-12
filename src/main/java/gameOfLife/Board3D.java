package gameOfLife;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Particle;
import util.Util;

import java.util.Arrays;

public class Board3D {
    private boolean board[][][];
    int minAlive, maxAlive, boardHeight, boardWidth, boardDepth, currentAlive;

    public boolean[][][] getBoard(){
        return board;
    }

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
    public Board3D(int[][] initialState, int boardHeight, int boardWidth, int boardDepth, int minAlive, int maxAlive){
        this.maxAlive = maxAlive;
        this.minAlive = minAlive;
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.boardDepth = boardDepth;
        this.currentAlive = initialState.length;

        board = new boolean[boardHeight][boardWidth][boardDepth];

        for(int[] cell : initialState){
            board[cell[0]][cell[1]][cell[2]] = true;
        }
    }

    public boolean[][][] iterate(){
        // todo: a chequear esto, funciona para 2d pero aca no se
        boolean[][][] clone = Arrays.stream(board).map(boolean[][]::clone).toArray(boolean[][][]::new);;
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
        return board;
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
        return liveNeighbors <= maxAlive && liveNeighbors >= minAlive;
    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder("");

        boardString.append(currentAlive);
        boardString.append("\n\n");

        for(int i=0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                for (int k = 0; k < boardDepth; k++) {
                    if (board[i][j][k]) {
                        boardString.append(i);
                        boardString.append(" ");
                        boardString.append(j);
                        boardString.append(" ");
                        boardString.append(k);
                        boardString.append("\n");
                    }
                }
            }
        }

        return  boardString.toString();
    }
}
