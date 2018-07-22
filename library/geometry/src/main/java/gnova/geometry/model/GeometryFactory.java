package gnova.geometry.model;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.extra.Circle2D;

import java.io.Serializable;
import java.util.Collection;

/**
 * 几何对象工厂
 */
public interface GeometryFactory extends Serializable {

    /**
     * 获取SRID
     *
     * @return
     */
    int getSrid();

    /**
     * 获取坐标序列工厂
     *
     * @return 坐标序列工厂，不会返回null
     */
    @NotNull
    CoordinateSequenceFactory getCoordinateSequenceFactory();

    /**
     * 使用包围盒创建一个几何对象
     *
     * <p>若包围盒对象为{@link BoundingBox#NONE 空包围盒对象}，则返回{@link Geometry#NONE 空几何对象}
     *
     * @param bbox 包围盒，不允许为null
     * @return 几何对象，不会返回null
     */
    @NotNull
    Geometry createGeometry(@NotNull BoundingBox bbox);

    /**
     * 使用几何对象创建一个几何对象
     *
     * <p>与{@link Geometry#clone() 克隆}方法所不同的是，该方法会让创建出来的几何对象的工厂变为当前工厂对象。
     *
     * @param geometry 几何对象，不允许为null
     * @return 几何对象，不会返回null
     */
    @NotNull
    Geometry createGeometry(@NotNull Geometry geometry);

    /**
     * 创建一个点对象
     *
     * @param coordinate 坐标，不允许为null
     * @return 点对象，不会返回null
     */
    @NotNull
    Point createPoint(@NotNull Coordinate coordinate);

    /**
     * 创建一个点对象
     *
     * @param x x坐标
     * @param y y坐标
     * @return 点对象，不会返回null
     */
    @NotNull
    default Point createPoint(double x, double y) {
        return createPoint(new Coordinate(x, y));
    }

    /**
     * 创建一个点对象
     *
     * @param x x坐标
     * @param y y坐标
     * @param z z坐标
     * @return 点对象，不会返回null
     */
    @NotNull
    default Point createPoint(double x, double y, double z) {
        return createPoint(new Coordinate(x, y, z));
    }

    /**
     * 创建一个线串对象
     *
     * @param coordinates 坐标的数组，不允许为null，长度至少为2，且数组中不能够包含null或{@link Coordinate#NONE 空坐标对象}
     * @return 线串对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    LineString createLineString(@NotNull Coordinate[] coordinates)
            throws IllegalArgumentException;

    /**
     * 创建一个线串对象
     *
     * @param coordinates 坐标的集合，不允许为null，长度至少为2，且集合中不能够包含null或{@link Coordinate#NONE 空坐标对象}
     * @return 线串对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    default LineString createLineString(@NotNull Collection<Coordinate> coordinates)
            throws IllegalArgumentException {
        return createLineString(coordinates.toArray(new Coordinate[coordinates.size()]));
    }

    /**
     * 创建一个线串对象
     *
     * @param coordinates 坐标序列，不允许为null，长度至少为2，且序列中不能够包含null或{@link Coordinate#NONE 空坐标对象}
     * @return 线串对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    LineString createLineString(@NotNull CoordinateSequence coordinates)
            throws IllegalArgumentException;

    /**
     * 创建一个线环对象
     *
     * @param coordinates 坐标的数组，不允许为null，首尾坐标必须相等，长度至少为4，且数组中不能够包含null或{@link Coordinate#NONE 空坐标对象}
     * @return 线串对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    LinearRing createLinearRing(@NotNull Coordinate[] coordinates) throws IllegalArgumentException;

    /**
     * 创建一个线环对象
     *
     * @param coordinates 坐标的集合，不允许为null，首尾坐标必须相等，长度至少为4，且集合中不能够包含null或{@link Coordinate#NONE 空坐标对象}
     * @return 线串对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    default LinearRing createLinearRing(@NotNull Collection<Coordinate> coordinates)
            throws IllegalArgumentException {
        return createLinearRing(coordinates.toArray(new Coordinate[coordinates.size()]));
    }

    /**
     * 创建一个线环对象
     *
     * @param coordinates 坐标序列，不允许为null，首尾坐标必须相等，长度至少为4，且序列中不能够包含null或{@link Coordinate#NONE 空坐标对象}
     * @return 线串对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    LinearRing createLinearRing(@NotNull CoordinateSequence coordinates) throws IllegalArgumentException;

    /**
     * 创建一个多边形对象
     *
     * @param shell 外环，不允许为null
     * @return 边形对象，不会返回null
     */
    @NotNull
    default Polygon createPolygon(@NotNull LinearRing shell) {
        return createPolygon(shell, null);
    }


