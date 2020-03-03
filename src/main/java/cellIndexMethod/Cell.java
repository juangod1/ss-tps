package cellIndexMethod;

import particle.Particle;

import java.util.List;

public class Cell {
    private int column;
    private int row;
    private List<Particle> particles;

    public Cell(int column, int row, List<Particle> particles){
        this.column = column;
        this.row = row;
        this.particles = particles;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
