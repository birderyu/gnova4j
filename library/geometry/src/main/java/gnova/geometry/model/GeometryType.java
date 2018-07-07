package gnova.geometry.model;

/**
 * 几何对象的类型
 * 
 * @author Birderyu
 * @date 2017/6/21
 */
public enum GeometryType {

    /**
     * 空类型
     *
     * @see Geometry#NONE
     */
    None(0, "None"),

    /**
     * 点类型
     *
     * @see Point
     */
    Point(1, "Point"),

    /**
     * 线串类型
     *
     * @see LineString
     */
    LineString(2, "LineString"),

    /**
     * 线环类型
     *
     * @see LinearRing
     */
    LinearRing(3, "LinearRing"),

    /**
     * 多边形类型
     *
     * @see Polygon
     */
    Polygon(4, "Polygon"),

    /**
     * 多点类型
     *
     * @see MultiPoint
     */
    MultiPoint(5, "MultiPoint"),

    /**
     * 多线串类型
     *
     * @see MultiLineString
     */
    MultiLineString(6, "MultiLineString"),

    /**
     * 多多边形类型
     *
     * @see MultiPolygon
     */
    MultiPolygon(7, "MultiPolygon"),

    /**
     * 几何集合类型
     *
     * @see GeometryCollection
     */
    GeometryCollection(8, "GeometryCollection");

    private final int type;
    private final String name;

    GeometryType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static GeometryType from(int type) {
        for (GeometryType gt : GeometryType.values()) {
            if (gt.getType() == type) {
                return gt;
            }
        }
        throw new IllegalArgumentException("不支持的几何类型：" + type);
    }

    public static GeometryType fromName(String name) {
        for (GeometryType gt : GeometryType.values()) {
            if (gt.getName().equalsIgnoreCase(name)) {
                return gt;
            }
        }
        throw new IllegalArgumentException("不支持的几何类型：" + name);
    }

}
