package particle;

import java.util.HashSet;
import java.util.Set;

public class UnorderedParticlePair {
    private Set<Particle> pair;
    private Particle p1;
    private Particle p2;

    public UnorderedParticlePair(Particle p1, Particle p2){
        pair = new HashSet<>();
        pair.add(p1);
        pair.add(p2);
        this.p1 = p1;
        this.p2 = p2;
    }

    public Particle getOtherParticle(Particle p){
        return p == p1 ? p2 : p1;
    }

    public Set<Particle> getPair(){
        return pair;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UnorderedParticlePair){
            return this.getPair().equals(((UnorderedParticlePair)obj).getPair());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getPair().hashCode();
    }
}
