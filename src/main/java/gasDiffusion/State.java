package gasDiffusion;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class State {
    private double time;
    private double previousTime=0;
    private List<Particle> particles;
    private List<Wall> walls;
    private Set<Collision> collisions;
    private double width;
    private double height;
    private double partitionOpeningSize;
    private double fpLeft;
    private CollisionManager collisionManager;

    State(List<Particle> particles, double width, double height, double partitionOpeningSize) {
        this.time = 0;
        this.particles = particles;
        this.walls = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.partitionOpeningSize = partitionOpeningSize;
        this.fpLeft = 1;
        generateWalls();
        collisionManager = new CollisionManager(particles, walls);
    }

    double getFp() { return fpLeft; }

    private void generateWalls() {
        walls.add(new Wall(new Point2D.Double(0,0), new Point2D.Double(0, height),true));
        walls.add(new Wall(new Point2D.Double(0,0), new Point2D.Double(width,0),false));
        walls.add(new Wall(new Point2D.Double(width,0), new Point2D.Double(width, height),true));
        walls.add(new Wall(new Point2D.Double(0, height), new Point2D.Double(width, height),false));
        double partitionOpeningStart = height/2 - partitionOpeningSize /2;
        double partitionOpeningEnd = height/2 + partitionOpeningSize /2;
        walls.add(new Wall(new Point2D.Double(width/2,0), new Point2D.Double(width/2, partitionOpeningStart),true));
        walls.add(new Wall(new Point2D.Double(width/2,partitionOpeningEnd), new Point2D.Double(width/2, height),true));
    }

    void calculateNextCollision() {
        collisions = collisionManager.getNextCollisions();
        if (!collisions.isEmpty() && collisions.iterator().next()!=null) {
            previousTime = time;
            time = collisions.iterator().next().time + previousTime;
        }
    }

    void updateParticles() {
        double particlesOnLeft = 0;

        for (Particle particle : particles) {
            Point2D.Double position = particle.getPosition();
            double delta_t = time - previousTime;
            double x = position.getX() + particle.getVx() * delta_t;
            double y = position.getY() + particle.getVy() * delta_t;
            position.setLocation(x,y);

            if (x < width/2)
                particlesOnLeft++;
        }

        fpLeft = particlesOnLeft / particles.size();
    }

    void writeFrameToFile(File outputFile) throws IOException {
        FileWriter f = new FileWriter(outputFile, true);
        f.append(this.toString());
        f.close();
    }

    @Override
    public String toString() {
        StringBuilder state = new StringBuilder();

        state.append(particles.size()).append("\n\n");

        for (Particle particle : particles) {
            state.append(particle.getPosition().getX()).append(" ").append(particle.getPosition().getY()).append(" ");
            state.append(particle.getVx()).append(" ").append(particle.getVy()).append(" ").append(particle.getRadius()).append("\n");
        }

        return state.toString();
    }

    void updateVelocities() {
        Iterator<Particle> it;
        Particle p1, p2;
        double deltaV_deltaR, tita, j, deltaX, deltaY, deltaVx, deltaVy, jx, jy;

        if(collisions.iterator().next()!=null) {
            for (Collision collision : collisions) {
                it = collision.particles.iterator();
                p1 = it.next();
                p2 = it.next();

                deltaX = p2.getPosition().getX() - p1.getPosition().getX();
                deltaY = p2.getPosition().getY() - p1.getPosition().getY();
                deltaVx = p2.getVx() - p1.getVx();
                deltaVy = p2.getVy() - p1.getVy();
                deltaV_deltaR = deltaVx * deltaX + deltaVy * deltaY;
                tita = p2.getRadius() + p1.getRadius();
                j = (2 * p2.getMass() * p1.getMass() * deltaV_deltaR) / (tita * (p2.getMass() + p1.getMass()));
                jx = (j * deltaX) / tita;
                jy = (j * deltaY) / tita;

                p1.setVx(p1.getVx() + jx / p1.getMass());
                p1.setVy(p1.getVy() + jy / p1.getMass());

                p2.setVx(p2.getVx() + jx / p2.getMass());
                p2.setVy(p2.getVy() + jy / p2.getMass());
            }
        }
    }

    void updateCollisions() {
        Set<Particle> changedParticlesByCollisions = new HashSet<>();

        if(collisions.iterator().next()!=null) {
            for (Collision collision : collisions) {
                changedParticlesByCollisions.addAll(collision.particles);
            }
        }

        collisionManager.updateCollisions(changedParticlesByCollisions, particles, walls);
    }
}
