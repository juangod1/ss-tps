package particle;

import java.awt.*;

public class ParticleImpl implements Particle {
    private double radius;
    private Point position;
    private int id;

    public ParticleImpl(double radius, Point position, int id){
        this.position = position;
        this.radius = radius;
        this.id = id;
    }

    public ParticleImpl(double radius, int id) {
        this.radius = radius;
        this.id = id;
    }

    public double getRadius() {
        return radius;
    }

    public int getId(){
        return id;
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

    public double getDistanceModule(Particle p2){
        return Math.sqrt(Math.pow(this.position.x, 2) + Math.pow(this.position.y, 2));
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
