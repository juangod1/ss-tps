package gasDiffusion.particle;

import java.awt.geom.Point2D;

public class Particle {
    private double radius;
    private Point2D.Double position;
    private int id;
    private double vx;
    private double vy;
    private double mass;

    public Particle(double radius, double vx, double vy, Point2D.Double position, int id){
        this.position = position;
        this.radius = radius;
        this.id = id;
        this.vx = vx;
        this.vy = vy;
    }

    public Particle(double radius, int id, double mass) {
        this.radius = radius;
        this.id = id;
        this.mass = mass;
    }

    public double getRadius() {
        return radius;
    }

    public int getId(){
        return id;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Point2D.Double getPosition() {
        return position;
    }

    public double getMass() {
        return mass;
    }

    public void setPosition(Point2D.Double position) {
        this.position = position;
    }

    public void setId(int id) { this.id = id; }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

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
        if (!(o instanceof Particle))
            return false;
        Particle p = (Particle)o;

        return p.getId() == this.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
