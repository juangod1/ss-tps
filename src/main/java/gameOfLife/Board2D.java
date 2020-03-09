package gameOfLife;

public class Board2D {
    private boolean board[][];
    int minAlive, maxAlive, boardHeight, boardWidth;

    private int[][] neighborDirections = {
            {-1,-1},{-1,0},{-1,1},
            {0,-1},{0,1},
            {1,-1},{1,0},{1,1}
    };

    // initial state is a vector of the coordinates of the living cells
    public Board2D(int[][] initialState, int boardHeight, int boardWidth, int minAlive, int maxAlive){
        this.maxAlive = maxAlive;
        this.minAlive = minAlive;
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;

        board = new boolean[boardHeight][boardWidth];

        for(int[] cell : initialState){
            board[cell[0]][cell[1]] = true;
        }
    }

    public boolean[][] iterate(){
        for(int i=0; i < boardHeight; i++){
            for(int j=0; j < boardWidth; j++){
                if(cellWillLive(i,j)){

                }
            }
        }
        return board;
    }

    private boolean cellWillLive(int row, int col){
        int rowAux, colAux, liveNeighbors=0;
        for(int[] direction : neighborDirections){
            rowAux = row;
            colAux = col;
            rowAux += direction[0];
            colAux += direction[1];

            if(board[rowAux % boardHeight][colAux % boardWidth]){
                liveNeighbors++;
            }
        }
        return liveNeighbors <= maxAlive && liveNeighbors >= minAlive;
    }
}
