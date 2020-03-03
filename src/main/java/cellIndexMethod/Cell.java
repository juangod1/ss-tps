package cellIndexMethod;

import particle.Particle;

import java.util.List;

public class Cell {
    private int row;
    private int column;
    private List<Particle> particles;

    public Cell(int row, int column, List<Particle> particles){
        this.row = row;
        this.column = column;
        this.particles = particles;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
