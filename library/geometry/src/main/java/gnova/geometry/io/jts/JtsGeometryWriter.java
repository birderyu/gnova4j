package gnova.geometry.io.jts;

import gnova.geometry.io.GeometryWriter;
import gnova.geometry.model.Geometry;
import gnova.geometry.model.GeometryCollection;
import gnova.geometry.model.LineString;
import gnova.geometry.model.LinearRing;
import gnova.geometry.model.MultiLineString;
import gnova.geometry.model.MultiPoint;
import gnova.geometry.model.MultiPolygon;
import gnova.geometry.model.Point;
import gnova.geometry.model.Polygon;
import gnova.geometry.model.impl.jts.GeometryFactoryAdaptor;

public class JtsGeometryWriter
        implements GeometryWriter<org.locationtech.jts.geom.Geometry> {

    private final GeometryFactoryAdaptor jtsGeometryFactory;

    public JtsGeometryWriter() {
        this(new org.locationtech.jts.geom.GeometryFactory());
    }

    public JtsGeometryWriter(org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory) {
        this.jtsGeometryFactory = new GeometryFactoryAdaptor(jtsGeometryFactory);
    }

    @Override
    public org.locationtech.jts.geom.Geometry write(Geometry geometry) {
        return jtsGeometryFactory.toJtsGeometry(geometry);
    }

    @Override
    public org.locationtech.jts.geom.Point writePoint(Point point) {
        return jtsGeometryFactory.toJtsPoint(point);
    }

    @Override
    public org.locationtech.jts.geom.LineString writeLineString(LineString lineString) {
        return jtsGeometryFactory.toJtsLineString(lineString);
    }

    @Override
    public org.locationtech.jts.geom.LinearRing writeLinearRing(LinearRing linearRing) {
        return jtsGeometryFactory.toJtsLinearRing(linearRing);
    }

    @Override
    public org.locationtech.jts.geom.Polygon writePolygon(Polygon polygon) {
        return jtsGeometryFactory.toJtsPolygon(polygon);
    }

    @Override
    public org.locationtech.jts.geom.GeometryCollection writeGeometryCollection(GeometryCollection geometries) {
        return jtsGeometryFactory.toJtsGeometryCollection(geometries);
    }

    @Override
    public org.locationtech.jts.geom.MultiPoint writeMultiPoint(MultiPoint multiPoint) {
        return jtsGeometryFactory.toJtsMultiPoint(multiPoint);
    }

    @Override
    public org.locationtech.jts.geom.MultiLineString writeMultiLineString(MultiLineString multiLineString) {
        return jtsGeometryFactory.toJtsMultiLineString(multiLineString);
    }

    @Override
    public org.locationtech.jts.geom.MultiPolygon writeMultiPolygon(MultiPolygon multiPolygon) {
        return jtsGeometryFactory.toJtsMultiPolygon(multiPolygon);
    }
}
