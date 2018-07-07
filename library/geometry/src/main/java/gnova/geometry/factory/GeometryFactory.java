package gnova.geometry.factory;

import gnova.geometry.model.*;
import gnova.geometry.model.extra.Circle2D;

import java.io.Serializable;
import java.util.Collection;

/**
 * 几何对象工厂
 */
public interface GeometryFactory
        extends Serializable {

    /**
     * 获取SRID
     *
     * @return
     */
    int getSRID();

    /**
     * 获取坐标序列工厂
     *
     * @return
     */
    CoordinateSequenceFactory getCoordinateSequenceFactory();

    /**
     * 使用包围盒创建一个几何对象
     *
     * @param bbox 包围盒
     * @return 几何对象
     */
    Geometry createGeometry(BoundingBox bbox);

    /**
     * 使用几何对象创建一个几何对象
     *
     * @param geometry 几何对象
     * @return 几何对象
     */
    Geometry createGeometry(Geometry geometry);

    /**
     * 创建一个点对象
     *
     * @param coordinate 坐标
     * @return 点对象
     */
    Point createPoint(Coordinate coordinate);

    /**
     * 创建一个点对象
     *
     * @param x x坐标
     * @param y y坐标
     * @return 点对象
     */
    default Point createPoint(double x, double y) {
        return createPoint(new Coordinate(x, y));
    }

    /**
     * 创建一个点对象
     *
     * @param x x坐标
     * @param y y坐标
     * @param z z坐标
     * @return 点对象
     */
    default Point createPoint(double x, double y, double z) {
        return createPoint(new Coordinate(x, y, z));
    }

    /**
     * 创建一个线串对象
     *
     * @param coordinates 坐标的数组
     * @return 线串对象
     */
    LineString createLineString(Coordinate[] coordinates);

    /**
     * 创建一个线串对象
     *
     * @param coordinates 坐标的集合
     * @return 线串对象
     */
    default LineString createLineString(Collection<Coordinate> coordinates) {
        if (coordinates == null || coordinates.size() < 2) {
            throw new IllegalArgumentException("Invalid number of points in LineString (must not less than 2): "
                    + coordinates);
        }
        return createLineString(coordinates.toArray(new Coordinate[coordinates.size()]));
    }

    /**
     * 创建一个线串对象
     *
     * @param coordinates 坐标序列
     * @return 线串对象
     */
    LineString createLineString(CoordinateSequence coordinates);

    /**
     * 创建一个线环对象
     *
     * @param coordinates 坐标的数组
     * @return 线串对象
     */
    LinearRing createLinearRing(Coordinate[] coordinates);

    /**
     * 创建一个线环对象
     *
     * @param coordinates 坐标的集合
     * @return 线串对象
     */
    default LinearRing createLinearRing(Collection<Coordinate> coordinates) {
        if (coordinates == null || coordinates.size() < 3) {
            throw new IllegalArgumentException("Invalid number of points in LinearRing (must not less than 3): "
                    + coordinates);
        }
        return createLinearRing(coordinates.toArray(new Coordinate[coordinates.size()]));
    }

    /**
     * 创建一个线环对象
     *
     * @param coordinates 坐标序列
     * @return 线串对象
     */
    LinearRing createLinearRing(CoordinateSequence coordinates);

    /**
     * 创建一个多边形对象
     *
     * @param shell 外环，不可以为null
     * @param holes 内环，可以为null
     * @return 边形对象
     */
    Polygon createPolygon(LinearRing shell, LinearRing[] holes);

    /**
     * 创建一个几何集合对象
     *
     * @param geometries 几何对象的数组
     * @return 几何集合对象
     */
    GeometryCollection createGeometryCollection(Geometry[] geometries);

    /**
     * 创建一个几何集合对象
     *
     * @param geometries 几何对象的集合
     * @return 几何集合对象
     */
    default GeometryCollection createGeometryCollection(Collection<? extends Geometry> geometries) {
        return createGeometryCollection(geometries.toArray(new Geometry[geometries.size()]));
    }

    /**
     * 创建一个多点对象
     *
     * @param points 点对象的数组
     * @return 多点对象
     */
    MultiPoint createMultiPoint(Point[] points);

    /**
     * 创建一个多点对象
     *
     * @param points 点对象的集合
     * @return 多点对象
     */
    default MultiPoint createMultiPoint(Collection<? extends Point> points) {
        return createMultiPoint(points.toArray(new Point[points.size()]));
    }

    /**
     * 创建一个多点对象
     *
     * @param coordinates 坐标的数组
     * @return 多点对象
     */
    MultiPoint createMultiPoint(Coordinate[] coordinates);

    /**
     * 创建一个多点对象
     *
     * @param coordinates 坐标序列
     * @return 多点对象
     */
    MultiPoint createMultiPoint(CoordinateSequence coordinates);

    /**
     * 创建一个多线串对象
     *
     * @param lineStrings 线串对象的数组
     * @return 多线串对象
     */
    MultiLineString createMultiLineString(LineString[] lineStrings);

    /**
     * 创建一个多线串对象
     *
     * @param lineStrings 线串对象的集合
     * @return 多线串对象
     */
    default MultiLineString createMultiLineString(Collection<? extends LineString> lineStrings) {
        return createMultiLineString(lineStrings.toArray(new LineString[lineStrings.size()]));
    }

    /**
     * 创建一个多多边形对象
     *
     * @param polygons 多边形对象的数组
     * @return 多多边形对象
     */
    MultiPolygon createMultiPolygon(Polygon[] polygons);

    /**
     * 创建一个多多边形对象
     *
     * @param polygons 多边形对象的集合
     * @return 多多边形对象
     */
    default MultiPolygon createMultiPolygon(Collection<? extends Polygon> polygons) {
        return createMultiPolygon(polygons.toArray(new Polygon[polygons.size()]));
    }

    /**
     * 创建一个二维圆对象
     *
     * @param centre 圆心
     * @param radius 半径
     * @return 二维圆对象
     */
    default Circle2D createCircle(Coordinate centre, double radius) {
        return new Circle2D(centre.getX(), centre.getY(), radius, this);
    }

}