    /**
     * 创建一个多边形对象
     *
     * @param shell 外环，不允许为null
     * @param holes 内环，可以为null或空数组，表示无内环，数组中不能够包含null
     * @return 边形对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    Polygon createPolygon(@NotNull LinearRing shell, LinearRing[] holes)
            throws IllegalArgumentException;

    /**
     * 创建一个几何集合对象
     *
     * @param geometries 几何对象的数组，不允许为null，但可以为空数组，且数组中不能够包含null或{@link Geometry#NONE 空几何对象}
     * @return 几何集合对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    GeometryCollection createGeometryCollection(@NotNull Geometry[] geometries)
            throws IllegalArgumentException;

    /**
     * 创建一个几何集合对象
     *
     * @param geometries 几何对象的集合，不允许为null，但可以为空集合，且集合中不能够包含null或{@link Geometry#NONE 空几何对象}
     * @return 几何集合对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    default GeometryCollection createGeometryCollection(@NotNull Collection<? extends Geometry> geometries)
            throws IllegalArgumentException {
        return createGeometryCollection(geometries.toArray(new Geometry[geometries.size()]));
    }

    /**
     * 创建一个多点对象
     *
     * @param points 点对象的数组，不允许为null，但可以为空数组，且数组中不能够包含null
     * @return 多点对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    MultiPoint createMultiPoint(@NotNull Point[] points) throws IllegalArgumentException;

    /**
     * 创建一个多点对象
     *
     * @param points 点对象的集合，不允许为null，但可以为空数组，且数组中不能够包含null
     * @return 多点对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    default MultiPoint createMultiPoint(@NotNull Collection<? extends Point> points)
            throws IllegalArgumentException {
        return createMultiPoint(points.toArray(new Point[points.size()]));
    }

    /**
     * 创建一个多点对象
     *
     * @param coordinates 坐标的数组，不允许为null，但可以为空数组，且数组中不能够包含null或{@link Coordinate#NONE 空坐标对象}
     * @return 多点对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    MultiPoint createMultiPoint(@NotNull Coordinate[] coordinates)
            throws IllegalArgumentException;

    /**
     * 创建一个多点对象
     *
     * @param coordinates 坐标序列，不允许为null，但可以为空序列，且序列中不能够包含null或{@link Coordinate#NONE 空坐标对象}
     * @return 多点对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    MultiPoint createMultiPoint(@NotNull CoordinateSequence coordinates)
            throws IllegalArgumentException;

    /**
     * 创建一个多线串对象
     *
     * @param lineStrings 线串对象的数组，不允许为null，但可以为空数组，且数组中不能够包含null
     * @return 多线串对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    MultiLineString createMultiLineString(@NotNull LineString[] lineStrings)
            throws IllegalArgumentException;

    /**
     * 创建一个多线串对象
     *
     * @param lineStrings 线串对象的集合，不允许为null，但可以为空集合，且集合中不能够包含null
     * @return 多线串对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    default MultiLineString createMultiLineString(@NotNull Collection<? extends LineString> lineStrings)
            throws IllegalArgumentException {
        return createMultiLineString(lineStrings.toArray(new LineString[lineStrings.size()]));
    }

    /**
     * 创建一个多多边形对象
     *
     * @param polygons 多边形对象的数组，不允许为null，但可以为空数组，且数组中不能够包含null
     * @return 多多边形对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    MultiPolygon createMultiPolygon(@NotNull Polygon[] polygons)
            throws IllegalArgumentException;

    /**
     * 创建一个多多边形对象
     *
     * @param polygons 多边形对象的集合，不允许为null，但可以为空集合，且集合中不能够包含null
     * @return 多多边形对象，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    default MultiPolygon createMultiPolygon(@NotNull Collection<? extends Polygon> polygons)
            throws IllegalArgumentException {
        return createMultiPolygon(polygons.toArray(new Polygon[polygons.size()]));
    }

    /**
     * 创建一个二维圆对象
     *
     * @param centre 圆心，不允许为null，也不允许为{@link Coordinate#NONE 空坐标对象}
     * @param radius 半径
     * @return 二维圆对象，不会返回null
     * @exception IllegalArgumentException 若参数不合法，则抛出此异常
     */
    default Circle2D createCircle(@NotNull Coordinate centre, double radius)
            throws IllegalArgumentException {
        if (centre == Coordinate.NONE) {
            throw new IllegalArgumentException("centre is NONE");
        }
        return new Circle2D(centre.getX(), centre.getY(), radius, this);
    }

}
