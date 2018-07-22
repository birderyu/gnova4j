package gnova.geometry.model;

import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;
import gnova.core.json.JsonObject;
import gnova.geometry.model.operator.*;
import gnova.core.json.JsonArrayBuilder;
import gnova.core.json.JsonObjectBuilder;

import java.io.Serializable;

/**
 * 几何对象
 *
 * <p>几何对象用来表示带有几何坐标信息的对象。
 *
 * <p>几何对象是一个{@link Immutable 不可变的}对象，这说明几何对象本身是线程安全的。
 *
 * <p>几何对象符合GeoJSON（RFC 7946）中关于几何类型的定义，包括如下几种类型：
 * <br>{@link Point 点}，表示一个坐标点；
 * <br>{@link LineString 线串}，表示一串坐标点组成的一条折线；
 * <br>{@link LinearRing 线环}，特殊的{@link LineString 线串}，表示收尾相连的一条折线，它不是一种单独的类型，仅用于构建{@link Polygon 多边形}；
 * <br>{@link Polygon 多边形}，表示一个多边形，是多条{@link LinearRing 线环}的组合，第一个线环为外环（ExteriorRing），之后的环为内环（InteriorRing），内环可以为空；
 * <br>{@link MultiPoint 多点}，表示{@link Point 点}的集合；
 * <br>{@link MultiLineString 多线串}，表示{@link LineString 线串}的集合；
 * <br>{@link MultiPolygon 多多边形}，表示{@link Polygon 多边形}的集合；
 * <br>{@link GeometryCollection 几何对象集合}，表示{@link Geometry 几何对象}的集合。
 *
 * <p>几何对象继承自{@link TopologicalOperator 空间拓扑操作}、{@link RelationalOperator 空间关系操作}、
 * {@link ProximityOperator 空间距离操作}、{@link SimplifierOperator 空间简化操作}和{@link AffineOperator 空间仿射操作}
 * 这五个接口，包括了大量与空间计算相关的方法。
 *
 * @see Comparable
 * @see Cloneable
 * @see Serializable
 * @see TopologicalOperator
 * @see RelationalOperator
 * @see ProximityOperator
 * @see SimplifierOperator
 * @see AffineOperator
 * @see Point
 * @see LineString
 * @see LinearRing
 * @see Polygon
 * @see MultiPoint
 * @see MultiLineString
 * @see MultiPolygon
 * @see GeometryCollection
 * @author birderyu
 * @date 2017/6/21
 * @version 1.0.0
 */
@Immutable
public interface Geometry
        extends Comparable<Geometry>, Cloneable, Serializable,
        TopologicalOperator, RelationalOperator, ProximityOperator, SimplifierOperator, AffineOperator {

    /**
     * 一个空的几何对象
     */
    Geometry NONE = NullGeometry.NONE;

    /**
     * 获取几何对象的类型
     *
     * @return 几何对象的类型
     */
    @NotNull
    GeometryType getType();

    /**
     * 获取几何对象的包围盒
     *
     * @return 几何对象的包围盒
     */
    @NotNull
    BoundingBox getBoundingBox();

    /**
     * 获取创建该几何对象的工厂接口
     *
     * @return 几何对象的工厂
     */
    @NotNull
    GeometryFactory getFactory();

    /**
     * 获取几何对象的维度
     *
     * <p>0：点形几何对象
     * <br>1：线形几何对象
     * <br>2：面形几何对象
     * <br>4：体形几何对象
     *
     * @return 几何对象的维度
     */
    int getDimension();

    /**
     * 获取几何对象边界的维度
     *
     * <p>0：点形几何对象
     * <br>1：线形几何对象
     * <br>2：面形几何对象
     * <br>4：体形几何对象
     *
     * @return 几何对象边界的维度
     */
    int getBoundaryDimension();

    /**
     * 获取几何对象坐标的维度
     *
     * <p>2：二维，包含X、Y坐标
     * <br>3：三维，包含X、Y、Z坐标
     * <br>4：四维，包含X、Y、Z坐标和度量值
     *
     * @return 几何对象坐标的维度
     */
    int getCoordinateDimension();

    /**
     * 判断几何对象是否为空
     *
     * @return 若几何对象为空，则返回true，否则返回false
     */
    boolean isEmpty();

    /**
     * 判断几何对象是否为简单的
     *
     * @return 若几何对象为简单的对象，则返回true，否则返回false
     */
    boolean isSimple();

    /**
     * 判断几何对象是否为有效的
     *
     * @return 若几何对象为有效的对象，则返回true，否则返回false
     */
    boolean isValid();

    /**
     * 获取坐标
     *
     * @return 坐标值
     */
    @NotNull
    Coordinate getCoordinate();

    /**
     * 获取坐标的集合
     *
     * @return 坐标的集合
     */
    @NotNull
    Iterable<Coordinate> getCoordinates();

    /**
     * 获取几何对象的SRID
     *
     * @return 几何对象的SRID
     */
    default int getSrid() {
        return getFactory().getSrid();
    }

    /**
     * 获取几何对象的浮点精度
     *
     * @return 对象的浮点精度
     */
    @NotNull
    default Precision getPrecision() {
        return getFactory().getPrecision();
    }

    /**
     * 翻转当前的几何对象成为一个新的几何对象
     *
     * @return 几何对象
     */
    @NotNull
    Geometry reverse();

    /**
     * 标准化当前的几何对象成为一个新的几何对象
     *
     * @return 几何对象
     */
    @NotNull
    Geometry normalize();

    /**
     * 将几何对象转化为JSON字符串
     *
     * @return
     */
    @NotNull
    String toGeometryJSON();

    /**
     * 转换成JSON对象
     *
     * @param job JSON对象构造器
     * @param jab JSON数组构造器
     * @return JSON对象
     */
    @NotNull
    JsonObject toGeometryJSON(@NotNull JsonObjectBuilder job, @NotNull JsonArrayBuilder jab);

    /**
     * 判断几何对象是否精确相等
     *
     * @param other
     * @return
     */
    boolean exactlyEquals(@NotNull Geometry other);

    /**
     * 判断几何对象是否精确相等
     *
     * @param other
     * @param tolerance 坐标距离容差，若两个坐标点的距离小于该值，则认为坐标一致
     * @return
     */
    boolean exactlyEquals(@NotNull Geometry other, double tolerance);

    /**
     * 判断对象是否相等
     *
     * 这里的相等，指的是几何对象间的精确相等
     *
     * @param obj
     * @return
     */
    @Override
    boolean equals(Object obj);

    @Override
    int hashCode();

    @Override
    @NotNull
    String toString();

    /**
     * 拷贝一个几何对象
     *
     * @return 几何对象的拷贝
     */
    @NotNull
    Geometry clone();

    static boolean exactlyEquals(@NotNull Geometry left, @NotNull Geometry right) {
        return left.exactlyEquals(right);
    }

    static boolean exactlyEquals(@NotNull Geometry left, @NotNull Geometry right, double tolerance) {
        return left.exactlyEquals(right, tolerance);
    }

}
