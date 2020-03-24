package gasDiffusion;

import java.awt.geom.Point2D;
import java.util.List;

public class State {
    private double time;
    private List<Particle> particles;
    private List<Wall> walls;
    private double width;
    private double height;
    private double partitionOpeningSize;

    State(List<Particle> particles, double width, double height, double partitionOpeningSize) {
        this.time = 0;
        this.particles = particles;
        this.width = width;
        this.height = height;
        this.partitionOpeningSize = partitionOpeningSize;
        generateWalls();
    }

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

    public List<Particle> getParticles() {
        return particles;
    }

}
