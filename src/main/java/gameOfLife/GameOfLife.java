package gameOfLife;

public class GameOfLife {
    public static void main(String[] args){
        int[][] initialState = {
                {0,0},{1,0},{2,0},{5,3},{2,2},{1,1}
        };

        Board2D board = new Board2D(initialState, 20,20,2,6);

        while(true){
            System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            board.iterate();
            System.out.println(board);
            try{Thread.sleep(500);} catch (InterruptedException e){}
        }
    }
}
