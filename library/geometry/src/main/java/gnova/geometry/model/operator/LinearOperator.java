package gnova.geometry.model.operator;

import gnova.geometry.model.Coordinate;
import gnova.geometry.model.Geometry;
import gnova.geometry.model.Point;
import gnova.geometry.model.pattern.Lineal;

/**
 * 线状几何类型操作
 */
public interface LinearOperator<L extends Lineal> {

    default double getStartLength() {
        return 0;
    }

    double getEndLength();

    default boolean isValidLength(double length) {
        return length >= getStartLength() && length <= getEndLength();
    }

    /**
     *
     * @param startLength
     * @param endLength
     * @return
     */
    L extract(double startLength, double endLength);

    Coordinate extractPoint(double length);

    Coordinate extractPoint(double length, double offset);

    double lengthOf(double x, double y);

    default double lengthOf(Coordinate coordinate) {
        return lengthOf(coordinate.getX(), coordinate.getY());
    }

    default double lengthOf(Point point) {
        return lengthOf(point.getX(), point.getY());
    }

    double lengthOfAfter(double x, double y, double minLength);

    default double lengthOfAfter(Coordinate coordinate, double minLength) {
        return lengthOfAfter(coordinate.getX(), coordinate.getY(), minLength);
    }

    default double lengthOfAfter(Point point, double minLength) {
        return lengthOfAfter(point.getX(), point.getY(), minLength);
    }

    double[] lengthsOf(L subLine);

    Geometry selfIntersections();
}
