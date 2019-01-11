package gnova.geometry.io.jts;

import gnova.core.annotation.NotNull;
import gnova.geometry.io.GeometryReader;
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

public class JtsGeometryReader
        implements GeometryReader<org.locationtech.jts.geom.Geometry> {

    @Override
    public Geometry read(org.locationtech.jts.geom.Geometry jtsGeometry) {
        return GeometryFactoryAdaptor.fromJtsGeometry(jtsGeometry);
    }

    @NotNull
    public Point readPoint(@NotNull org.locationtech.jts.geom.Point jtsPoint) {
        return GeometryFactoryAdaptor.fromJtsPoint(jtsPoint);
    }

    @NotNull
    public LineString readPoint(org.locationtech.jts.geom.LineString jtsLineString) {
        return GeometryFactoryAdaptor.fromJtsLineString(jtsLineString);
    }

    @NotNull
    public LinearRing readPoint(org.locationtech.jts.geom.LinearRing jtsLinearRing) {
        return GeometryFactoryAdaptor.fromJtsLinearRing(jtsLinearRing);
    }

    @NotNull
    public Polygon readPolygon(org.locationtech.jts.geom.Polygon jtsPolygon) {
        return GeometryFactoryAdaptor.fromJtsPolygon(jtsPolygon);
    }

    @NotNull
    public GeometryCollection readGeometryCollection(org.locationtech.jts.geom.GeometryCollection jtsGeometryCollection) {
        return GeometryFactoryAdaptor.fromJtsGeometryCollection(jtsGeometryCollection);
    }

    @NotNull
    public MultiPoint readMultiPoint(org.locationtech.jts.geom.MultiPoint jtsMultiPoint) {
        return GeometryFactoryAdaptor.fromJtsMultiPoint(jtsMultiPoint);
    }

    @NotNull
    public MultiLineString readMultiLineString(org.locationtech.jts.geom.MultiLineString jtsMultiLineString) {
        return GeometryFactoryAdaptor.fromJtsMultiLineString(jtsMultiLineString);
    }

    @NotNull
    public MultiPolygon readMultiPolygon(org.locationtech.jts.geom.MultiPolygon jtsMultiPolygon) {
        return GeometryFactoryAdaptor.fromJtsMultiPolygon(jtsMultiPolygon);
    }

}
