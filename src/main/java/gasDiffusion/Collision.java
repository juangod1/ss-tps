package gasDiffusion;

import java.util.HashSet;
import java.util.Set;

public class Collision {
    public Collision(Double time, Particle particle1, Particle particle2){
        this.time = time;
        particles = new HashSet<>();
        particles.add(particle1);
        particles.add(particle2);
    }
    double time;
    Set<Particle> particles;

    @Override
    public int hashCode() {
        return particles.hashCode();
    }
}
