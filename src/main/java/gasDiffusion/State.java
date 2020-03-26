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
        collisionManager = new CollisionManager(particles, walls, 0);
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
            time = collisions.iterator().next().time;
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

        state.append(Math.round(1+particles.size()+
                (width/particles.get(0).getRadius()) +
                (height/particles.get(0).getRadius()) +
                (walls.get(4).end.getY()-walls.get(4).start.getY())/(2*particles.get(0).getRadius()) +
                (walls.get(5).end.getY()-walls.get(5).start.getY())/(2*particles.get(0).getRadius())
                )).append("\n\n");

        Wall w = walls.get(4);
        for(double i=w.start.getY();i<w.end.getY();i+=particles.get(0).getRadius()*2){
            state.append(width/2).append(" ").append(i).append(" ").append(0).append(" ").append(0).append(" ").append(particles.get(0).getRadius());
            state.append(" ").append(1).append(" ").append(0).append(" ").append(0).append("\n");
        }
        w = walls.get(5);
        for(double i=w.start.getY();i<w.end.getY();i+=particles.get(0).getRadius()*2){
            state.append(width/2).append(" ").append(i).append(" ").append(0).append(" ").append(0).append(" ").append(particles.get(0).getRadius());
            state.append(" ").append(1).append(" ").append(0).append(" ").append(0).append("\n");
        }

        for(double i=0;i<width;i+=particles.get(0).getRadius()*2){
            state.append(i).append(" ").append(0).append(" ").append(0).append(" ").append(0).append(" ").append(particles.get(0).getRadius());
            state.append(" ").append(1).append(" ").append(0).append(" ").append(0).append("\n");
            state.append(i).append(" ").append(height).append(" ").append(0).append(" ").append(0).append(" ").append(particles.get(0).getRadius());
            state.append(" ").append(1).append(" ").append(0).append(" ").append(0).append("\n");
        }

        for(double j=0;j<height;j+=particles.get(0).getRadius()*2){
            state.append(0).append(" ").append(j).append(" ").append(0).append(" ").append(0).append(" ").append(particles.get(0).getRadius());
            state.append(" ").append(1).append(" ").append(0).append(" ").append(0).append("\n");
            state.append(width).append(" ").append(j).append(" ").append(0).append(" ").append(0).append(" ").append(particles.get(0).getRadius());
            state.append(" ").append(1).append(" ").append(0).append(" ").append(0).append("\n");
        }

        for (Particle particle : particles) {
            state.append(particle.getPosition().getX()).append(" ").append(particle.getPosition().getY()).append(" ");
            state.append(particle.getVx()).append(" ").append(particle.getVy()).append(" ").append(particle.getRadius());
            state.append(" ").append(1).append(" ").append(1).append(" ").append(1).append("\n");
        }

        return state.toString();
    }

    void updateVelocities() {
        Iterator<Particle> it;
        Particle p1, p2;
        double deltaV_deltaR, tita, j, deltaX, deltaY, deltaVx, deltaVy, jx, jy;

        if(collisions.iterator().next()!=null) {
            for (Collision collision : collisions) {
                if (collision.wall == null) {
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
                } else {
                    it = collision.particles.iterator();
                    p1 = it.next();

                    if (collision.wall.isVertical) {
                        p1.setVx(-1 * p1.getVx());
                    } else {
                        p1.setVy(-1 * p1.getVy());
                    }
                }
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

        collisionManager.updateCollisions(changedParticlesByCollisions, particles, walls, time);
    }
}
