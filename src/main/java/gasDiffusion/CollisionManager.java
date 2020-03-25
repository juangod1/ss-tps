package gasDiffusion;

import java.util.*;

public class CollisionManager {
    private PriorityQueue<Collision> nextCollisions;
    private HashMap<Particle, HashMap<Particle, Collision>> collisionsIndex;

    CollisionManager(List<Particle> particles, List<Wall> wall, double currentTime){
        collisionsIndex = new HashMap<>();
        nextCollisions = createPQ();
        for(Particle particle : particles){
            updateCollisionForParticle(particle, particles, wall, currentTime);
        }
    }

    Set<Collision> getNextCollisions(){
        Set<Collision> collisions = new HashSet<>();

        collisions.add(nextCollisions.poll());

        while(nextCollisions.peek()!=null && nextCollisions.peek().time == collisions.iterator().next().time)
            collisions.add(nextCollisions.poll());

        return collisions;
    }

    void updateCollisions(Set<Particle> changedParticlesByCollisions, List<Particle> particles, List<Wall> walls, double currentTime){
        for(Particle particle : changedParticlesByCollisions){
            updateCollisionForParticle(particle, particles, walls, currentTime);
        }
    }

    private PriorityQueue<Collision> createPQ(){
        return new PriorityQueue<>(Comparator.comparingDouble(p -> p.time));
    }

    private void updateCollisionForParticle(Particle particle, List<Particle> particles, List<Wall> walls, double currentTime){
        PriorityQueue<Collision> potentialCollisions = createPQ();

        for(Particle stateParticle : particles){
            double collisionTime = calculateCollisionTime(stateParticle, particle);
            if (collisionTime != -1){
                potentialCollisions.add(new Collision(collisionTime, particle, stateParticle));
            }
        }

        for(Wall wall : walls){
            double collisionTime = calculateCollisionTimeWall(particle, wall);
            if (collisionTime != -1){
                potentialCollisions.add(new Collision(collisionTime + currentTime, particle, wall));
            }
        }

        Collision firstPotentialCollision = potentialCollisions.poll();

        if(firstPotentialCollision!=null)
            checkIfShouldUpdateCollisionIndex(firstPotentialCollision);
    }

    private void checkIfShouldUpdateCollisionIndex(Collision firstPotentialCollision){
        Iterator<Particle> firstPotentialCollisionParticles = firstPotentialCollision.particles.iterator();
        Particle p1 = firstPotentialCollisionParticles.next();
        Particle p2 = firstPotentialCollisionParticles.hasNext() ? firstPotentialCollisionParticles.next() : null;

        if(collisionsIndex.containsKey(p1)){
            checkIfShouldUpdateCollisionIndexKnowingItContainsCertainParticle(firstPotentialCollision, p1, p2);
        } else if(collisionsIndex.containsKey(p2)) {
            checkIfShouldUpdateCollisionIndexKnowingItContainsCertainParticle(firstPotentialCollision, p2, p1);
        } else {
            nextCollisions.add(firstPotentialCollision);
            HashMap<Particle, Collision> m1 = new HashMap<>();

            if(firstPotentialCollision.wall == null) {
                m1.put(p2, firstPotentialCollision);
            } else {
                m1.put(null, firstPotentialCollision);
            }
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

    private double calculateCollisionTimeWall(Particle p1, Wall wall){
        if (wall.isVertical) {
            double x = wall.start.getX();
            double lowerLimit = wall.start.getY();
            double higherLimit = wall.end.getY();

            double tc = (x - p1.getPosition().getX()) / p1.getVx();

            if (tc < 0) return -1;

            double y = p1.getPosition().getY() + p1.getVy() * tc;

            if (y < lowerLimit || y > higherLimit) return -1;
            return tc;
        } else {
            double y = wall.start.getY();
            double lowerLimit = wall.start.getX();
            double higherLimit = wall.end.getX();

            double tc = (y - p1.getPosition().getY()) / p1.getVy();

            if (tc < 0) return -1;

            double x = p1.getPosition().getX() + p1.getVx() * tc;

            if (x < lowerLimit || x > higherLimit) return -1;
            return tc;
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

        double ans = -1 * (deltaV_deltaR + Math.sqrt(d)) / (deltaV_deltaV);
        return ans >= 0 ? ans : -1;
    }
}
