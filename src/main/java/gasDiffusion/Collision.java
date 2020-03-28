package gasDiffusion;

import java.util.HashSet;
import java.util.Set;

public class Collision {
    double time;
    Set<Particle> particles;
    Wall wall;

    public Collision(Double time, Particle particle1, Particle particle2) {
        this.time = time;
        particles = new HashSet<>();
        particles.add(particle1);
        particles.add(particle2);
        wall=null;
    }

    public Collision(Double time, Particle particle1, Wall wall) {
        this.time = time;
        particles = new HashSet<>();
        particles.add(particle1);
        this.wall=wall;
    }

    @Override
    public int hashCode() {
        return particles.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collision collision = (Collision) o;
        return Double.compare(collision.time, time) == 0 &&
                particles.equals(collision.particles) && (wall==null ? collision.wall==null :
                wall.equals(collision.wall));

    }
}
