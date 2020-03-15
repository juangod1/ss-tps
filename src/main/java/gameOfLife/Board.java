package gameOfLife;

public abstract class Board {
    int minAlive, maxAlive, boardHeight, boardWidth, boardDepth, currentAlive;

    abstract void iterate();

    public abstract String toString();
}
