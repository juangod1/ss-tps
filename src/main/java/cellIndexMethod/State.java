package cellIndexMethod;

import particle.Particle;

import java.util.List;

public class State {
    private List<Particle> particles;
    private double areaLength;
    private int numCells;
    private double interactionRadius;

    public State(List<Particle> particles, double areaLength, int numCells, double interactionRadius) {
        this.particles = particles;
        this.areaLength = areaLength;
        this.numCells = numCells;
        this.interactionRadius = interactionRadius;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public double getAreaLength() {
        return areaLength;
    }

    public int getNumCells() {
        return numCells;
    }

    public double getInteractionRadius() {
        return interactionRadius;
    }
}
