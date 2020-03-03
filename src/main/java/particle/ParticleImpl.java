package particle;

import java.awt.*;

public class ParticleImpl implements Particle {
    private double radius;
    private Point position;

    public ParticleImpl(double radius, Point position){
        setPosition(position);
        setRadius(radius);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
