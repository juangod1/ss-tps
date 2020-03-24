package gasDiffusion;

import gasDiffusion.particle.Particle;

import java.util.List;

public class State {
    private List<Particle> particles;
    private double width;
    private double height;

    public State(List<Particle> particles, double width, double height) {
        this.particles = particles;
        this.width = width;
        this.height = height;
    }

    public List<Particle> getParticles() {
        return particles;
    }

}
