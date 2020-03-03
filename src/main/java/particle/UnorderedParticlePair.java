package particle;

import java.util.HashSet;
import java.util.Set;

public class UnorderedParticlePair {
    private Set<Particle> pair;

    public UnorderedParticlePair(Particle p1, Particle p2){
        pair = new HashSet<>();
        pair.add(p1);
        pair.add(p2);
    }

    public Set<Particle> getPair(){
        return pair;
    }
}
