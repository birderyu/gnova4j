package gnova.drawing.d2;

import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;

import java.util.Objects;

/**
 *
 */
@Immutable
public class Point implements Comparable<Point> {

    /**
     *
     */
    public static final Point NONE = new Point(Float.NaN, Float.NaN);

    private final float x;
    private final float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Float.compare(point.x, x) == 0 &&
                Float.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(@NotNull Point p) {
        if (this == p) {
            return 0;
        }
        int px = Float.compare(x, p.x);
        if (px != 0) {
            return px;
        }
        int py = Float.compare(y, p.y);
        if (py != 0) {
            return py;
        }
        return 0;
    }
}
