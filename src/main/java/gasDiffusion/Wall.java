package gasDiffusion;

import java.awt.geom.Point2D;

public class Wall {
    Point2D start;
    Point2D end;
    boolean isVertical;

    public Wall(Point2D start, Point2D end, boolean isVertical) {
        this.start = start;
        this.end = end;
        this.isVertical = isVertical;
    }
}
