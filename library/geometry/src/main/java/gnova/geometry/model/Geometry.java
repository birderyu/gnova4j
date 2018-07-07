package gnova.geometry.model;

import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;
import gnova.geometry.factory.FactoryFinder;
import gnova.geometry.factory.GeometryFactory;
import gnova.geometry.json.GeometryJSON;
import gnova.geometry.model.operator.ProximityOperator;
import gnova.geometry.model.operator.RelationalOperator;
import gnova.geometry.model.operator.TopologicalOperator;
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
 * <p>几何对象符合GeoJSON中关于几何类型的定义，包括如下几种类型：
 * <br>{@link Point 点}，表示一个坐标点；
 * <br>{@link LineString 线串}，表示一串坐标点组成的一条折线；
 * <br>{@link LinearRing 线环}，特殊的{@link LineString 线串}，表示收尾相连的一条折线，它不是一种单独的类型，仅用于构建{@link Polygon 多边形}；
 * <br>{@link Polygon 多边形}，表示一个多边形，是多条{@link LinearRing 线环}的组合，第一个线环为外环（ExteriorRing），之后的环为内环（InteriorRing），内环可以为空；
 * <br>{@link MultiPoint 多点}，表示{@link Point 点}的集合；
 * <br>{@link MultiLineString 多线串}，表示{@link LineString 线串}的集合；
 * <br>{@link MultiPolygon 多多边形}，表示{@link Polygon 多边形}的集合；
 * <br>{@link GeometryCollection 几何对象集合}，表示{@link Geometry 几何对象}的集合。
 *
 * <p>几何对象继承自{@link TopologicalOperator 空间拓扑操作}、{@link RelationalOperator 空间关系操作}和{@link ProximityOperator 空间距离操作}
 * 这三个接口，包括了大量与空间计算相关的方法。
 *
 * @see Comparable
 * @see Cloneable
 * @see Serializable
 * @see TopologicalOperator
 * @see RelationalOperator
 * @see ProximityOperator
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
        TopologicalOperator, RelationalOperator, ProximityOperator {

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
    default Coordinate getCoordinate() {
        Coordinate[] coordinates = getCoordinates();
        if (coordinates.length == 0) {
            return Coordinate.NONE;
        }
        return coordinates[0];
    }

    /**
     * 获取坐标的数组
     *
     * @return 坐标的数组
     */
    @NotNull
    Coordinate[] getCoordinates();

    /**
     * 获取几何对象的SRID
     *
     * @return 几何对象的SRID
     */
    default int getSRID() {
        return getFactory().getSRID();
    }

    /**
     * 设置几何对象的SRID
     *
     * @param srid SRID
     */
    void setSRID(int srid);

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
     * @param <JO> JSON对象的类型
     * @param <JA> JSON数组的类型
     * @return JSON对象
     */
    @NotNull
    <JO, JA> JO toGeometryJSON(@NotNull JsonObjectBuilder<JO> job,
                               @NotNull JsonArrayBuilder<JA> jab);

    /**
     * 判断几何对象是否相等
     *
     * @param geometry 几何对象
     * @return 若几何对象相等，则返回true，否则返回false
     */
    @Override
    boolean equals(@NotNull Geometry geometry);

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

    /**
     * 一个空的几何对象
     */
    Geometry NONE = new Geometry() {

        @Override
        public GeometryType getType() {
            return GeometryType.None;
        }

        @Override
        public BoundingBox getBoundingBox() {
            return BoundingBox.NONE;
        }

        @Override
        public GeometryFactory getFactory() {
            return FactoryFinder.getDefaultGeometryFactory();
        }

        @Override
        public int getDimension() {
            return 0;
        }

        @Override
        public int getBoundaryDimension() {
            return 0;
        }

        @Override
        public int getCoordinateDimension() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean isSimple() {
            return true;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public Coordinate[] getCoordinates() {
            return new Coordinate[0];
        }

        @Override
        public void setSRID(int srid) {

        }

        @Override
        public Geometry reverse() {
            return this;
        }

        @Override
        public Geometry normalize() {
            return this;
        }

        @Override
        public String toGeometryJSON() {
            return GeometryJSON.NONE.toString();
        }

        @Override
        public <JO, JA> JO toGeometryJSON(JsonObjectBuilder<JO> job, JsonArrayBuilder<JA> jab) {
            return GeometryJSON.NONE.toJsonObject(job, jab);
        }

        @Override
        public boolean equals(Geometry geometry) {
            return geometry == this;
        }

        @Override
        public Geometry clone() {
            return this;
        }

        @Override
        public double distance(Geometry other) {
            return 0;
        }

        @Override
        public Coordinate[] nearestPoints(Geometry other) {
            return new Coordinate[0];
        }

        @Override
        public boolean contains(Geometry other) {
            return false;
        }

        @Override
        public boolean crosses(Geometry other) {
            return false;
        }

        @Override
        public boolean touches(Geometry other) {
            return false;
        }

        @Override
        public boolean disjoint(Geometry other) {
            return false;
        }

        @Override
        public boolean intersects(Geometry other) {
            return false;
        }

        @Override
        public boolean within(Geometry other) {
            return false;
        }

        @Override
        public boolean overlaps(Geometry other) {
            return false;
        }

        @Override
        public boolean covers(Geometry other) {
            return false;
        }

        @Override
        public boolean coveredBy(Geometry other) {
            return false;
        }

        @Override
        public Geometry getBoundary() {
            return this;
        }

        @Override
        public Geometry union() {
            return null;
        }

        @Override
        public Geometry convexHull() {
            return null;
        }

        @Override
        public Geometry clip(BoundingBox bbox) {
            return null;
        }

        @Override
        public Geometry split(Geometry bladeIn) {
            return null;
        }

        @Override
        public Geometry cut(Geometry bladeIn) {
            return null;
        }

        @Override
        public Polygon buffer(double distance) {
            return null;
        }

        @Override
        public Polygon buffer(double distance, int quadrantSegments) {
            return null;
        }

        @Override
        public Polygon buffer(double distance, int quadrantSegments, int endCapStyle) {
            return null;
        }

        @Override
        public Geometry intersection(Geometry other) {
            return null;
        }

        @Override
        public Geometry union(Geometry other) {
            return null;
        }

        @Override
        public Geometry difference(Geometry other) {
            return null;
        }

        @Override
        public Geometry symmetricDifference(Geometry other) {
            return null;
        }

        @Override
        public Point centroid() {
            return null;
        }

        @Override
        public Point interior() {
            return null;
        }

        @Override
        public Geometry simplify(double distanceTolerance) {
            return null;
        }

        @Override
        public Geometry triangulation(double distanceTolerance, GeometryType resultType) {
            return null;
        }

        @Override
        public int compareTo(Geometry o) {
            return 0;
        }
    };

}
