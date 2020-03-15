package gameOfLife;

class Rule {
    private int minContinueAlive, maxContinueAlive, becomeAlive;

    Rule(int minContinueAlive, int maxContinueAlive, int becomeAlive) {
        this.minContinueAlive = minContinueAlive;
        this.maxContinueAlive = maxContinueAlive;
        this.becomeAlive = becomeAlive;
    }

    boolean cellLives(boolean isAlive, int liveNeighbors) {
        if (isAlive)
            return liveNeighbors <= maxContinueAlive && liveNeighbors >= minContinueAlive;
        else
            return liveNeighbors == becomeAlive;
    }
}
