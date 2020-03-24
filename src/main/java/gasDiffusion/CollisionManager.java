package gasDiffusion;

import java.util.*;

public class CollisionManager {
    private PriorityQueue<Collision> nextCollisions;
    private HashMap<Particle, HashMap<Particle, Collision>> collisionsIndex;

    public CollisionManager(State initialState){
        collisionsIndex = new HashMap<>();
        nextCollisions = createPQ();
        for(Particle particle : initialState.getParticles()){
            updateCollisionForParticle(particle, initialState);
        }
    }

    public Set<Collision> getNextCollisions(){
        Set<Collision> collisions = new HashSet<>();

        collisions.add(nextCollisions.poll());

        while(nextCollisions.peek().time == collisions.iterator().next().time)
            collisions.add(nextCollisions.poll());

        return collisions;
    }

    public void updateCollisions(List<Particle> changedParticles, State state){
        for(Particle particle : changedParticles){
            updateCollisionForParticle(particle, state);
        }
    }

    private PriorityQueue<Collision> createPQ(){
        return new PriorityQueue<>(Comparator.comparingDouble(p -> p.time));
    }

    private void updateCollisionForParticle(Particle particle, State state){
        PriorityQueue<Collision> potentialCollisions = createPQ();

        for(Particle stateParticle : state.getParticles()){
            Double collisionTime = calculateCollisionTime(stateParticle, particle);
            if(collisionTime!=null){
                potentialCollisions.add(new Collision(collisionTime, particle, stateParticle));
            }
        }

        checkIfShouldUpdateCollisionIndex(potentialCollisions.poll());
    }

    private void checkIfShouldUpdateCollisionIndex(Collision firstPotentialCollision){
        Iterator<Particle> firstPotentialCollisionParticles = firstPotentialCollision.particles.iterator();
        Particle p1 = firstPotentialCollisionParticles.next();
        Particle p2 = firstPotentialCollisionParticles.next();

        if(collisionsIndex.containsKey(p1)){
            checkIfShouldUpdateCollisionIndexKnowingItContainsCertainParticle(firstPotentialCollision, p1, p2);
        } else if(collisionsIndex.containsKey(p2)) {
            checkIfShouldUpdateCollisionIndexKnowingItContainsCertainParticle(firstPotentialCollision, p2, p1);
        } else {
            nextCollisions.add(firstPotentialCollision);
            HashMap<Particle, Collision> m1 = new HashMap<>();
            m1.put(p2, firstPotentialCollision);
            collisionsIndex.put(p1, m1);
        }
    }

    private void checkIfShouldUpdateCollisionIndexKnowingItContainsCertainParticle(Collision firstPotentialCollision, Particle p1, Particle p2){
        HashMap<Particle, Collision> currentNextCollisionMap = collisionsIndex.get(p1);
        Collision currentNextCollision = collisionsIndex.get(p1).values().iterator().next();
        Particle currentNextCollisionParticle = collisionsIndex.get(p1).keySet().iterator().next();

        if(currentNextCollision.time>firstPotentialCollision.time){
            nextCollisions.remove(currentNextCollision);
            nextCollisions.add(firstPotentialCollision);
            currentNextCollisionMap.remove(currentNextCollisionParticle);
            currentNextCollisionMap.put(p2, firstPotentialCollision);
        }
    }

    private Double calculateCollisionTime(Particle p1, Particle p2){
        // TODO
        return null;
    }
}
