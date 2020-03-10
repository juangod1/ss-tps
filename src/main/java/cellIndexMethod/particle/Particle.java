package cellIndexMethod.particle;

import java.awt.*;
import java.awt.geom.Point2D;

public interface Particle {
    double getRadius();
    void setRadius(double radius);

    Point2D.Double getPosition();
    void setPosition(Point2D.Double position);

    int getId();

    public double getDistanceModule(Particle p2);
}
