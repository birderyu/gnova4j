package gnova.geometry.model;

import gnova.core.annotation.Immutable;
import gnova.geometry.model.pattern.Lineal;
import gnova.core.ReadOnlyCollection;
import gnova.core.ReadOnlyIterator;

/**
 * 线串
 *
 * <p>线串用于表示一串{@link Coordinate 坐标}组成的一条折线，是多个{@link Point 点}组成的{@link ReadOnlyCollection 只读集合}，
 * 它是一个1维的几何对象。
 *
 * @see Geometry
 * @see Point
 * @see Lineal
 * @see ReadOnlyCollection
 * @author birderyu
 * @date 2017/6/21
 * @version 1.0.0
 */
@Immutable
public interface LineString
        extends Geometry, Lineal, ReadOnlyCollection<Point> {

    /**
     * 获取线串中点坐标的个数
     *
     * @return 点坐标的个数
     * @see java.util.Collection#size()
     */
    @Override
    int size();

    @Override
    default boolean isEmpty() {
        return size() == 0;
    }

    CoordinateSequence getCoordinateSequence();

    default Coordinate getCoordinateAt(int n) {
        return getPointAt(n).getCoordinate();
    }

    Point getPointAt(int n);

    default Point getStartPoint() {
        if (isEmpty()) {
            return null;
        }
        return getPointAt(0);
    }

    default Point getEndPoint() {
        if (isEmpty()) {
            return null;
        }
        return getPointAt(size() - 1);
    }

    default boolean isClosed() {
        if (isEmpty()) {
            return false;
        }
        return getStartPoint().equals(getEndPoint());
    }

    default boolean isRing() {
        return isClosed() && isSimple();
    }

    double getLength();

    Geometry extract(int start, int end);

    @Override
    default GeometryType getType() {
        return GeometryType.LineString;
    }

    @Override
    default int getDimension() {
        return Lineal.DIMENSION;
    }

    @Override
    LineString reverse();

    @Override
    LineString normalize();

    @Override
    LineString clone();

    @Override
    default ReadOnlyIterator<Point> iterator() {
        return new LineStringIterator(this, 0);
    }

    @Override
    default Point[] toArray() {
        return toArray(new Point[size()]);
    }

}
