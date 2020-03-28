package gasDiffusion;

import java.awt.geom.Point2D;
import java.util.Objects;

class Wall {
    Point2D start;
    Point2D end;
    boolean isVertical;

    Wall(Point2D start, Point2D end, boolean isVertical) {
        this.start = start;
        this.end = end;
        this.isVertical = isVertical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wall wall = (Wall) o;
        return isVertical == wall.isVertical &&
                start.equals(wall.start) &&
                end.equals(wall.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, isVertical);
    }
}
