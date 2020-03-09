package cellIndexMethod.particle;

import java.awt.*;

public interface Particle {
    double getRadius();
    void setRadius(double radius);

    Point getPosition();
    void setPosition(Point position);

    int getId();

    public double getDistanceModule(Particle p2);
}
