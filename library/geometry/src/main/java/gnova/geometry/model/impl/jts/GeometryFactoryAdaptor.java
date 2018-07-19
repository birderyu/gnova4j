package gnova.geometry.model.impl.jts;
import gnova.geometry.model.CoordinateSequenceFactory;
import gnova.geometry.model.*;
import gnova.geometry.model.GeometryFactory;

import java.util.Arrays;

/**
 * Created by Birderyu on 2017/6/21.
 */
public final class GeometryFactoryAdaptor
        implements GeometryFactory {

    enum JtsGeometryType {

        Point(com.vividsolutions.jts.geom.Point.class, GeometryType.Point.getType()),
        LineString(com.vividsolutions.jts.geom.LineString.class, GeometryType.LineString.getType()),
        LinearRing(com.vividsolutions.jts.geom.LinearRing.class, GeometryType.LinearRing.getType()),
        Polygon(com.vividsolutions.jts.geom.Polygon.class, GeometryType.Polygon.getType()),
        MultiPoint(com.vividsolutions.jts.geom.MultiPoint.class, GeometryType.MultiPoint.getType()),
        MultiLineString(com.vividsolutions.jts.geom.MultiLineString.class, GeometryType.MultiLineString.getType()),
        MultiPolygon(com.vividsolutions.jts.geom.MultiPolygon.class, GeometryType.MultiPolygon.getType()),
        GeometryCollection(com.vividsolutions.jts.geom.GeometryCollection.class, GeometryType.GeometryCollection.getType());

        private final Class<? extends com.vividsolutions.jts.geom.Geometry> binding;
        private final int geometryType;
        private final String name;

        private JtsGeometryType(Class<? extends com.vividsolutions.jts.geom.Geometry> type,
                                int geometryType) {
            this.binding = type;
            this.geometryType = geometryType;
            this.name = type.getSimpleName();
        }

        public Class<? extends com.vividsolutions.jts.geom.Geometry> getBinding() {
            return binding;
        }

        public GeometryType getGeometryType() {
            return GeometryType.from(geometryType);
        }

        public String getGeometryName() {
            return getGeometryType().getName();
        }

        @Override
        public String toString() {
            return getGeometryType().toString();
        }

        static public JtsGeometryType from(com.vividsolutions.jts.geom.Geometry geom) {
            if (geom != null) {
                return fromBinding(geom.getClass());
            }

            return null;
        }

        static public JtsGeometryType fromBinding(Class<? extends com.vividsolutions.jts.geom.Geometry> geomClass) {
            for (JtsGeometryType gt : JtsGeometryType.values()) {
                if (gt.binding == geomClass) {
                    return gt;
                }
            }

            JtsGeometryType match = null;

            for (JtsGeometryType gt : JtsGeometryType.values()) {
                if (gt == JtsGeometryType.GeometryCollection) {
                    continue;
                }

                if (gt.binding.isAssignableFrom(geomClass)) {
                    if (match == null) {
                        match = gt;
                    } else {
                        return null;
                    }
                }
            }

            if (match == null) {
                if (GeometryCollection.class.isAssignableFrom(geomClass)) {
                    return JtsGeometryType.GeometryCollection;
                }
            }

            return match;
        }

        static public JtsGeometryType fromName(String name) {
            for (JtsGeometryType gt : JtsGeometryType.values()) {
                if (gt.getGeometryName().equalsIgnoreCase(name)) {
                    return gt;
                }
            }
            return null;
        }
    }

    private com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory;
    private CoordinateSequenceFactory coordinateSequenceFactory;

    public GeometryFactoryAdaptor() {
        jtsGeometryFactory = new com.vividsolutions.jts.geom.GeometryFactory();
        coordinateSequenceFactory = new CoordinateSequenceFactoryAdaptor(jtsGeometryFactory.getCoordinateSequenceFactory());
    }

    public GeometryFactoryAdaptor(int srid) {
        jtsGeometryFactory = new com.vividsolutions.jts.geom.GeometryFactory(
                new com.vividsolutions.jts.geom.PrecisionModel(), srid);
        coordinateSequenceFactory = new CoordinateSequenceFactoryAdaptor(jtsGeometryFactory.getCoordinateSequenceFactory());
    }

    public GeometryFactoryAdaptor(com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory) {
        this.jtsGeometryFactory = jtsGeometryFactory;
    }

    @Override
    public int getSRID() {
        return jtsGeometryFactory.getSRID();
    }

    @Override
    public CoordinateSequenceFactory getCoordinateSequenceFactory() {
        return coordinateSequenceFactory;
    }

    @Override
    public Geometry createGeometry(BoundingBox bbox) {
        return fromJtsGeometry(jtsGeometryFactory.toGeometry(
                GeometryFactoryAdaptor.toJtsEnvelope(bbox)));
    }

    @Override
    public Geometry createGeometry(Geometry geometry) {
        return GeometryFactoryAdaptor.fromJtsGeometry(
                jtsGeometryFactory.createGeometry(toJtsGeometry(geometry)));
    }

    @Override
    public Point createPoint(Coordinate coordinate) {
        return GeometryFactoryAdaptor.fromJtsPoint(
                jtsGeometryFactory.createPoint(
                        CoordinateSequenceFactoryAdaptor.toJtsCoordinate(coordinate)));
    }

    @Override
    public LineString createLineString(Coordinate[] coordinates) {

        if (coordinates == null || coordinates.length < 2) {
            throw new IllegalArgumentException("Invalid number of points in LineString (must not less than 2): "
                    + Arrays.toString(coordinates));
        }
        return GeometryFactoryAdaptor.fromJtsLineString(
                jtsGeometryFactory.createLineString(
                        CoordinateSequenceFactoryAdaptor.toJtsCoordinateArray(coordinates)));
    }

    @Override
    public LineString createLineString(CoordinateSequence coordinates) {

        if (coordinates == null || coordinates.size() < 2) {
            throw new IllegalArgumentException("Invalid number of points in LineString (must not less than 2): "
                    + coordinates);
        }
        return GeometryFactoryAdaptor.fromJtsLineString(
                jtsGeometryFactory.createLineString(
                        CoordinateSequenceFactoryAdaptor.toJtsCoordinateSequence(coordinates)));
    }

    @Override
    public LinearRing createLinearRing(Coordinate[] coordinates) {

        if (coordinates == null || coordinates.length < 4) {
            throw new IllegalArgumentException("Invalid number of points in LinearRing (must not less than 4): "
                    + coordinates);
        }
        return GeometryFactoryAdaptor.fromJtsLinearRing(
                jtsGeometryFactory.createLinearRing(
                        CoordinateSequenceFactoryAdaptor.toJtsCoordinateArray(coordinates)));
    }

    @Override
    public LinearRing createLinearRing(CoordinateSequence coordinates) {

        if (coordinates == null || coordinates.size() < 4) {
            throw new IllegalArgumentException("Invalid number of points in LinearRing (must not less than 4): "
                    + coordinates);
        }
        return GeometryFactoryAdaptor.fromJtsLinearRing(
                jtsGeometryFactory.createLinearRing(
                        CoordinateSequenceFactoryAdaptor.toJtsCoordinateSequence(coordinates)));
    }

    @Override
    public Polygon createPolygon(LinearRing shell, LinearRing[] holes) {
        return GeometryFactoryAdaptor.fromJtsPolygon(
                jtsGeometryFactory.createPolygon(
                        toJtsLinearRing(shell),
                        toJtsLinearRingArray(holes)));
    }

    @Override
    public GeometryCollection createGeometryCollection(Geometry[] geometries) {
        return GeometryFactoryAdaptor.fromJtsGeometryCollection(
                jtsGeometryFactory.createGeometryCollection(
                        toJtsGeometryArray(geometries)));
    }

    @Override
    public MultiPoint createMultiPoint(Point[] points) {
        return GeometryFactoryAdaptor.fromJtsMultiPoint(
                jtsGeometryFactory.createMultiPoint(
                        toJtsPointArray(points)));
    }

    @Override
    public MultiPoint createMultiPoint(Coordinate[] coordinates) {
        return GeometryFactoryAdaptor.fromJtsMultiPoint(
                jtsGeometryFactory.createMultiPoint(
                        CoordinateSequenceFactoryAdaptor.toJtsCoordinateArray(coordinates)));
    }

    @Override
    public MultiPoint createMultiPoint(CoordinateSequence coordinates) {
        return GeometryFactoryAdaptor.fromJtsMultiPoint(
                jtsGeometryFactory.createMultiPoint(
                        CoordinateSequenceFactoryAdaptor.toJtsCoordinateSequence(coordinates)));
    }

    @Override
    public MultiLineString createMultiLineString(LineString[] lineStrings) {
        return GeometryFactoryAdaptor.fromJtsMultiLineString(
                jtsGeometryFactory.createMultiLineString(
                        toJtsLineStringArray(lineStrings)));
    }

    @Override
    public MultiPolygon createMultiPolygon(Polygon[] polygons) {
        return GeometryFactoryAdaptor.fromJtsMultiPolygon(
                jtsGeometryFactory.createMultiPolygon(
                        toJtsPolygonArray(polygons)));
    }

    public com.vividsolutions.jts.geom.Geometry toJtsGeometry(Geometry geometry) {
        return GeometryFactoryAdaptor.toJtsGeometry(jtsGeometryFactory, geometry);
    }

    public com.vividsolutions.jts.geom.Geometry[] toJtsGeometryArray(Geometry[] geometries) {
        return GeometryFactoryAdaptor.toJtsGeometryArray(jtsGeometryFactory, geometries);
    }

    public com.vividsolutions.jts.geom.Point toJtsPoint(Point point) {
        return GeometryFactoryAdaptor.toJtsPoint(jtsGeometryFactory, point);
    }

    public com.vividsolutions.jts.geom.Point[] toJtsPointArray(Point[] points) {
        return GeometryFactoryAdaptor.toJtsPointArray(jtsGeometryFactory, points);
    }

    public com.vividsolutions.jts.geom.LineString toJtsLineString(LineString lineString) {
        return GeometryFactoryAdaptor.toJtsLineString(jtsGeometryFactory, lineString);
    }

    public com.vividsolutions.jts.geom.LineString[] toJtsLineStringArray(LineString[] lineStrings) {
        return GeometryFactoryAdaptor.toJtsLineStringArray(jtsGeometryFactory, lineStrings);
    }

    public com.vividsolutions.jts.geom.LinearRing toJtsLinearRing(LinearRing linearRing) {
        return GeometryFactoryAdaptor.toJtsLinearRing(jtsGeometryFactory, linearRing);
    }

    public com.vividsolutions.jts.geom.LinearRing[] toJtsLinearRingArray(LinearRing[] linearRings) {
        return GeometryFactoryAdaptor.toJtsLinearRingArray(jtsGeometryFactory, linearRings);
    }

    public com.vividsolutions.jts.geom.Polygon toJtsPolygon(Polygon polygon) {
        return GeometryFactoryAdaptor.toJtsPolygon(jtsGeometryFactory, polygon);
    }

    public com.vividsolutions.jts.geom.Polygon[] toJtsPolygonArray(Polygon[] polygons) {
        return GeometryFactoryAdaptor.toJtsPolygonArray(jtsGeometryFactory, polygons);
    }

    public com.vividsolutions.jts.geom.GeometryCollection toJtsGeometryCollection(GeometryCollection geometryCollection) {
        return GeometryFactoryAdaptor.toJtsGeometryCollection(jtsGeometryFactory, geometryCollection);
    }

    public com.vividsolutions.jts.geom.MultiPoint toJtsMultiPoint(MultiPoint multiPoint) {
        return GeometryFactoryAdaptor.toJtsMultiPoint(jtsGeometryFactory, multiPoint);
    }

    public com.vividsolutions.jts.geom.MultiLineString toJtsMultiLineString(MultiLineString multiLineString) {
        return GeometryFactoryAdaptor.toJtsMultiLineString(jtsGeometryFactory, multiLineString);
    }

    public com.vividsolutions.jts.geom.MultiPolygon toJtsMultiPolygon(MultiPolygon multiPolygon) {
        return GeometryFactoryAdaptor.toJtsMultiPolygon(jtsGeometryFactory, multiPolygon);
    }

    static public Geometry fromJtsGeometry(com.vividsolutions.jts.geom.Geometry jtsGeometry) {

        switch (JtsGeometryType.from(jtsGeometry)) {
            case Point:
                return fromJtsPoint((com.vividsolutions.jts.geom.Point) jtsGeometry);
            case LinearRing:
                return fromJtsLinearRing((com.vividsolutions.jts.geom.LinearRing) jtsGeometry);
            case LineString:
                return fromJtsLineString((com.vividsolutions.jts.geom.LineString) jtsGeometry);
            case Polygon:
                return fromJtsPolygon((com.vividsolutions.jts.geom.Polygon) jtsGeometry);
            case MultiPoint:
                return fromJtsMultiPoint((com.vividsolutions.jts.geom.MultiPoint) jtsGeometry);
            case MultiLineString:
                return fromJtsMultiLineString((com.vividsolutions.jts.geom.MultiLineString) jtsGeometry);
            case MultiPolygon:
                return fromJtsMultiPolygon((com.vividsolutions.jts.geom.MultiPolygon) jtsGeometry);
            case GeometryCollection:
                return fromJtsGeometryCollection((com.vividsolutions.jts.geom.GeometryCollection) jtsGeometry);
        }
        return null;
    }

    static public Point fromJtsPoint(com.vividsolutions.jts.geom.Point jtsPoint) {
        return new PointAdaptor(jtsPoint);
    }

    static public LineString fromJtsLineString(com.vividsolutions.jts.geom.LineString jtsLineString) {

        switch (JtsGeometryType.from(jtsLineString)) {
            case LineString:
                return new LineStringAdaptor(jtsLineString);
            case LinearRing:
                return fromJtsLinearRing((com.vividsolutions.jts.geom.LinearRing) jtsLineString);
        }

        return null;
    }

    static public LinearRing fromJtsLinearRing(com.vividsolutions.jts.geom.LinearRing jtsLinearRing) {
        return new LinearRingAdaptor(jtsLinearRing);
    }

    static public Polygon fromJtsPolygon(com.vividsolutions.jts.geom.Polygon jtsPolygon) {
        return new PolygonAdaptor(jtsPolygon);
    }

    static public GeometryCollection fromJtsGeometryCollection(com.vividsolutions.jts.geom.GeometryCollection jtsGeometryCollection) {

        switch (JtsGeometryType.from(jtsGeometryCollection)) {
            case GeometryCollection:
                return new GeometryCollectionAdaptor(jtsGeometryCollection);
            case MultiPoint:
                return GeometryFactoryAdaptor.fromJtsMultiPoint((com.vividsolutions.jts.geom.MultiPoint) jtsGeometryCollection);
            case MultiLineString:
                return GeometryFactoryAdaptor.fromJtsMultiLineString((com.vividsolutions.jts.geom.MultiLineString) jtsGeometryCollection);
            case MultiPolygon:
                return GeometryFactoryAdaptor.fromJtsMultiPolygon((com.vividsolutions.jts.geom.MultiPolygon) jtsGeometryCollection);
        }

        return null;
    }

    static public MultiPoint fromJtsMultiPoint(com.vividsolutions.jts.geom.MultiPoint jtsMultiPoint) {
        return new MultiPointAdaptor(jtsMultiPoint);
    }

    static public MultiLineString fromJtsMultiLineString(com.vividsolutions.jts.geom.MultiLineString jtsMultiLineString) {
        return new MultiLineStringAdaptor(jtsMultiLineString);
    }

    static public MultiPolygon fromJtsMultiPolygon(com.vividsolutions.jts.geom.MultiPolygon jtsMultiPolygon) {
        return new MultiPolygonAdaptor(jtsMultiPolygon);
    }

    static public com.vividsolutions.jts.geom.Envelope toJtsEnvelope(BoundingBox bbox) {
        return new com.vividsolutions.jts.geom.Envelope(bbox.getMinX(), bbox.getMaxX(),
                bbox.getMinY(), bbox.getMaxY());
    }

    static public com.vividsolutions.jts.geom.Geometry toJtsGeometry(
            com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory,
            Geometry geometry) {

        if (geometry instanceof AbstractGeometryAdaptor) {
            return ((AbstractGeometryAdaptor) geometry).getJts();
        }
        switch (geometry.getType()) {
            case Point:
                return GeometryFactoryAdaptor.toJtsPoint(jtsGeometryFactory, (Point) geometry);
            case LineString:
                return GeometryFactoryAdaptor.toJtsLineString(jtsGeometryFactory, (LineString) geometry);
            case LinearRing:
                return GeometryFactoryAdaptor.toJtsLinearRing(jtsGeometryFactory, (LinearRing) geometry);
            case Polygon:
                return GeometryFactoryAdaptor.toJtsPolygon(jtsGeometryFactory, (Polygon) geometry);
            case MultiPoint:
                return GeometryFactoryAdaptor.toJtsMultiPoint(jtsGeometryFactory, (MultiPoint) geometry);
            case MultiLineString:
                return GeometryFactoryAdaptor.toJtsMultiLineString(jtsGeometryFactory, (MultiLineString) geometry);
            case MultiPolygon:
                return GeometryFactoryAdaptor.toJtsMultiPolygon(jtsGeometryFactory, (MultiPolygon) geometry);
            case GeometryCollection:
                return GeometryFactoryAdaptor.toJtsGeometryCollection(jtsGeometryFactory, (GeometryCollection) geometry);
        }
        return null;
    }

    static public com.vividsolutions.jts.geom.Geometry[] toJtsGeometryArray(
            com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory,
            Geometry[] geometries) {

        com.vividsolutions.jts.geom.Geometry[] jtsGeometries =
                new com.vividsolutions.jts.geom.Geometry[geometries.length];
        for (int i = 0; i < geometries.length; i++) {
            jtsGeometries[i] = GeometryFactoryAdaptor.toJtsGeometry(jtsGeometryFactory, geometries[i]);
        }
        return jtsGeometries;

    }

    static public com.vividsolutions.jts.geom.Point toJtsPoint(
            com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory,
            Point point) {

        if (point instanceof PointAdaptor) {
            return ((PointAdaptor) point).getJts();
        }
        return jtsGeometryFactory.createPoint(
                CoordinateSequenceFactoryAdaptor.toJtsCoordinate(point.getCoordinate()));

    }

    static public com.vividsolutions.jts.geom.Point[] toJtsPointArray(
            com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory,
            Point[] points) {

        com.vividsolutions.jts.geom.Point[] jtsPoints =
                new com.vividsolutions.jts.geom.Point[points.length];
        for (int i = 0; i < points.length; i++) {
            jtsPoints[i] = GeometryFactoryAdaptor.toJtsPoint(jtsGeometryFactory, points[i]);
        }
        return jtsPoints;

    }

    static public com.vividsolutions.jts.geom.LineString toJtsLineString(
            com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory,
            LineString lineString) {

        switch (lineString.getType()) {
            case LineString:
            {
                if (lineString instanceof LineStringAdaptor) {
                    return ((LineStringAdaptor) lineString).getJts();
                }
                return jtsGeometryFactory.createLineString(
                        CoordinateSequenceFactoryAdaptor.toJtsCoordinateSequence(
                                lineString.getCoordinateSequence()));
            }
            case LinearRing:
                return GeometryFactoryAdaptor.toJtsLinearRing(jtsGeometryFactory, (LinearRing) lineString);
        }

        return null;

    }

    static public com.vividsolutions.jts.geom.LineString[] toJtsLineStringArray(
            com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory,
            LineString[] lineStrings) {

        com.vividsolutions.jts.geom.LineString[] jtsLineStrings =
                new com.vividsolutions.jts.geom.LineString[lineStrings.length];
        for (int i = 0; i < lineStrings.length; i++) {
            jtsLineStrings[i] = GeometryFactoryAdaptor.toJtsLineString(jtsGeometryFactory, lineStrings[i]);
        }
        return jtsLineStrings;

    }

    static public com.vividsolutions.jts.geom.LinearRing toJtsLinearRing(
            com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory,
            LinearRing linearRing) {
    	if (linearRing == null) {
    		return null;
    	}
        if (linearRing instanceof LinearRingAdaptor) {
            return ((LinearRingAdaptor) linearRing).getJts();
        }
        return jtsGeometryFactory.createLinearRing(
                CoordinateSequenceFactoryAdaptor.toJtsCoordinateSequence(
                        linearRing.getCoordinateSequence()));

    }

    static public com.vividsolutions.jts.geom.LinearRing[] toJtsLinearRingArray(
            com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory,
            LinearRing[] linearRings) {

    	if (linearRings == null) {
    		return null;
    	}
        com.vividsolutions.jts.geom.LinearRing[] jtsLinearRings =
                new com.vividsolutions.jts.geom.LinearRing[linearRings.length];
        for (int i = 0; i < linearRings.length; i++) {
            jtsLinearRings[i] = GeometryFactoryAdaptor.toJtsLinearRing(jtsGeometryFactory, linearRings[i]);
        }
        return jtsLinearRings;

    }

    static public com.vividsolutions.jts.geom.Polygon toJtsPolygon(
            com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory,
            Polygon polygon) {

        if (polygon instanceof PolygonAdaptor) {
            return ((PolygonAdaptor) polygon).getJts();
        }

        com.vividsolutions.jts.geom.LinearRing[] holes = null;
        if (polygon.getInteriorRingSize() > 0) {
            holes = new com.vividsolutions.jts.geom.LinearRing[polygon.getInteriorRingSize()];
            for (int i = 0; i < holes.length; i++) {
                holes[i] = GeometryFactoryAdaptor.toJtsLinearRing(
                        jtsGeometryFactory,
                        polygon.getInteriorRingAt(i));
            }
        }

        return jtsGeometryFactory.createPolygon(
                GeometryFactoryAdaptor.toJtsLinearRing(
                        jtsGeometryFactory, polygon.getExteriorRing()),
                holes);
    }

    static public com.vividsolutions.jts.geom.Polygon[] toJtsPolygonArray(
            com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory,
            Polygon[] polygons) {

        com.vividsolutions.jts.geom.Polygon[] jtsPolygons =
                new com.vividsolutions.jts.geom.Polygon[polygons.length];
        for (int i = 0; i < polygons.length; i++) {
            jtsPolygons[i] = GeometryFactoryAdaptor.toJtsPolygon(jtsGeometryFactory, polygons[i]);
        }
        return jtsPolygons;

    }

    static public com.vividsolutions.jts.geom.GeometryCollection toJtsGeometryCollection(
            com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory,
            GeometryCollection geometryCollection) {

        switch (geometryCollection.getType()) {
            case GeometryCollection:
            {
                if (geometryCollection instanceof GeometryCollectionAdaptor) {
                    return ((GeometryCollectionAdaptor) geometryCollection).getJts();
                }

                com.vividsolutions.jts.geom.Geometry[] jtsGeometries
                        = new com.vividsolutions.jts.geom.Geometry[geometryCollection.size()];
                for (int i = 0; i < jtsGeometries.length; i++) {
                    jtsGeometries[i] = GeometryFactoryAdaptor.toJtsGeometry(
                            jtsGeometryFactory, geometryCollection.getGeometryAt(i));
                }

                return jtsGeometryFactory.createGeometryCollection(jtsGeometries);
            }
            case MultiPoint:
                return GeometryFactoryAdaptor.toJtsMultiPoint(jtsGeometryFactory, (MultiPoint) geometryCollection);
            case MultiLineString:
                return GeometryFactoryAdaptor.toJtsMultiLineString(jtsGeometryFactory, (MultiLineString) geometryCollection);
            case MultiPolygon:
                return GeometryFactoryAdaptor.toJtsMultiPolygon(jtsGeometryFactory, (MultiPolygon) geometryCollection);
        }
        return null;

    }

    static public com.vividsolutions.jts.geom.MultiPoint toJtsMultiPoint(
            com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory,
            MultiPoint multiPoint) {

        if (multiPoint instanceof MultiPointAdaptor) {
            return ((MultiPointAdaptor) multiPoint).getJts();
        }

        com.vividsolutions.jts.geom.Point[] jtsPoints
                = new com.vividsolutions.jts.geom.Point[multiPoint.size()];
        for (int i = 0; i < jtsPoints.length; i++) {
            jtsPoints[i] = GeometryFactoryAdaptor.toJtsPoint(
                    jtsGeometryFactory, multiPoint.getPointAt(i));
        }

        return jtsGeometryFactory.createMultiPoint(jtsPoints);
    }

    static public com.vividsolutions.jts.geom.MultiLineString toJtsMultiLineString(
            com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory,
            MultiLineString multiLineString) {

        if (multiLineString instanceof MultiLineStringAdaptor) {
            return ((MultiLineStringAdaptor) multiLineString).getJts();
        }

        com.vividsolutions.jts.geom.LineString[] jtsLineStrings
                = new com.vividsolutions.jts.geom.LineString[multiLineString.size()];
        for (int i = 0; i < jtsLineStrings.length; i++) {
            jtsLineStrings[i] = GeometryFactoryAdaptor.toJtsLineString(
                    jtsGeometryFactory, multiLineString.getLineStringAt(i));
        }

        return jtsGeometryFactory.createMultiLineString(jtsLineStrings);
    }

    static public com.vividsolutions.jts.geom.MultiPolygon toJtsMultiPolygon(
            com.vividsolutions.jts.geom.GeometryFactory jtsGeometryFactory,
            MultiPolygon multiPolygon) {

        if (multiPolygon instanceof MultiPolygonAdaptor) {
            return ((MultiPolygonAdaptor) multiPolygon).getJts();
        }

        com.vividsolutions.jts.geom.Polygon[] jtsPolygons
                = new com.vividsolutions.jts.geom.Polygon[multiPolygon.size()];
        for (int i = 0; i < jtsPolygons.length; i++) {
            jtsPolygons[i] = GeometryFactoryAdaptor.toJtsPolygon(
                    jtsGeometryFactory, multiPolygon.getPolygonAt(i));
        }

        return jtsGeometryFactory.createMultiPolygon(jtsPolygons);
    }

}
