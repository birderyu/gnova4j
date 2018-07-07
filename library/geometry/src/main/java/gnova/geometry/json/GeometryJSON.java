package gnova.geometry.json;

import gnova.core.annotation.Checked;
import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;
import gnova.geometry.factory.GeometryFactory;
import gnova.geometry.model.*;
import gnova.core.json.JsonArrayBuilder;
import gnova.core.json.JsonObjectBuilder;
import gnova.core.json.JsonArray;

import java.util.Arrays;

/**
 * 几何对象JSON，是一个JSON对象
 *
 * RFC 7946 GeoJSON标准定义中的几何对象
 */
@Immutable
public abstract class GeometryJSON
        implements Cloneable {

    /**
     * 一个空的GeometryJSON
     */
    public static final GeometryJSON NONE = new GeometryJSON() {

        @Override
        public String getType() {
            return "null";
        }

        @Override
        public Geometry toGeometry(GeometryFactory factory) {
            throw new UnsupportedOperationException("toGeometry");
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString() {
            return "{\"type\": null,\"coordinates\": null}";
        }

        @Override
        public GeometryJSON clone() {
            throw new UnsupportedOperationException("clone");
        }

        @Override
        public <JO, JA> JO toJsonObject(JsonObjectBuilder<JO> job,
                                        JsonArrayBuilder<JA> jab) {
            return job.build()
                    .append("type", null)
                    .append("coordinates", null)
                    .getSubject();
        }
    };

    /**
     * 类型属性的名称
     */
    public static final String FIELD_NAME_TYPE = "type";

    /**
     * 坐标属性的名称
     */
    public static final String FIELD_NAME_COORDINATES = "coordinates";

    /**
     * 几何集合属性的名称
     */
    public static final String FIELD_NAME_GEOMETRIES = "geometries";

    /**
     * 点类型的类型名称
     */
    public static final String TYPE_POINT = "Point";

    /**
     * 折线类型的类型名称
     */
    public static final String TYPE_LINESTRING = "LineString";

    /**
     * 多边形类型的类型名称
     */
    public static final String TYPE_POLYGON = "Polygon";

    /**
     * 多点类型的类型名称
     */
    public static final String TYPE_MULTIPOINT = "MultiPoint";

    /**
     * 多折线类型的类型名称
     */
    public static final String TYPE_MULTILINESTRING = "MultiLineString";

    /**
     * 多多边形类型的类型名称
     */
    public static final String TYPE_MULTIPOLYGON = "MultiPolygon";

    /**
     * 几何集合类型的类型名称
     */
    public static final String TYPE_GEOMETRYCOLLECTION = "GeometryCollection";

    /**
     * 将一个字符串表示的GeometryJSON类型转换为几何对象类型
     *
     * @param type GeometryJSON类型
     * @return 几何对象类型
     */
    public static GeometryType toType(String type) {
        switch (type) {
            case TYPE_POINT:
                return GeometryType.Point;
            case TYPE_LINESTRING:
                return GeometryType.LineString;
            case TYPE_POLYGON:
                return GeometryType.Polygon;
            case TYPE_MULTIPOINT:
                return GeometryType.Point;
            case TYPE_MULTILINESTRING:
                return GeometryType.MultiPoint;
            case TYPE_MULTIPOLYGON:
                return GeometryType.MultiLineString;
            case TYPE_GEOMETRYCOLLECTION:
                return GeometryType.GeometryCollection;
        }
        return GeometryType.None;
    }

    /**
     * 将一个几何对象转换成GeometryJSON
     *
     * @param geometry 几何对象
     * @return GeometryJSON
     */
    public static GeometryJSON fromGeometry(@NotNull Geometry geometry) {

        if (geometry == Geometry.NONE) {
            return GeometryJSON.NONE;
        }
        switch (geometry.getType()) {
            case Point:
                return GeometryJSON.fromPoint((Point) geometry);
            case LineString:
            case LinearRing:
                return GeometryJSON.fromLineString((LineString) geometry);
            case Polygon:
                return GeometryJSON.fromPolygon((Polygon) geometry);
            case MultiPoint:
                return GeometryJSON.fromMultiPoint((MultiPoint) geometry);
            case MultiLineString:
                return GeometryJSON.fromMultiLineString((MultiLineString) geometry);
            case MultiPolygon:
                return GeometryJSON.fromMultiPolygon((MultiPolygon) geometry);
            case GeometryCollection:
                return GeometryJSON.fromGeometryCollection((GeometryCollection) geometry);
        }
        return GeometryJSON.NONE;
    }

    /**
     * 将一个点对象转换成PointJSON
     *
     * @param point 点对象
     * @return PointJSON
     */
    public static PointJSON fromPoint(Point point) {
        return new PointJSON(new Position(fromPoint0(point)));
    }

    /**
     * 将一个折线对象转换成LineStringJSON
     *
     * @param lineString 折线对象
     * @return LineStringJSON
     */
    public static LineStringJSON fromLineString(LineString lineString) {
        return new LineStringJSON(new PositionArray(fromLineString0(lineString)));
    }

    /**
     * 将一个多边形对象转换成PolygonJSON
     *
     * @param polygon 多边形对象
     * @return PolygonJSON
     */
    public static PolygonJSON fromPolygon(Polygon polygon) {
        return new PolygonJSON(new PositionArrayArray(fromPolygon0(polygon)));
    }

    /**
     * 将一个多点对象转换成MultiPointJSON
     *
     * @param multiPoint 多点对象
     * @return MultiPointJSON
     */
    public static MultiPointJSON fromMultiPoint(MultiPoint multiPoint) {
        return new MultiPointJSON(new PositionArray(fromMultiPoint0(multiPoint)));
    }

    /**
     * 将一个多折线对象转换成MultiLineStringJSON
     *
     * @param multiLineString 多折线对象
     * @return MultiLineStringJSON
     */
    public static MultiLineStringJSON fromMultiLineString(MultiLineString multiLineString) {
        return new MultiLineStringJSON(new PositionArrayArray(fromMultiLineString0(multiLineString)));
    }

    /**
     * 将一个多多边形对象转换成MultiPolygonJSON
     *
     * @param multiPolygon 多多边形对象
     * @return MultiPolygonJSON
     */
    public static MultiPolygonJSON fromMultiPolygon(MultiPolygon multiPolygon) {
        return new MultiPolygonJSON(new PositionArrayArrayArray(fromMultiPolygon0(multiPolygon)));
    }

    /**
     * 将一个几何集合对象转换成GeometryCollectionJSON
     *
     * @param geometries 几何集合对象
     * @return GeometryCollectionJSON
     */
    public static GeometryCollectionJSON fromGeometryCollection(GeometryCollection geometries) {
        switch (geometries.getType()) {
            case MultiPoint:
            case MultiLineString:
            case MultiPolygon:
                throw new IllegalArgumentException("geometries must be a GeometryCollection.");
        }
        int count = geometries.size();
        GeometryJSON[] geometryJSONs = new GeometryJSON[count];
        for (int i = 0; i < count; i++) {
            geometryJSONs[i] = GeometryJSON.fromGeometry(geometries.getGeometryAt(i));
        }
        return new GeometryCollectionJSON(geometryJSONs);
    }

    /**
     * 获取类型
     *
     * @return GeometryJSON的类型，为下面的值之一：
     *      <br>{@link GeometryJSON#TYPE_POINT}：点类型；
     *      <br>{@link GeometryJSON#TYPE_LINESTRING}：折线类型；
     *      <br>{@link GeometryJSON#TYPE_POLYGON}：多边形类型；
     *      <br>{@link GeometryJSON#TYPE_MULTIPOINT}：多点类型；
     *      <br>{@link GeometryJSON#TYPE_MULTILINESTRING}：多折线类型；
     *      <br>{@link GeometryJSON#TYPE_MULTIPOLYGON}：多多边形类型；
     *      <br>{@link GeometryJSON#TYPE_GEOMETRYCOLLECTION}：几何集合类型。
     */
    public abstract String getType();

    /**
     * 将当前对象转换为几何对象
     *
     * @param factory 几何对象工厂
     * @return 几何对象
     */
    public abstract Geometry toGeometry(GeometryFactory factory);

    /**
     * 判断两个对象是否相等
     *
     * @param o 其他对象，必须是一个GeometryJSON对象
     * @return 若相等，则返回true，否则返回false
     */
    @Override
    public abstract boolean equals(Object o);

    /**
     * 获取当前对象的哈希码
     *
     * @return
     */
    @Override
    public abstract int hashCode();

    /**
     * 转换成JSON字符串
     *
     * @return 字符串
     */
    @Override
    public abstract String toString();

    /**
     * 拷贝当前对象成为一个新的GeometryJSON对象
     *
     * @return GeometryJSON
     */
    @Override
    public abstract GeometryJSON clone();

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
    public abstract <JO, JA> JO toJsonObject(@NotNull JsonObjectBuilder<JO> job,
                                             @NotNull JsonArrayBuilder<JA> jab);

    /**
     * 位置（坐标）
     *
     * RFC 7946 GeoJSON标准定义中的位置对象
     */
    static final class Position
            implements Cloneable {

        private final double[] values;

        public Position(double[] values) {
            this.values = Arrays.copyOf(values, values.length);
        }

        public int size() {
            return values == null ? 0 : values.length;
        }

        public double[] getValues() {
            return values;
        }

        public double[] getCopyValues() {
            return values == null ? null : Arrays.copyOf(values, values.length);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Arrays.equals(values, position.values);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(values);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < values.length; i++) {
                sb.append(values[i]);
                if (i != values.length - 1) {
                    sb.append(", ");
                }
            }
            return sb.append("]").toString();
        }

        @Override
        public Position clone() {
            return new Position(values);
        }

        public <JA> JA toJsonArray(@NotNull JsonArrayBuilder<JA> jab) {
            JsonArray<JA> array = jab.build();
            for (int i = 0; i < values.length; i++) {
                array.add(values[i]);
            }
            return array.getSubject();
        }
    }

    /**
     * 位置的数组
     */
    static final class PositionArray
            implements Cloneable  {

        private final Position[] positions;

        public PositionArray(double[][] values) {
            positions = toPositions(values);
        }

        public int size() {
            return positions == null ? 0 : positions.length;
        }

        public Position[] getPositions() {
            return positions;
        }

        public double[][] getValues() {
            double[][] values = new double[positions.length][];
            for (int i = 0; i < values.length; i++) {
                values[i] = positions[i].getValues();
            }
            return values;
        }

        public double[][] getCopyValues() {
            double[][] values = new double[positions.length][];
            for (int i = 0; i < values.length; i++) {
                values[i] = positions[i].getCopyValues();
            }
            return values;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PositionArray that = (PositionArray) o;
            return Arrays.equals(positions, that.positions);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(positions);
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer("[");
            for (int i = 0; i < positions.length; i++) {
                sb.append(positions[i].toString());
                if (i != positions.length - 1) {
                    sb.append(", ");
                }
            }
            return sb.append("]").toString();
        }

        @Override
        public PositionArray clone() {
            return new PositionArray(getValues());
        }

        public <JA> JA toJsonArray(@NotNull JsonArrayBuilder<JA> jab) {
            JsonArray<JA> array = jab.build();
            for (int i = 0; i < positions.length; i++) {
                array.add(positions[i].toJsonArray(jab));
            }
            return array.getSubject();
        }
    }

    /**
     * 位置数组的数组
     */
    static final class PositionArrayArray
            implements Cloneable {

        private final PositionArray[] positions;

        public PositionArrayArray(double[][][] values) {
            positions = toPositions(values);
        }

        public int size() {
            return positions == null ? 0 : positions.length;
        }

        public PositionArray[] getPositions() {
            return positions;
        }

        public double[][][] getValues() {
            double[][][] values = new double[positions.length][][];
            for (int i = 0; i < values.length; i++) {
                values[i] = positions[i].getValues();
            }
            return values;
        }

        public double[][][] getCopyValues() {
            double[][][] values = new double[positions.length][][];
            for (int i = 0; i < values.length; i++) {
                values[i] = positions[i].getCopyValues();
            }
            return values;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PositionArrayArray that = (PositionArrayArray) o;
            return Arrays.equals(positions, that.positions);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(positions);
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer("[");
            for (int i = 0; i < positions.length; i++) {
                sb.append(positions[i].toString());
                if (i != positions.length - 1) {
                    sb.append(", ");
                }
            }
            return sb.append("]").toString();
        }

        @Override
        public PositionArrayArray clone() {
            return new PositionArrayArray(getValues());
        }

        public <JA> JA toJsonArray(@NotNull JsonArrayBuilder<JA> jab) {
            JsonArray<JA> array = jab.build();
            for (int i = 0; i < positions.length; i++) {
                array.add(positions[i].toJsonArray(jab));
            }
            return array.getSubject();
        }

    }

    /**
     * 位置数组数组的数组
     */
    static final class PositionArrayArrayArray
            implements Cloneable {

        private final PositionArrayArray[] positions;

        public PositionArrayArrayArray(double[][][][] values) {
            positions = toPositions(values);
        }

        public int size() {
            return positions == null ? 0 : positions.length;
        }

        public PositionArrayArray[] getPositions() {
            return positions;
        }

        public double[][][][] getValues() {
            double[][][][] values = new double[positions.length][][][];
            for (int i = 0; i < values.length; i++) {
                values[i] = positions[i].getValues();
            }
            return values;
        }

        public double[][][][] getCopyValues() {
            double[][][][] values = new double[positions.length][][][];
            for (int i = 0; i < values.length; i++) {
                values[i] = positions[i].getCopyValues();
            }
            return values;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PositionArrayArrayArray that = (PositionArrayArrayArray) o;
            return Arrays.equals(positions, that.positions);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(positions);
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer("[");
            for (int i = 0; i < positions.length; i++) {
                sb.append(positions[i].toString());
                if (i != positions.length - 1) {
                    sb.append(", ");
                }
            }
            return sb.append("]").toString();
        }

        @Override
        public PositionArrayArrayArray clone() {
            return new PositionArrayArrayArray(getValues());
        }

        public <JA> JA toJsonArray(@NotNull JsonArrayBuilder<JA> jab) {
            JsonArray<JA> array = jab.build();
            for (int i = 0; i < positions.length; i++) {
                array.add(positions[i].toJsonArray(jab));
            }
            return array.getSubject();
        }
    }

    protected static double[] fromCoordinate0(Coordinate coordinate) {
        double[] values = null;
        if (coordinate.hasZ() && coordinate.hasM()) {
            values = new double[4];
            values[2] = coordinate.getZ();
            values[3] = coordinate.getM();
        } else if (coordinate.hasZ()) {
            values = new double[3];
            values[2] = coordinate.getZ();
        } else if (coordinate.hasM()) {
            values = new double[3];
            values[2] = coordinate.getM();
        } else {
            values = new double[2];
        }
        values[0] = coordinate.getX();
        values[1] = coordinate.getY();
        return values;
    }

    protected static double[] fromPoint0(Point point) {
        return fromCoordinate0(point.getCoordinate());
    }

    protected static double[][] fromLineString0(LineString lineString) {
        int count = lineString.size();
        double[][] coordinates = new double[count][];
        for (int i = 0; i < count; i++) {
            coordinates[i] = fromCoordinate0(lineString.getCoordinateAt(i));
        }
        return coordinates;
    }

    protected static double[][][] fromPolygon0(Polygon polygon) {
        int count = polygon.getInteriorRingSize() + 1;
        double[][][] coordinates = new double[count][][];
        coordinates[0] = fromLineString0(polygon.getExteriorRing());
        for (int i = 1; i < count; i++) {
            coordinates[i] = fromLineString0(polygon.getInteriorRingAt(i - 1));
        }
        return coordinates;
    }

    protected static double[][] fromMultiPoint0(MultiPoint multiPoint) {
        int count = multiPoint.size();
        double[][] coordinates = new double[count][];
        for (int i = 0; i < count; i++) {
            coordinates[i] = fromPoint0(multiPoint.getPointAt(i));
        }
        return coordinates;
    }

    protected static double[][][] fromMultiLineString0(MultiLineString multiLineString) {
        int count = multiLineString.size();
        double[][][] coordinates = new double[count][][];
        for (int i = 1; i < count; i++) {
            coordinates[i] = fromLineString0(multiLineString.getLineStringAt(i));
        }
        return coordinates;
    }

    protected static double[][][][] fromMultiPolygon0(MultiPolygon multiPolygon) {
        int count = multiPolygon.size();
        double[][][][] coordinates = new double[count][][][];
        for (int i = 1; i < count; i++) {
            coordinates[i] = fromPolygon0(multiPolygon.getPolygonAt(i));
        }
        return coordinates;
    }

    protected static Position toPosition(@Checked double[] coordinates) {
        return new Position(coordinates);
    }

    protected static Position[] toPositions(@Checked double[][] coordinates) {
        Position[] positions = new Position[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            positions[i] = new Position(coordinates[i]);
        }
        return positions;
    }

    protected static PositionArray[] toPositions(@Checked double[][][] coordinates) {
        PositionArray[] positions = new PositionArray[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            positions[i] = new PositionArray(coordinates[i]);
        }
        return positions;
    }

    protected static PositionArrayArray[] toPositions(@Checked double[][][][] coordinates) {
        PositionArrayArray[] positions = new PositionArrayArray[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            positions[i] = new PositionArrayArray(coordinates[i]);
        }
        return positions;
    }

    protected static Coordinate toCoordinate(double[] coordinates) {
        return new Coordinate(coordinates[0], coordinates[1]);
    }

    protected static Coordinate[] toCoordinates(double[][] coordinates) {
        Coordinate[] coords = new Coordinate[coordinates.length];
        for (int i = 0; i < coords.length; i++) {
            coords[i] = toCoordinate(coordinates[i]);
        }
        return coords;
    }
}
