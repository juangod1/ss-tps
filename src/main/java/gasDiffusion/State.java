package gasDiffusion;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class State {
    private double time;
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
        collisionManager = new CollisionManager(particles);
        generateWalls();
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
        if (!collisions.isEmpty())
            time = collisions.iterator().next().time;
    }

    void updateParticles() {
        double particlesOnLeft = 0;

        for (Particle particle : particles) {
            Point2D.Double position = particle.getPosition();
            double x = position.getX() + particle.getVx() * time;
            double y = position.getY() + particle.getVy() * time;
            position.setLocation(x,y);

            if (x < width/2)
                particlesOnLeft++;
        }

        fpLeft = particlesOnLeft / particles.size();
    }

    void writeFrameToFile(File dynamicFile) throws IOException {
        FileWriter f = new FileWriter(dynamicFile);
        f.append(this.toString());
        f.close();
    }

    @Override
    public String toString() {
        StringBuilder state = new StringBuilder();

        state.append("\n").append(particles.size()).append("\n\n");

        for (Particle particle : particles) {
            state.append(particle.getPosition().getX()).append(" ").append(particle.getPosition().getY()).append(" ");
            state.append(particle.getVx()).append(" ").append(particle.getVy()).append(" ").append(particle.getRadius()).append("\n");
        }

        return state.toString();
    }

    void updateVelocities() {
        // TODO only of particles that collided
    }

    void updateCollisions() {
        Set<Particle> changedParticlesByCollisions = new HashSet<>();
        for (Collision collision : collisions) {
            changedParticlesByCollisions.addAll(collision.particles);
        }

        collisionManager.updateCollisions(changedParticlesByCollisions, particles);
    }
}
