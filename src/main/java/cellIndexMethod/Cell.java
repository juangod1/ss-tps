package cellIndexMethod;

import cellIndexMethod.particle.Particle;

import java.util.List;

public class Cell {
    private int row;
    private int column;
    private List<Particle> particles;
    //private List<Particle> overlappingParticles;

    public Cell(int row, int column, List<Particle> particles, List<Particle> overlappingParticles){
        this.row = row;
        this.column = column;
        this.particles = particles;
        //this.overlappingParticles = overlappingParticles;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public int getRow() {
        return row;
    }

    //public void setOverlappingParticles(List<Particle> list){
    //    this.overlappingParticles = list;
    //}

    public int getColumn() {
        return column;
    }

    //public List<Particle> getOverlappingParticles() {
        //return overlappingParticles;
    //}
}
