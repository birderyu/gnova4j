package gnova.geometry.model;

import gnova.geometry.model.pattern.Polygonal;
import gnova.core.*;

/**
 * 多边形
 *
 * <p>多边形用于表示一个多边形，是多个{@link LinearRing 线环}组成的{@link ReadOnlyCollection 只读集合}，是一个2维的几何对象。
 *
 * <p>在多边形中，第一个线环为外环（ExteriorRing），之后的为内环（InteriorRing），
 * 外环为顺时针方向，内环为逆时针方向，
 * 外环表示多边形外部的轮廓，内环表示多边形内部的洞（孔、岛屿），内环可以为空。
 *
 * @see Geometry
 * @see LinearRing
 * @see Polygonal
 * @see ReadOnlyCollection
 * @author birderyu
 * @date 2017/6/21
 * @version 1.0.0
 */
public interface Polygon
        extends Geometry, Polygonal, ReadOnlyCollection<LinearRing> {

    /**
     * 获取多边形中线环的个数
     *
     * @return 线环的个数
     * @see java.util.Collection#size()
     */
    @Override
    default int size() {
        return 1 + getInteriorRingSize();
    }

    LinearRing getExteriorRing();

    int getInteriorRingSize();

    LinearRing getInteriorRingAt(int n);

    double getArea();

    @Override
    default GeometryType getType() {
        return GeometryType.Polygon;
    }

    @Override
    default int getDimension() {
        return Polygonal.DIMENSION;
    }

    @Override
    default boolean isEmpty() {
        return getExteriorRing().isEmpty();
    }

    @Override
    Polygon reverse();

    @Override
    Polygon normalize();

    @Override
    Polygon clone();

    @Override
    default ReadOnlyIterator<LinearRing> iterator() {
        return new ReadOnlyIteratorProxy<>(
                new MultiIterator<>(
                        new SingleIterator<>(getExteriorRing()),
                        new InteriorRingIterator(this, 0)));
    }

    @Override
    default LinearRing[] toArray() {
        return toArray(new LinearRing[size()]);
    }

}
