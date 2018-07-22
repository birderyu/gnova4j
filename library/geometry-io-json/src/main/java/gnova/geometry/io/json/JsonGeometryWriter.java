package gnova.geometry.io.json;

import gnova.core.annotation.NotNull;
import gnova.core.json.JsonArrayBuilder;
import gnova.core.json.JsonObject;
import gnova.core.json.JsonObjectBuilder;
import gnova.geometry.io.GeometryIOException;
import gnova.geometry.io.TextGeometryWriter;
import gnova.geometry.json.GeometryJSON;
import gnova.geometry.model.*;
import gnova.geometry.model.Geometry;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by Birderyu on 2017/6/25.
 */
public class JsonGeometryWriter
        implements TextGeometryWriter {

    public String write(Geometry geometry) {
        return GeometryJSON.fromGeometry(geometry).toString();
    }

    public String writePoint(Point point) {
        return GeometryJSON.fromPoint(point).toString();
    }

    public String writeLineString(LineString lineString) {
        return GeometryJSON.fromLineString(lineString).toString();
    }

    public String writeLinearRing(LinearRing linearRing) {
        return writeLineString(linearRing);
    }

    public String writePolygon(Polygon polygon) {
        return GeometryJSON.fromPolygon(polygon).toString();
    }

    public String writeGeometryCollection(GeometryCollection geometries) {
        switch (geometries.getType()) {
            case MultiPoint:
                return writeMultiPoint((MultiPoint) geometries);
            case MultiLineString:
                return writeMultiLineString((MultiLineString) geometries);
            case MultiPolygon:
                return writeMultiPolygon((MultiPolygon) geometries);
        }
        return GeometryJSON.fromGeometryCollection(geometries).toString();
    }

    public String writeMultiPoint(MultiPoint multiPoint) {
        return GeometryJSON.fromMultiPoint(multiPoint).toString();
    }

    public String writeMultiLineString(MultiLineString multiLineString) {
        return GeometryJSON.fromMultiLineString(multiLineString).toString();
    }

    public String writeMultiPolygon(MultiPolygon multiPolygon) {
        return GeometryJSON.fromMultiPolygon(multiPolygon).toString();
    }

    public void write(Geometry geometry, Writer writer)
            throws GeometryIOException {
        try {
            writer.write(write(geometry));
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    public void writePoint(Point point, Writer writer)
            throws GeometryIOException {
        try {
            writer.write(writePoint(point));
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    public void writeLineString(LineString lineString, Writer writer)
            throws GeometryIOException {
        try {
            writer.write(writeLineString(lineString));
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    public void writeLinearRing(LinearRing linearRing, Writer writer)
            throws GeometryIOException {
        try {
            writer.write(writeLinearRing(linearRing));
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    public void writePolygon(Polygon polygon, Writer writer)
            throws GeometryIOException {
        try {
            writer.write(writePolygon(polygon));
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    public void writeGeometryCollection(GeometryCollection geometries, Writer writer)
            throws GeometryIOException {
        try {
            writer.write(writeGeometryCollection(geometries));
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    public void writeMultiPoint(MultiPoint multiPoint, Writer writer)
            throws GeometryIOException {
        try {
            writer.write(writeGeometryCollection(multiPoint));
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    public void writeMultiLineString(MultiLineString multiLineString, Writer writer)
            throws GeometryIOException {
        try {
            writer.write(writeMultiLineString(multiLineString));
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    public void writeMultiPolygon(MultiPolygon multiPolygon, Writer writer)
            throws GeometryIOException {
        try {
            writer.write(writeMultiPolygon(multiPolygon));
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    public JsonObject write(@NotNull JsonObjectBuilder job, @NotNull JsonArrayBuilder jab, Geometry geometry) {
        return GeometryJSON.fromGeometry(geometry).toJsonObject(job, jab);
    }

    public JsonObject writePoint(@NotNull JsonObjectBuilder job, @NotNull JsonArrayBuilder jab, Point point) {
        return GeometryJSON.fromPoint(point).toJsonObject(job, jab);
    }

    public JsonObject writeLineString(@NotNull JsonObjectBuilder job, @NotNull JsonArrayBuilder jab, LineString lineString) {
        return GeometryJSON.fromLineString(lineString).toJsonObject(job, jab);
    }

    public JsonObject writeLinearRing(@NotNull JsonObjectBuilder job, @NotNull JsonArrayBuilder jab, LinearRing linearRing) {
        return writeLineString(job, jab, linearRing);
    }

    public JsonObject writePolygon(@NotNull JsonObjectBuilder job, @NotNull JsonArrayBuilder jab, Polygon polygon) {
        return GeometryJSON.fromPolygon(polygon).toJsonObject(job, jab);
    }

    public JsonObject writeGeometryCollectionJSON(@NotNull JsonObjectBuilder job, @NotNull JsonArrayBuilder jab, GeometryCollection geometries) {
        switch (geometries.getType()) {
            case MultiPoint:
                return writeMultiPoint(job, jab, (MultiPoint) geometries);
            case MultiLineString:
                return writeMultiLineString(job, jab, (MultiLineString) geometries);
            case MultiPolygon:
                return writeMultiPolygon(job, jab, (MultiPolygon) geometries);
        }
        return GeometryJSON.fromGeometryCollection(geometries).toJsonObject(job, jab);
    }

    public JsonObject writeMultiPoint(@NotNull JsonObjectBuilder job, @NotNull JsonArrayBuilder jab, MultiPoint multiPoint) {
        return GeometryJSON.fromMultiPoint(multiPoint).toJsonObject(job, jab);
    }

    public JsonObject writeMultiLineString(@NotNull JsonObjectBuilder job, @NotNull JsonArrayBuilder jab, MultiLineString multiLineString) {
        return GeometryJSON.fromMultiLineString(multiLineString).toJsonObject(job, jab);
    }

    public JsonObject writeMultiPolygon(@NotNull JsonObjectBuilder job, @NotNull JsonArrayBuilder jab, MultiPolygon multiPolygon) {
        return GeometryJSON.fromMultiPolygon(multiPolygon).toJsonObject(job, jab);
    }

}
