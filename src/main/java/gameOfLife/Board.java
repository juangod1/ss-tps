package gameOfLife;

public abstract class Board {
    int boardHeight, boardWidth, boardDepth, currentAlive;
    Rule rule;

    abstract void iterate();

    public abstract String toString();
}
