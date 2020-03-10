package cellIndexMethod.particle;

import java.awt.*;
import java.awt.geom.Point2D;

public class ParticleImpl implements Particle {
    private double radius;
    private Point2D.Double position;
    private int id;

    public ParticleImpl(double radius, Point2D.Double position, int id){
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

    public Point2D.Double getPosition() {
        return position;
    }

    public void setPosition(Point2D.Double position) {
        this.position = position;
    }

    public void setId(int id) { this.id = id; }

    public double getDistanceModule(Particle p2){
        return Math.sqrt(Math.pow(
                this.position.x - p2.getPosition().x, 2) + Math.pow(this.position.y - p2.getPosition().y, 2)
        );
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ParticleImpl))
            return false;
        ParticleImpl p = (ParticleImpl)o;

        return p.getId() == this.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
