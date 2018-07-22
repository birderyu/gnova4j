package gnova.geometry.io.json;

import gnova.core.json.JsonArray;
import gnova.core.json.JsonObject;
import gnova.core.json.JsonParseException;
import gnova.core.json.JsonParser;
import gnova.geometry.io.TextGeometryReader;
import gnova.geometry.model.GeometryFactory;
import gnova.geometry.io.GeometryIOException;
import gnova.geometry.json.*;
import gnova.geometry.model.*;

import java.io.Reader;

/**
 * Created by Birderyu on 2017/6/21.
 */
public class JsonGeometryReader
        implements TextGeometryReader {

    private final GeometryFactory factory;
    private final JsonParser parser;

    public JsonGeometryReader(GeometryFactory factory, JsonParser parser) {
        this.factory = factory;
        this.parser = parser;
    }

    @Override
    public Geometry read(String json) throws GeometryIOException {
        return read(getJsonObject(json));
    }

    @Override
    public Point readPoint(String json) throws GeometryIOException {
        return readPoint(getJsonObject(json), false);
    }

    @Override
    public LineString readLineString(String json) throws GeometryIOException {
        return readLineString(getJsonObject(json), false);
    }

    @Override
    public Polygon readPolygon(String json) throws GeometryIOException {
        return readPolygon(getJsonObject(json), false);
    }

    @Override
    public GeometryCollection readGeometryCollection(String json) throws GeometryIOException {
        return readGeometryCollection(getJsonObject(json), false);
    }

    @Override
    public MultiPoint readMultiPoint(String json) throws GeometryIOException {
        return readMultiPoint(getJsonObject(json), false);
    }

    @Override
    public MultiLineString readMultiLineString(String json) throws GeometryIOException {
        return readMultiLineString(getJsonObject(json), false);
    }

    @Override
    public MultiPolygon readMultiPolygon(String json) throws GeometryIOException {
        return readMultiPolygon(getJsonObject(json), false);
    }

    @Override
    public Geometry read(Reader reader) throws GeometryIOException {
        return read(getJsonObject(reader));
    }

    @Override
    public Point readPoint(Reader reader) throws GeometryIOException {
        return readPoint(getJsonObject(reader), false);
    }

    @Override
    public LineString readLineString(Reader reader) throws GeometryIOException {
        return readLineString(getJsonObject(reader), false);
    }

    @Override
    public Polygon readPolygon(Reader reader) throws GeometryIOException {
        return readPolygon(getJsonObject(reader), false);
    }

    @Override
    public GeometryCollection readGeometryCollection(Reader reader) throws GeometryIOException {
        return readGeometryCollection(getJsonObject(reader), false);
    }

    @Override
    public MultiPoint readMultiPoint(Reader reader) throws GeometryIOException {
        return readMultiPoint(getJsonObject(reader), false);
    }

    @Override
    public MultiLineString readMultiLineString(Reader reader) throws GeometryIOException {
        return readMultiLineString(getJsonObject(reader), false);
    }

    @Override
    public MultiPolygon readMultiPolygon(Reader reader) throws GeometryIOException {
        return readMultiPolygon(getJsonObject(reader), false);
    }

    public Geometry read(JsonObject jsonObject)
            throws GeometryIOException {

        Object type = jsonObject.get(GeometryJSON.FIELD_NAME_TYPE);
        if (type == null) {
            throw new GeometryIOException("格式错误，未包含属性"
                    + GeometryJSON.FIELD_NAME_TYPE + "：" + jsonObject);
        }
        String typeString = type.toString();
        if (GeometryJSON.TYPE_POINT.equals(typeString)) {
            return readPoint(jsonObject, true);
        } else if (GeometryJSON.TYPE_LINESTRING.equals(typeString)) {
            return readLineString(jsonObject, true);
        } else if (GeometryJSON.TYPE_POLYGON.equals(typeString)) {
            return readPolygon(jsonObject, true);
        } else if (GeometryJSON.TYPE_MULTIPOINT.equals(typeString)) {
            return readMultiPoint(jsonObject, true);
        } else if (GeometryJSON.TYPE_MULTILINESTRING.equals(typeString)) {
            return readMultiLineString(jsonObject, true);
        } else if (GeometryJSON.TYPE_MULTIPOLYGON.equals(typeString)) {
            return readMultiPolygon(jsonObject, true);
        } else if (GeometryJSON.TYPE_GEOMETRYCOLLECTION.equals(typeString)) {
            return readGeometryCollection(jsonObject, true);
        }
        throw new GeometryIOException("格式错误，无法解析的属性"
                + GeometryJSON.FIELD_NAME_TYPE + "：" + jsonObject);
    }

    public Point readPoint(JsonObject jsonObject)
            throws GeometryIOException {
        return readPoint(jsonObject, false);
    }

    public LineString readLineString(JsonObject jsonObject)
            throws GeometryIOException {
        return readLineString(jsonObject, false);
    }

    public Polygon readPolygon(JsonObject jsonObject)
            throws GeometryIOException {
        return readPolygon(jsonObject, false);
    }

    public GeometryCollection readGeometryCollection(JsonObject jsonObject)
            throws GeometryIOException {
        return readGeometryCollection(jsonObject, false);
    }

    public MultiPoint readMultiPoint(JsonObject jsonObject)
            throws GeometryIOException {
        return readMultiPoint(jsonObject, false);
    }

    public MultiLineString readMultiLineString(JsonObject jsonObject) throws GeometryIOException {

        return readMultiLineString(jsonObject, false);
    }

    public MultiPolygon readMultiPolygon(JsonObject jsonObject) throws GeometryIOException {
        return readMultiPolygon(jsonObject, false);
    }

    private Point readPoint(JsonObject jsonObject, boolean checked) throws GeometryIOException {

        if (!checked) {
            checkField(jsonObject,
                    GeometryJSON.FIELD_NAME_TYPE,
                    GeometryJSON.TYPE_POINT);
        }

        Object coordinates = jsonObject.get(GeometryJSON.FIELD_NAME_COORDINATES);
        if (coordinates == null) {
            throw new GeometryIOException("格式错误，未包含属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "：" + jsonObject);
        }
        if (!(coordinates instanceof JsonArray)) {
            throw new GeometryIOException("格式错误，属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "必须为一个JSON数组："
                    + jsonObject);
        }
        double[] position = toPosition((JsonArray) coordinates);
        return new PointJSON(position).toPoint(factory);

    }

    private LineString readLineString(JsonObject jsonObject, boolean checked)
            throws GeometryIOException {

        if (!checked) {
            checkField(jsonObject,
                    GeometryJSON.FIELD_NAME_TYPE,
                    GeometryJSON.TYPE_LINESTRING);
        }

        Object oc = jsonObject.get(GeometryJSON.FIELD_NAME_COORDINATES);
        if (oc == null) {
            throw new GeometryIOException("格式错误，未包含属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "：" + jsonObject);
        }
        if (!(oc instanceof JsonArray)) {
            throw new GeometryIOException("格式错误，属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "必须为一个JSON数组："
                    + jsonObject);
        }
        JsonArray coordinates = (JsonArray) oc;
        // 线中至少包含两个点
        if (coordinates.size() < 2) {
            throw new GeometryIOException("格式错误，属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "中的元素太少："
                    + jsonObject);
        }

        double[][] positions = toPositions(coordinates);
        return new LineStringJSON(positions).toLineString(factory);
    }

    private Polygon readPolygon(JsonObject jsonObject, boolean checked)
            throws GeometryIOException {

        if (!checked) {
            checkField(jsonObject,
                    GeometryJSON.FIELD_NAME_TYPE,
                    GeometryJSON.TYPE_POLYGON);
        }

        Object oc = jsonObject.get(GeometryJSON.FIELD_NAME_COORDINATES);
        if (oc == null) {
            throw new GeometryIOException("格式错误，未包含属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "：" + jsonObject);
        }
        if (!(oc instanceof JsonArray)) {
            throw new GeometryIOException("格式错误，属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "必须为一个JSON数组："
                    + jsonObject);
        }
        JsonArray coordinates = (JsonArray) oc;
        // 多边形中至少包含一个环
        if (coordinates.size() < 1) {
            throw new GeometryIOException("格式错误，属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "中的元素太少："
                    + jsonObject);
        }

        double[][][] positions = new double[coordinates.size()][][];
        int index = 0;
        for (Object position : coordinates) {
            if (!(position instanceof JsonArray)) {
                throw new GeometryIOException("格式错误，属性"
                        + GeometryJSON.FIELD_NAME_COORDINATES + "中的元素必须为数组："
                        + jsonObject);
            }
            // 环中至少包含三个点
            if (((JsonArray) position).size() < 3) {
                throw new GeometryIOException("格式错误，属性"
                        + GeometryJSON.FIELD_NAME_COORDINATES + "中的元素太少："
                        + jsonObject);
            }
            positions[index++] = toPositions((JsonArray) position);
        }

        return new PolygonJSON(positions).toPolygon(factory);
    }

    private GeometryCollection readGeometryCollection(JsonObject jsonObject, boolean checked)
            throws GeometryIOException {

        if (!checked) {
            Object type = jsonObject.get(GeometryJSON.FIELD_NAME_TYPE);
            if (type == null) {
                throw new GeometryIOException("格式错误，未包含字段"
                        + GeometryJSON.FIELD_NAME_TYPE + "：" + jsonObject);
            }
            if (GeometryJSON.TYPE_MULTIPOINT.equals(type)) {
                return readMultiPoint(jsonObject, true);
            } else if (GeometryJSON.TYPE_MULTILINESTRING.equals(type)) {
                return readMultiLineString(jsonObject, true);
            } else if (GeometryJSON.TYPE_MULTIPOLYGON.equals(type)) {
                return readMultiPolygon(jsonObject, true);
            } else if (GeometryJSON.TYPE_GEOMETRYCOLLECTION.equals(type)) {

            } else {
                throw new GeometryIOException("格式错误，字段"
                        + GeometryJSON.FIELD_NAME_TYPE + "的值非法：" + jsonObject);
            }
        }

        Object geometries = jsonObject.get(GeometryJSON.FIELD_NAME_GEOMETRIES);
        if (!(geometries instanceof JsonArray)) {
            throw new GeometryIOException("格式错误，属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "中的元素必须为数组："
                    + jsonObject);
        }
        Geometry[] _geometries = new Geometry[((JsonArray) geometries).size()];
        int index = 0;
        for (Object geometry : (JsonArray) geometries) {
            if (!(geometry instanceof JsonObject)) {
                throw new GeometryIOException("格式错误，属性"
                        + GeometryJSON.FIELD_NAME_GEOMETRIES + "中的元素必须为JSON对象："
                        + jsonObject);
            }
            _geometries[index++] = read((JsonObject) geometry);
        }
        return factory.createGeometryCollection(_geometries);
    }

    private MultiPoint readMultiPoint(JsonObject jsonObject, boolean checked)
            throws GeometryIOException {

        if (!checked) {
            checkField(jsonObject,
                    GeometryJSON.FIELD_NAME_TYPE,
                    GeometryJSON.TYPE_MULTIPOINT);
        }

        Object oc = jsonObject.get(GeometryJSON.FIELD_NAME_COORDINATES);
        if (oc == null) {
            throw new GeometryIOException("格式错误，未包含属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "：" + jsonObject);
        }
        if (!(oc instanceof JsonArray)) {
            throw new GeometryIOException("格式错误，属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "必须为一个JSON数组："
                    + jsonObject);
        }

        double[][] positions = toPositions((JsonArray) oc);
        return new MultiPointJSON(positions).toMultiPoint(factory);
    }

    private MultiLineString readMultiLineString(JsonObject jsonObject, boolean checked) throws GeometryIOException {

        if (!checked) {
            checkField(jsonObject,
                    GeometryJSON.FIELD_NAME_TYPE,
                    GeometryJSON.TYPE_MULTILINESTRING);
        }

        Object oc = jsonObject.get(GeometryJSON.FIELD_NAME_COORDINATES);
        if (oc == null) {
            throw new GeometryIOException("格式错误，未包含属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "：" + jsonObject);
        }
        if (!(oc instanceof JsonArray)) {
            throw new GeometryIOException("格式错误，属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "必须为一个JSON数组："
                    + jsonObject);
        }
        JsonArray coordinates = (JsonArray) oc;
        double[][][] positions = new double[coordinates.size()][][];
        int index = 0;
        for (Object position : coordinates) {
            if (!(position instanceof JsonArray)) {
                throw new GeometryIOException("格式错误，属性"
                        + GeometryJSON.FIELD_NAME_COORDINATES + "中的元素必须为数组："
                        + jsonObject);
            }
            // 线中至少包含两个点
            if (((JsonArray) position).size() < 2) {
                throw new GeometryIOException("格式错误，属性"
                        + GeometryJSON.FIELD_NAME_COORDINATES + "中的元素太少："
                        + jsonObject);
            }
            positions[index++] = toPositions((JsonArray) position);
        }

        return new MultiLineStringJSON(positions).toMultiLineString(factory);
    }

    private MultiPolygon readMultiPolygon(JsonObject jsonObject, boolean checked) throws GeometryIOException {

        if (!checked) {
            checkField(jsonObject,
                    GeometryJSON.FIELD_NAME_TYPE,
                    GeometryJSON.TYPE_MULTIPOLYGON);
        }

        Object oc = jsonObject.get(GeometryJSON.FIELD_NAME_COORDINATES);
        if (oc == null) {
            throw new GeometryIOException("格式错误，未包含属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "：" + jsonObject);
        }
        if (!(oc instanceof JsonArray)) {
            throw new GeometryIOException("格式错误，属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "必须为一个JSON数组："
                    + jsonObject);
        }
        JsonArray coordinates = (JsonArray) oc;
        double[][][][] positions = new double[coordinates.size()][][][];
        int i = 0;
        for (Object _positions : coordinates) {
            if (!(_positions instanceof JsonArray)) {
                throw new GeometryIOException("格式错误，属性"
                        + GeometryJSON.FIELD_NAME_COORDINATES + "中的元素必须为数组："
                        + jsonObject);
            }
            positions[i] = new double[((JsonArray) _positions).size()][][];
            int j = 0;
            for (Object position : (JsonArray) _positions) {
                // 环中至少包含三个点
                if (((JsonArray) position).size() < 3) {
                    throw new GeometryIOException("格式错误，属性"
                            + GeometryJSON.FIELD_NAME_COORDINATES + "中的元素太少："
                            + jsonObject);
                }
                positions[i][j++] = toPositions((JsonArray) position);
            }
            ++i;
        }

        return new MultiPolygonJSON(positions).toMultiPolygon(factory);
    }

    private JsonObject getJsonObject(String json) throws GeometryIOException {

        try {
            Object jsonObject = parser.parse(json);
            if (jsonObject instanceof JsonArray) {
                return (JsonObject) jsonObject;
            }
            throw new GeometryIOException("对象必须是一个json对象");

        } catch (JsonParseException e) {
            throw new GeometryIOException(e);
        }
    }

    private JsonObject getJsonObject(Reader reader) throws GeometryIOException {
        try {
            Object jsonObject = parser.parse(reader);
            if (jsonObject instanceof JsonObject) {
                return (JsonObject) jsonObject;
            }
            throw new GeometryIOException("对象必须是一个java.io.Reader对象");
        } catch (JsonParseException e) {
            throw new GeometryIOException(e);
        }
    }

    private void checkField(JsonObject jsonObject, String fieldName, Object fieldValue)
            throws GeometryIOException {
        Object value = jsonObject.get(fieldName);
        if (value == null) {
            throw new GeometryIOException("格式错误，未包含字段" + fieldName + "：" + jsonObject);
        }
        if (!fieldValue.equals(value)) {
            throw new GeometryIOException("格式错误，字段"
                    + fieldName + "的值非法（应该为" + fieldValue + "）：" + jsonObject);
        }
    }

    private double[] toPosition(JsonArray jsonArray)
            throws GeometryIOException {

        if (jsonArray.size() < 2) {
            throw new GeometryIOException("格式错误，属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "中的元素太少："
                    + jsonArray);
        }

        double[] values = new double[jsonArray.size()];
        try {
            int index = 0;
            for (Object obj : jsonArray) {
                values[index++] = Double.valueOf(obj.toString());
            }
        } catch (NumberFormatException ex) {
            throw new GeometryIOException("格式错误，属性"
                    + GeometryJSON.FIELD_NAME_COORDINATES + "中的元素必须为数字："
                    + jsonArray);
        }
        return values;
    }

    private double[][] toPositions(JsonArray jsonArray)
            throws GeometryIOException {

        double[][] positions = new double[jsonArray.size()][];
        int index = 0;
        for (Object position : jsonArray) {
            if (!(position instanceof JsonArray)) {
                throw new GeometryIOException("格式错误，属性"
                        + GeometryJSON.FIELD_NAME_COORDINATES + "中的元素必须为数组："
                        + jsonArray);
            }
            positions[index++] = toPosition((JsonArray) position);
        }
        return positions;
    }

}
