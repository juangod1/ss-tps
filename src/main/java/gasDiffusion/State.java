package gasDiffusion;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class State {
    double time;
    private double previousTime=0;
    private List<Particle> particles;
    private List<Wall> walls;
    private Set<Collision> collisions;
    private double width;
    private double height;
    private double partitionOpeningSize;
    private double fpLeft = 1;
    private double dp = 0;
    private CollisionManager collisionManager;
    private double totalWallsLength = 0;
    private double clock = 0.1;
    boolean stop = false;

    State(List<Particle> particles, double width, double height, double partitionOpeningSize) {
        this.time = 0;
        this.particles = particles;
        this.walls = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.partitionOpeningSize = partitionOpeningSize;
        generateWalls();
        collisionManager = new CollisionManager(particles, walls, 0);
    }

    boolean isTime() {
        if (time > clock) {
            clock += 0.02;
            return true;
        }
        return false;
    }

    double getFp() { return fpLeft; }

    private void generateWalls() {
        walls.add(new Wall(new Point2D.Double(0,0), new Point2D.Double(0, height),true));
        totalWallsLength += height;
        walls.add(new Wall(new Point2D.Double(0,0), new Point2D.Double(width,0),false));
        totalWallsLength += width;
        walls.add(new Wall(new Point2D.Double(width,0), new Point2D.Double(width, height),true));
        totalWallsLength += height;
        walls.add(new Wall(new Point2D.Double(0, height), new Point2D.Double(width, height),false));
        totalWallsLength += width;
        double partitionOpeningStart = height/2 - partitionOpeningSize /2;
        double partitionOpeningEnd = height/2 + partitionOpeningSize /2;
        walls.add(new Wall(new Point2D.Double(width/2,0), new Point2D.Double(width/2, partitionOpeningStart),true));
        totalWallsLength += partitionOpeningStart;
        walls.add(new Wall(new Point2D.Double(width/2,partitionOpeningEnd), new Point2D.Double(width/2, height),true));
        totalWallsLength += height - partitionOpeningEnd;
    }

    void calculateNextCollision() {
        collisions = collisionManager.getNextCollisions();
        if (!collisions.isEmpty() && collisions.iterator().next()!=null) {
            previousTime = time;
            time = collisions.stream().map(collision -> collision.time).min((d1,d2) -> d1-d2 > 0 ? -1 : 1 ).get();
        }
    }

    private void setPositions(Particle particle, double x, double y){
        double xp, yp;
        if(x<=0){
            xp = 0.001;
            particle.setVx(Math.abs(particle.getVx()));
        } else {
            if(x>=0.239){
                xp = 0.239;
                particle.setVx(Math.abs(particle.getVx())*-1);
            } else {
                xp = x;
            }
        }

        if(y<=0){
            yp = 0.001;
            particle.setVy(Math.abs(particle.getVy()));
        } else {
            if(y>=0.089){
                yp = 0.089;
                particle.setVy(Math.abs(particle.getVy())*-1);
            } else {
                yp = y;
            }
        }
        particle.setPosition(new Point2D.Double(xp, yp));
        collisionManager.updateCollisionForParticle(particle, particles, walls, time);
    }

    void updateParticles() {
        double particlesOnLeft = 0;

        for (Particle particle : particles) {
            Point2D.Double position = particle.getPosition();
            double delta_t = time - previousTime;
            double x = position.getX() + particle.getVx() * delta_t;
            double y = position.getY() + particle.getVy() * delta_t;
            setPositions(particle,x,y);

            if (x < width/2)
                particlesOnLeft++;
        }

        fpLeft = particlesOnLeft / particles.size();
    }

    void writeFrameToFile(File outputFile, File tableFile) throws IOException {
//        FileWriter f = new FileWriter(outputFile, true);
        FileWriter t = new FileWriter(tableFile, true);
//        f.append(this.toString());
//        f.close();

        List<Double> DCM = new ArrayList<>();

        for (Particle particle : particles) {
            if (particle.isTestigo) {
                DCM.add(Math.pow(particle.getPosition().getX() - particle.initialPosition.getX(), 2) + Math.pow(particle.getPosition().getY() - particle.initialPosition.getY(), 2));
            }
        }

        double avg = 0;

        for (Double d : DCM) {
            avg += d;
        }
        avg /= DCM.size();

        double sd = 0;

        for (Double d : DCM) {
            sd += Math.pow(d - avg, 2);
        }
        sd = Math.sqrt(sd/DCM.size());
        ;
//        double pressure = dp / (time * totalWallsLength);
//        if (time == 0)
//            pressure = 0;
//        double temp = calculateTemp();
        t.append(String.valueOf(time)).append(", ").append(String.valueOf(avg)).append(", ").append(String.valueOf(sd)).append("\n");
//        t.append(", ").append(String.valueOf(pressure)).append(", ").append(String.valueOf(temp)).append("\n");
        t.close();
    }

    private double calculateTemp() {
        double avgKineticE = 0;
        for (Particle particle : particles) {
            avgKineticE += particle.getMass() * (Math.pow(particle.getVx(), 2) + Math.pow(particle.getVy(), 2)) / 2;
        }
        return avgKineticE * 2 / (3 * 1.380648e-23);
    }

    @Override
    public String toString() {
        StringBuilder state = new StringBuilder();
        double wallParticleRadius = particles.get(0).getRadius()/3;
        state.append(Math.round(particles.size()+
                (width/wallParticleRadius) +
                (height/wallParticleRadius) +
                (walls.get(4).end.getY()-walls.get(4).start.getY())/(2*wallParticleRadius) +
                (walls.get(5).end.getY()-walls.get(5).start.getY())/(2*wallParticleRadius)
                )).append("\n\n");

        Wall w = walls.get(4);
        for(double i=w.start.getY();i<w.end.getY();i+=wallParticleRadius*2){
            state.append(width/2).append(" ").append(i).append(" ").append(0).append(" ").append(0).append(" ").append(wallParticleRadius);
            state.append(" ").append(1).append(" ").append(0).append(" ").append(0).append("\n");
        }
        w = walls.get(5);
        for(double i=w.start.getY();i<w.end.getY();i+=wallParticleRadius*2){
            state.append(width/2).append(" ").append(i).append(" ").append(0).append(" ").append(0).append(" ").append(wallParticleRadius);
            state.append(" ").append(1).append(" ").append(0).append(" ").append(0).append("\n");
        }

        for(double i=0;i<width;i+=wallParticleRadius*2){
            state.append(i).append(" ").append(0).append(" ").append(0).append(" ").append(0).append(" ").append(wallParticleRadius);
            state.append(" ").append(1).append(" ").append(0).append(" ").append(0).append("\n");
            state.append(i).append(" ").append(height).append(" ").append(0).append(" ").append(0).append(" ").append(wallParticleRadius);
            state.append(" ").append(1).append(" ").append(0).append(" ").append(0).append("\n");
        }

        for(double j=0;j<height;j+=wallParticleRadius*2){
            state.append(0).append(" ").append(j).append(" ").append(0).append(" ").append(0).append(" ").append(wallParticleRadius);
            state.append(" ").append(1).append(" ").append(0).append(" ").append(0).append("\n");
            state.append(width).append(" ").append(j).append(" ").append(0).append(" ").append(0).append(" ").append(wallParticleRadius);
            state.append(" ").append(1).append(" ").append(0).append(" ").append(0).append("\n");
        }

        double max = 0, min = 110;
        for (Particle particle : particles) {
            double velocity = Math.sqrt(Math.pow(particle.getVx(), 2) + Math.pow(particle.getVy(), 2));
            if (velocity > max) max = velocity;
            if (velocity < min) min = velocity;
        }

        for (Particle particle : particles) {
            double velocity = Math.sqrt(Math.pow(particle.getVx(), 2) + Math.pow(particle.getVy(), 2));
            state.append(particle.getPosition().getX()).append(" ").append(particle.getPosition().getY()).append(" ");
            state.append(particle.getVx()).append(" ").append(particle.getVy()).append(" ").append(particle.getRadius());
            state.append(" ").append(0).append(" ").append(0).append(" ").append(String.valueOf(1-(velocity-min)/((max-min) == 0? 1:(max-min)))).append("\n");
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

                    p2.setVx(p2.getVx() - jx / p2.getMass());
                    p2.setVy(p2.getVy() - jy / p2.getMass());
                } else {
                    it = collision.particles.iterator();
                    p1 = it.next();
                    Wall curr = collision.wall;
                    double vx_after, vy_after;
                    if (curr.isVertical) {
                        vx_after = -1 * p1.getVx();
                        dp += p1.getMass() * Math.abs(p1.getVx() - vx_after);
                        p1.setVx(vx_after);
                    } else {
                        vy_after = -1 * p1.getVy();
                        dp += p1.getMass() * Math.abs(p1.getVy() - vy_after);
                        p1.setVy(vy_after);
                    }
                    if (p1.isTestigo) stop = true;
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
