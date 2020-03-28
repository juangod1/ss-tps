package gasDiffusion;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;

public class CollisionManager {
    private PriorityQueue<Collision> nextCollisions;

    CollisionManager(List<Particle> particles, List<Wall> wall, double currentTime){
        nextCollisions = createPQ();
        for(Particle particle : particles){
            updateCollisionForParticle(particle, particles, wall, currentTime);
        }
    }

    Set<Collision> getNextCollisions(){
        Set<Collision> collisions = new HashSet<>();

        collisions.add(nextCollisions.poll());

        while(nextCollisions.peek()!=null && (Math.abs(nextCollisions.peek().time - collisions.iterator().next().time) < DELTA))
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

    static final double DELTA = 0.000000000001d;

    private void updateCollisionForParticle(Particle particle, List<Particle> particles, List<Wall> walls, double currentTime){
        PriorityQueue<Collision> potentialCollisions = createPQ();

        for(Particle stateParticle : particles){
            double collisionTime = calculateCollisionTime(stateParticle, particle, walls);
            if (collisionTime >= DELTA){
                potentialCollisions.add(new Collision(collisionTime + currentTime, particle, stateParticle));
            }
        }

        for(Wall wall : walls){
            double collisionTime = calculateCollisionTimeWall(particle, wall);
            if (collisionTime >= DELTA){
                potentialCollisions.add(new Collision(collisionTime + currentTime, particle, wall));
            }
        }

        Collision firstPotentialCollision = potentialCollisions.poll();

        if(firstPotentialCollision!=null)
            addCollision(firstPotentialCollision, particle, particles, walls, currentTime);
    }

    private void addCollision(Collision firstPotentialCollision, Particle particle, List<Particle> particles, List<Wall> walls, double currentTime){
        List<Collision> affectedParticleCollision = nextCollisions.parallelStream().filter(collision ->
            collision.particles.contains(particle)
        ).collect(Collectors.toList());

        if(affectedParticleCollision.size()==1) {
            List<Particle> orphanParticle = affectedParticleCollision.get(0).particles.stream().filter(p -> !p.equals(particle)).collect(Collectors.toList());

            nextCollisions.remove(affectedParticleCollision.get(0));

            if(orphanParticle.size()==1)
                updateCollisionForParticle(orphanParticle.get(0), particles, walls, currentTime);
        }

        if(!nextCollisions.contains(firstPotentialCollision)) nextCollisions.add(firstPotentialCollision);
    }

    private double calculateCollisionTimeWall(Particle p1, Wall wall){
        if (wall.isVertical) {
            double x = wall.start.getX();
            double lowerLimit = wall.start.getY();
            double higherLimit = wall.end.getY();

            double tc = (x + (p1.getVx() > 0 ? p1.getRadius()*-1 : p1.getRadius()) - p1.getPosition().getX()) / p1.getVx();

            if (tc < 0) return -1;

            double y = p1.getPosition().getY() + p1.getVy() * tc;

            if (y < lowerLimit || y > higherLimit) return -1;
            return tc;
        } else {
            double y = wall.start.getY();
            double lowerLimit = wall.start.getX();
            double higherLimit = wall.end.getX();

            double tc = (y + (p1.getVy() > 0 ? p1.getRadius()*-1 : p1.getRadius()) - p1.getPosition().getY()) / p1.getVy();

            if (tc < 0) return -1;

            double x = p1.getPosition().getX() + p1.getVx() * tc;

            if (x < lowerLimit || x > higherLimit) return -1;
            return tc;
        }
    }

    private double calculateCollisionTime(Particle p1, Particle p2, List<Wall> walls){
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

        if(ans < 0 || checkIfCollisionWillBeStoppedByWall(p1, p2, ans, walls)){
            return -1;
        }

        return ans;
    }

    private boolean checkIfCollisionWillBeStoppedByWall(Particle p1, Particle p2, double time, List<Wall> walls){
        return checkIfParticleTrajectoryWillHitOpeningWalls(p1, time, walls) || checkIfParticleTrajectoryWillHitOpeningWalls(p2, time, walls);
    }

    private boolean checkIfParticleTrajectoryWillHitOpeningWalls(Particle p, double time, List<Wall> walls){
        List<Wall> openingWalls = walls.stream().filter(
                wall -> wall.isVertical && wall.start.getX() == 0.12
                ).collect(Collectors.toList());

        Point2D.Double collisionPointDouble = new Point2D.Double(
                p.getPosition().getX() + time*p.getVx(),
                p.getPosition().getY() + time*p.getVy());

        if(
                (collisionPointDouble.getX() < openingWalls.get(0).start.getX()
                                            && openingWalls.get(0).start.getX() < p.getPosition().getX())
                ||
                (collisionPointDouble.getX() > openingWalls.get(0).start.getX()
                                            && openingWalls.get(0).start.getX() > p.getPosition().getX())
        ){
            double collTime = (0.12-p.getPosition().getX())/p.getVx();
            double yCollision = collTime*p.getVy()+p.getPosition().getY();
            return
                    (openingWalls.get(0).start.getY() < yCollision
                                                 && yCollision < openingWalls.get(0).end.getY())
                    ||
                    (openingWalls.get(1).start.getY() < yCollision &&
                                                     yCollision < openingWalls.get(1).end.getY())
                    ;
        }
        return false;
    }
}
