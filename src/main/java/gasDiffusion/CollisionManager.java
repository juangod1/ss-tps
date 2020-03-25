package gasDiffusion;

import java.util.*;

public class CollisionManager {
    private PriorityQueue<Collision> nextCollisions;
    private HashMap<Particle, HashMap<Particle, Collision>> collisionsIndex;

    CollisionManager(List<Particle> particles){
        collisionsIndex = new HashMap<>();
        nextCollisions = createPQ();
        for(Particle particle : particles){
            updateCollisionForParticle(particle, particles);
        }
    }

    Set<Collision> getNextCollisions(){
        Set<Collision> collisions = new HashSet<>();

        collisions.add(nextCollisions.poll());

        while(nextCollisions.peek().time == collisions.iterator().next().time)
            collisions.add(nextCollisions.poll());

        return collisions;
    }

    void updateCollisions(Set<Particle> changedParticlesByCollisions, List<Particle> particles){
        for(Particle particle : changedParticlesByCollisions){
            updateCollisionForParticle(particle, particles);
        }
    }

    private PriorityQueue<Collision> createPQ(){
        return new PriorityQueue<>(Comparator.comparingDouble(p -> p.time));
    }

    private void updateCollisionForParticle(Particle particle, List<Particle> particles){
        PriorityQueue<Collision> potentialCollisions = createPQ();

        for(Particle stateParticle : particles){
            double collisionTime = calculateCollisionTime(stateParticle, particle);
            if (collisionTime != -1){
                potentialCollisions.add(new Collision(collisionTime, particle, stateParticle));
            }
        }

        Collision firstPotentialCollision = potentialCollisions.poll();

        if(firstPotentialCollision!=null)
            checkIfShouldUpdateCollisionIndex(firstPotentialCollision);
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

    private double calculateCollisionTime(Particle p1, Particle p2){
        double deltaX = p2.getPosition().getX() - p1.getPosition().getX();
        double deltaY = p2.getPosition().getY() - p1.getPosition().getY();
        double deltaVx = p2.getVx() - p1.getVx();
        double deltaVy = p2.getVy() - p1.getVy();
        double deltaV_deltaR = deltaVx * deltaX + deltaVy * deltaY;
        if (deltaV_deltaR >= 0)
            return -1;

        double tita = p2.getRadius() + p1.getRadius();
        double deltaR_deltaR = Math.pow(deltaX, 2) + Math.pow(deltaY, 2);
        double deltaV_deltaV = Math.pow(deltaVx, 2) + Math.pow(deltaVy, 2);
        double d = Math.pow(deltaV_deltaR, 2) - deltaV_deltaV * (deltaR_deltaR - Math.pow(tita, 2));
        if (d < 0)
            return -1;

        return -1 * (deltaV_deltaR + Math.sqrt(d)) / (deltaV_deltaV);
    }
}
