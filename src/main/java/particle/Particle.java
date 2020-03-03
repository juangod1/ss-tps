package particle;

import java.awt.*;

public interface Particle {
    double getRadius();
    void setRadius(double radius);

    Point getPosition();
    void setPosition(Point position);

    public double getDistanceModule(Particle p2);
}
