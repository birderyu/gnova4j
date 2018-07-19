package gnova.geometry.model.extra;

import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;
import gnova.geometry.model.GeometryFactory;
import gnova.geometry.model.BoundingBox;
import gnova.geometry.model.Coordinate;
import gnova.geometry.model.LinearRing;
import gnova.geometry.model.Polygon;

import java.util.Objects;

/**
 * 圆
 */
@Immutable
public class Circle2D {

    /**
     * 圆上面的点个数
     */
    public static final int DEFALUT_SIDES = 32;

    /**
     * 圆心的X坐标
     */
    private final double x;

    /**
     * 圆心的Y坐标
     */
    private final double y;

    /**
     * 半径
     */
    private final double r;

    private final GeometryFactory gf;

    public Circle2D(double x, double y, double r, GeometryFactory gf) {
        this.x = x;
        this.y = y;
        this.r = Math.abs(r);
        this.gf = gf;
    }

    @NotNull
    public Coordinate getCentre() {
        return new Coordinate(x, y);
    }

    public double getRadius() {
        return r;
    }

    @NotNull
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x - r, x + r, y - r, y + r);
    }

    @NotNull
    public Polygon polygonize() {
        return polygonize(DEFALUT_SIDES);
    }

    @NotNull
    public Polygon polygonize(int side) {
        Coordinate coords[] = new Coordinate[side + 1];
        for( int i = 0; i < side; i++){
            double angle = ((double) i / (double) side) * Math.PI * 2.0;
            double dx = Math.cos(angle) * r;
            double dy = Math.sin(angle) * r;
            coords[i] = new Coordinate(x + dx, y + dy);
        }
        coords[side] = coords[0];
        LinearRing ring = gf.createLinearRing(coords);
        Polygon polygon = gf.createPolygon(ring, null);
        return polygon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle2D circle = (Circle2D) o;
        return Double.compare(circle.x, x) == 0 &&
                Double.compare(circle.y, y) == 0 &&
                Double.compare(circle.r, r) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r);
    }

    @Override
    @NotNull
    public String toString() {
        return "{\"type\": \"Circle2D\", \"centre\":["
                + x + ", " + y + "], \"radius\": + " + r + "}";
    }
}
