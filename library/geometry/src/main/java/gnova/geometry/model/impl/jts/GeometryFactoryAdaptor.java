package gnova.geometry.model.impl.jts;
import gnova.core.annotation.NotNull;
import gnova.geometry.model.CoordinateSequenceFactory;
import gnova.geometry.model.*;
import gnova.geometry.model.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;

import java.util.Arrays;

/**
 * Created by Birderyu on 2017/6/21.
 */
public final class GeometryFactoryAdaptor
        implements GeometryFactory {

    enum JtsGeometryType {

        Point(org.locationtech.jts.geom.Point.class, GeometryType.Point.getType()),
        LineString(org.locationtech.jts.geom.LineString.class, GeometryType.LineString.getType()),
        LinearRing(org.locationtech.jts.geom.LinearRing.class, GeometryType.LinearRing.getType()),
        Polygon(org.locationtech.jts.geom.Polygon.class, GeometryType.Polygon.getType()),
        MultiPoint(org.locationtech.jts.geom.MultiPoint.class, GeometryType.MultiPoint.getType()),
        MultiLineString(org.locationtech.jts.geom.MultiLineString.class, GeometryType.MultiLineString.getType()),
        MultiPolygon(org.locationtech.jts.geom.MultiPolygon.class, GeometryType.MultiPolygon.getType()),
        GeometryCollection(org.locationtech.jts.geom.GeometryCollection.class, GeometryType.GeometryCollection.getType());

        private final Class<? extends org.locationtech.jts.geom.Geometry> binding;
        private final int geometryType;
        private final String name;

        JtsGeometryType(Class<? extends org.locationtech.jts.geom.Geometry> type,
                                int geometryType) {
            this.binding = type;
            this.geometryType = geometryType;
            this.name = type.getSimpleName();
        }

        public Class<? extends org.locationtech.jts.geom.Geometry> getBinding() {
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

        static public JtsGeometryType from(org.locationtech.jts.geom.Geometry geom) {
            if (geom != null) {
                return fromBinding(geom.getClass());
            }

            return null;
        }

        static public JtsGeometryType fromBinding(Class<? extends org.locationtech.jts.geom.Geometry> geomClass) {
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

    private final org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory;
    private final CoordinateSequenceFactory coordinateSequenceFactory;

    public GeometryFactoryAdaptor() {
        this(new org.locationtech.jts.geom.GeometryFactory());
    }

    public GeometryFactoryAdaptor(int srid) {
        this(new org.locationtech.jts.geom.GeometryFactory(new org.locationtech.jts.geom.PrecisionModel(), srid));
    }

    public GeometryFactoryAdaptor(Precision precision) {
        this(new org.locationtech.jts.geom.GeometryFactory(new org.locationtech.jts.geom.PrecisionModel()));
    }

    public GeometryFactoryAdaptor(Precision precision, int srid) {
        this(new org.locationtech.jts.geom.GeometryFactory(toJtsPrecisionModel(precision), srid));
    }

    public GeometryFactoryAdaptor(org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory) {
        this.jtsGeometryFactory = jtsGeometryFactory;
        coordinateSequenceFactory = new CoordinateSequenceFactoryAdaptor(jtsGeometryFactory.getCoordinateSequenceFactory());
    }

    public org.locationtech.jts.geom.GeometryFactory getJts() {
        return jtsGeometryFactory;
    }

    @Override
    public int getSrid() {
        return jtsGeometryFactory.getSRID();
    }

    @Override
    public Precision getPrecision() {
        return fromJtsPrecisionModel(jtsGeometryFactory.getPrecisionModel());
    }

    @Override
    public CoordinateSequenceFactory getCoordinateSequenceFactory() {
        return coordinateSequenceFactory;
    }

    @Override
    public Geometry createGeometry(BoundingBox bbox) {
        if (bbox == BoundingBox.NONE) {
            return Geometry.NONE;
        }
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
                        coordinate == Coordinate.NONE ? null :
                        CoordinateSequenceFactoryAdaptor.toJtsCoordinate(coordinate)));
    }

    @Override
    public LineString createLineString(Coordinate[] coordinates) {

        if (coordinates.length < 2) {
            throw new IllegalArgumentException("Invalid number of points in LineString (must not less than 2): "
                    + Arrays.toString(coordinates));
        } else {
            for (Coordinate coordinate : coordinates) {
                if (coordinate == null || coordinate == Coordinate.NONE) {
                    throw new IllegalArgumentException("Invalid number of coordinates in LineString (must not be null or NONE): "
                            + Arrays.toString(coordinates));
                }
            }
        }
        return GeometryFactoryAdaptor.fromJtsLineString(
                jtsGeometryFactory.createLineString(
                        CoordinateSequenceFactoryAdaptor.toJtsCoordinateArray(coordinates)));
    }

    @Override
    public LineString createLineString(CoordinateSequence coordinates) {

        if (coordinates.size() < 2) {
            throw new IllegalArgumentException("Invalid number of points in LineString (must not less than 2): "
                    + coordinates);
        } else {
            for (Coordinate coordinate : coordinates) {
                if (coordinate == null || coordinate == Coordinate.NONE) {
                    throw new IllegalArgumentException("Invalid number of coordinates in LineString (must not be null or NONE): "
                            + coordinates);
                }
            }
        }
        return GeometryFactoryAdaptor.fromJtsLineString(
                jtsGeometryFactory.createLineString(
                        CoordinateSequenceFactoryAdaptor.toJtsCoordinateSequence(coordinates)));
    }

    @Override
    public LinearRing createLinearRing(Coordinate[] coordinates) {

        if (coordinates.length < 4) {
            throw new IllegalArgumentException("Invalid number of points in LinearRing (must not less than 4): "
                    + coordinates);
        } else {
            for (Coordinate coordinate : coordinates) {
                if (coordinate == null || coordinate == Coordinate.NONE) {
                    throw new IllegalArgumentException("Invalid number of coordinates in LinearRing (must not be null or NONE): "
                            + Arrays.toString(coordinates));
                }
            }
        }
        return GeometryFactoryAdaptor.fromJtsLinearRing(
                jtsGeometryFactory.createLinearRing(
                        CoordinateSequenceFactoryAdaptor.toJtsCoordinateArray(coordinates)));
    }

    @Override
    public LinearRing createLinearRing(CoordinateSequence coordinates) {

        if (coordinates.size() < 4) {
            throw new IllegalArgumentException("Invalid number of points in LinearRing (must not less than 4): "
                    + coordinates);
        } else {
            for (Coordinate coordinate : coordinates) {
                if (coordinate == null || coordinate == Coordinate.NONE) {
                    throw new IllegalArgumentException("Invalid number of coordinates in LinearRing (must not be null or NONE): "
                            + coordinates);
                }
            }
        }
        return GeometryFactoryAdaptor.fromJtsLinearRing(
                jtsGeometryFactory.createLinearRing(
                        CoordinateSequenceFactoryAdaptor.toJtsCoordinateSequence(coordinates)));
    }

    @Override
    public Polygon createPolygon(LinearRing shell, LinearRing[] holes) {
        if (holes != null) {
            for (LinearRing hole : holes) {
                if (hole == null) {
                    throw new IllegalArgumentException("holes has null");
                }
            }
        }
        return GeometryFactoryAdaptor.fromJtsPolygon(
                jtsGeometryFactory.createPolygon(
                        toJtsLinearRing(shell),
                        holes == null ? null : toJtsLinearRingArray(holes)));
    }

    @Override
    public GeometryCollection createGeometryCollection(Geometry[] geometries) {
        for (Geometry geometry : geometries) {
            if (geometry == null || geometry == Geometry.NONE) {
                throw new IllegalArgumentException("geometries has null or NONE");
            }
        }
        return GeometryFactoryAdaptor.fromJtsGeometryCollection(
                jtsGeometryFactory.createGeometryCollection(
                        toJtsGeometryArray(geometries)));
    }

    @Override
    public MultiPoint createMultiPoint(Point[] points) {
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("points has null");
            }
        }
        return GeometryFactoryAdaptor.fromJtsMultiPoint(
                jtsGeometryFactory.createMultiPoint(
                        toJtsPointArray(points)));
    }

    @Override
    public MultiPoint createMultiPoint(Coordinate[] coordinates) {
        for (Coordinate coordinate : coordinates) {
            if (coordinate == null || coordinate == Coordinate.NONE) {
                throw new IllegalArgumentException("coordinates has null or NONE");
            }
        }
        return GeometryFactoryAdaptor.fromJtsMultiPoint(
                jtsGeometryFactory.createMultiPointFromCoords(
                        CoordinateSequenceFactoryAdaptor.toJtsCoordinateArray(coordinates)));
    }

    @Override
    public MultiPoint createMultiPoint(CoordinateSequence coordinates) {
        for (Coordinate coordinate : coordinates) {
            if (coordinate == null || coordinate == Coordinate.NONE) {
                throw new IllegalArgumentException("coordinates has null or NONE");
            }
        }
        return GeometryFactoryAdaptor.fromJtsMultiPoint(
                jtsGeometryFactory.createMultiPoint(
                        CoordinateSequenceFactoryAdaptor.toJtsCoordinateSequence(coordinates)));
    }

    @Override
    public MultiLineString createMultiLineString(LineString[] lineStrings) {
        for (LineString lineString : lineStrings) {
            if (lineString == null) {
                throw new IllegalArgumentException("lineString has null");
            }
        }
        return GeometryFactoryAdaptor.fromJtsMultiLineString(
                jtsGeometryFactory.createMultiLineString(
                        toJtsLineStringArray(lineStrings)));
    }

    @Override
    public MultiPolygon createMultiPolygon(Polygon[] polygons) {
        for (Polygon polygon : polygons) {
            if (polygon == null) {
                throw new IllegalArgumentException("polygons has null");
            }
        }
        return GeometryFactoryAdaptor.fromJtsMultiPolygon(
                jtsGeometryFactory.createMultiPolygon(
                        toJtsPolygonArray(polygons)));
    }

    /**
     *
     * @param geometry
     * @return
     * @throws IllegalArgumentException 若集合对象为{@link Geometry#NONE 空几何对象}，则抛出此异常
     */
    public org.locationtech.jts.geom.Geometry toJtsGeometry(@NotNull Geometry geometry)
            throws IllegalArgumentException {
        return GeometryFactoryAdaptor.toJtsGeometry(jtsGeometryFactory, geometry);
    }

    public org.locationtech.jts.geom.Geometry[] toJtsGeometryArray(Geometry[] geometries) {
        return GeometryFactoryAdaptor.toJtsGeometryArray(jtsGeometryFactory, geometries);
    }

    public org.locationtech.jts.geom.Point toJtsPoint(Point point) {
        return GeometryFactoryAdaptor.toJtsPoint(jtsGeometryFactory, point);
    }

    public org.locationtech.jts.geom.Point[] toJtsPointArray(Point[] points) {
        return GeometryFactoryAdaptor.toJtsPointArray(jtsGeometryFactory, points);
    }

    public org.locationtech.jts.geom.LineString toJtsLineString(LineString lineString) {
        return GeometryFactoryAdaptor.toJtsLineString(jtsGeometryFactory, lineString);
    }

    public org.locationtech.jts.geom.LineString[] toJtsLineStringArray(LineString[] lineStrings) {
        return GeometryFactoryAdaptor.toJtsLineStringArray(jtsGeometryFactory, lineStrings);
    }

    public org.locationtech.jts.geom.LinearRing toJtsLinearRing(LinearRing linearRing) {
        return GeometryFactoryAdaptor.toJtsLinearRing(jtsGeometryFactory, linearRing);
    }

    public org.locationtech.jts.geom.LinearRing[] toJtsLinearRingArray(LinearRing[] linearRings) {
        return GeometryFactoryAdaptor.toJtsLinearRingArray(jtsGeometryFactory, linearRings);
    }

    public org.locationtech.jts.geom.Polygon toJtsPolygon(Polygon polygon) {
        return GeometryFactoryAdaptor.toJtsPolygon(jtsGeometryFactory, polygon);
    }

    public org.locationtech.jts.geom.Polygon[] toJtsPolygonArray(Polygon[] polygons) {
        return GeometryFactoryAdaptor.toJtsPolygonArray(jtsGeometryFactory, polygons);
    }

    public org.locationtech.jts.geom.GeometryCollection toJtsGeometryCollection(GeometryCollection geometryCollection) {
        return GeometryFactoryAdaptor.toJtsGeometryCollection(jtsGeometryFactory, geometryCollection);
    }

    public org.locationtech.jts.geom.MultiPoint toJtsMultiPoint(MultiPoint multiPoint) {
        return GeometryFactoryAdaptor.toJtsMultiPoint(jtsGeometryFactory, multiPoint);
    }

    public org.locationtech.jts.geom.MultiLineString toJtsMultiLineString(MultiLineString multiLineString) {
        return GeometryFactoryAdaptor.toJtsMultiLineString(jtsGeometryFactory, multiLineString);
    }

    public org.locationtech.jts.geom.MultiPolygon toJtsMultiPolygon(MultiPolygon multiPolygon) {
        return GeometryFactoryAdaptor.toJtsMultiPolygon(jtsGeometryFactory, multiPolygon);
    }

    static public Geometry fromJtsGeometry(org.locationtech.jts.geom.Geometry jtsGeometry) {

        if (jtsGeometry == null) {
            return Geometry.NONE;
        }
        switch (JtsGeometryType.from(jtsGeometry)) {
            case Point:
                return fromJtsPoint((org.locationtech.jts.geom.Point) jtsGeometry);
            case LinearRing:
                return fromJtsLinearRing((org.locationtech.jts.geom.LinearRing) jtsGeometry);
            case LineString:
                return fromJtsLineString((org.locationtech.jts.geom.LineString) jtsGeometry);
            case Polygon:
                return fromJtsPolygon((org.locationtech.jts.geom.Polygon) jtsGeometry);
            case MultiPoint:
                return fromJtsMultiPoint((org.locationtech.jts.geom.MultiPoint) jtsGeometry);
            case MultiLineString:
                return fromJtsMultiLineString((org.locationtech.jts.geom.MultiLineString) jtsGeometry);
            case MultiPolygon:
                return fromJtsMultiPolygon((org.locationtech.jts.geom.MultiPolygon) jtsGeometry);
            case GeometryCollection:
                return fromJtsGeometryCollection((org.locationtech.jts.geom.GeometryCollection) jtsGeometry);
        }
        return null;
    }

    static public Point fromJtsPoint(org.locationtech.jts.geom.Point jtsPoint) {
        return new PointAdaptor(jtsPoint);
    }

    static public LineString fromJtsLineString(org.locationtech.jts.geom.LineString jtsLineString) {

        switch (JtsGeometryType.from(jtsLineString)) {
            case LineString:
                return new LineStringAdaptor(jtsLineString);
            case LinearRing:
                return fromJtsLinearRing((org.locationtech.jts.geom.LinearRing) jtsLineString);
        }

        return null;
    }

    static public LinearRing fromJtsLinearRing(org.locationtech.jts.geom.LinearRing jtsLinearRing) {
        return new LinearRingAdaptor(jtsLinearRing);
    }

    static public Polygon fromJtsPolygon(org.locationtech.jts.geom.Polygon jtsPolygon) {
        return new PolygonAdaptor(jtsPolygon);
    }

    static public GeometryCollection fromJtsGeometryCollection(org.locationtech.jts.geom.GeometryCollection jtsGeometryCollection) {

        switch (JtsGeometryType.from(jtsGeometryCollection)) {
            case GeometryCollection:
                return new GeometryCollectionAdaptor(jtsGeometryCollection);
            case MultiPoint:
                return GeometryFactoryAdaptor.fromJtsMultiPoint((org.locationtech.jts.geom.MultiPoint) jtsGeometryCollection);
            case MultiLineString:
                return GeometryFactoryAdaptor.fromJtsMultiLineString((org.locationtech.jts.geom.MultiLineString) jtsGeometryCollection);
            case MultiPolygon:
                return GeometryFactoryAdaptor.fromJtsMultiPolygon((org.locationtech.jts.geom.MultiPolygon) jtsGeometryCollection);
        }

        return null;
    }

    static public MultiPoint fromJtsMultiPoint(org.locationtech.jts.geom.MultiPoint jtsMultiPoint) {
        return new MultiPointAdaptor(jtsMultiPoint);
    }

    static public MultiLineString fromJtsMultiLineString(org.locationtech.jts.geom.MultiLineString jtsMultiLineString) {
        return new MultiLineStringAdaptor(jtsMultiLineString);
    }

    static public MultiPolygon fromJtsMultiPolygon(org.locationtech.jts.geom.MultiPolygon jtsMultiPolygon) {
        return new MultiPolygonAdaptor(jtsMultiPolygon);
    }

    static public Precision fromJtsPrecisionModel(org.locationtech.jts.geom.PrecisionModel precisionModel) {
        if (precisionModel.getType() == PrecisionModel.FLOATING) {
            return new Precision(PrecisionMode.DoubleFloat);
        } else if (precisionModel.getType() == PrecisionModel.FLOATING_SINGLE) {
            return new Precision(PrecisionMode.SingleFloat);
        } else if (precisionModel.getType() == PrecisionModel.FIXED) {
            return new Precision(precisionModel.getScale());
        }
        return new Precision();
    }

    static public org.locationtech.jts.geom.Envelope toJtsEnvelope(BoundingBox bbox) {
        return new org.locationtech.jts.geom.Envelope(bbox.getMinX(), bbox.getMaxX(),
                bbox.getMinY(), bbox.getMaxY());
    }

    /**
     *
     * @param jtsGeometryFactory
     * @param geometry
     * @return
     * @throws IllegalArgumentException 若集合对象为{@link Geometry#NONE 空几何对象}，则抛出此异常
     */
    static public org.locationtech.jts.geom.Geometry toJtsGeometry(
            org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory,
            Geometry geometry) throws IllegalArgumentException {

        if (geometry == Geometry.NONE) {
            throw new IllegalArgumentException("geometry is NONE");
        } else if (geometry instanceof AbstractGeometryAdaptor) {
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

    static public org.locationtech.jts.geom.Geometry[] toJtsGeometryArray(
            org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory,
            Geometry[] geometries) {

        org.locationtech.jts.geom.Geometry[] jtsGeometries =
                new org.locationtech.jts.geom.Geometry[geometries.length];
        for (int i = 0; i < geometries.length; i++) {
            jtsGeometries[i] = GeometryFactoryAdaptor.toJtsGeometry(jtsGeometryFactory, geometries[i]);
        }
        return jtsGeometries;

    }

    static public org.locationtech.jts.geom.Point toJtsPoint(
            org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory,
            Point point) {

        if (point instanceof PointAdaptor) {
            return ((PointAdaptor) point).getJts();
        }
        return jtsGeometryFactory.createPoint(
                CoordinateSequenceFactoryAdaptor.toJtsCoordinate(point.getCoordinate()));

    }

    static public org.locationtech.jts.geom.Point[] toJtsPointArray(
            org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory,
            Point[] points) {

        org.locationtech.jts.geom.Point[] jtsPoints =
                new org.locationtech.jts.geom.Point[points.length];
        for (int i = 0; i < points.length; i++) {
            jtsPoints[i] = GeometryFactoryAdaptor.toJtsPoint(jtsGeometryFactory, points[i]);
        }
        return jtsPoints;

    }

    static public org.locationtech.jts.geom.LineString toJtsLineString(
            org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory,
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

    static public org.locationtech.jts.geom.LineString[] toJtsLineStringArray(
            org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory,
            LineString[] lineStrings) {

        org.locationtech.jts.geom.LineString[] jtsLineStrings =
                new org.locationtech.jts.geom.LineString[lineStrings.length];
        for (int i = 0; i < lineStrings.length; i++) {
            jtsLineStrings[i] = GeometryFactoryAdaptor.toJtsLineString(jtsGeometryFactory, lineStrings[i]);
        }
        return jtsLineStrings;

    }

    static public org.locationtech.jts.geom.LinearRing toJtsLinearRing(
            org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory,
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

    static public org.locationtech.jts.geom.LinearRing[] toJtsLinearRingArray(
            org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory,
            LinearRing[] linearRings) {

        org.locationtech.jts.geom.LinearRing[] jtsLinearRings =
                new org.locationtech.jts.geom.LinearRing[linearRings.length];
        for (int i = 0; i < linearRings.length; i++) {
            jtsLinearRings[i] = GeometryFactoryAdaptor.toJtsLinearRing(jtsGeometryFactory, linearRings[i]);
        }
        return jtsLinearRings;

    }

    static public org.locationtech.jts.geom.Polygon toJtsPolygon(
            org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory,
            Polygon polygon) {

        if (polygon instanceof PolygonAdaptor) {
            return ((PolygonAdaptor) polygon).getJts();
        }

        org.locationtech.jts.geom.LinearRing[] holes = null;
        if (polygon.getInteriorRingSize() > 0) {
            holes = new org.locationtech.jts.geom.LinearRing[polygon.getInteriorRingSize()];
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

    static public org.locationtech.jts.geom.Polygon[] toJtsPolygonArray(
            org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory,
            Polygon[] polygons) {

        org.locationtech.jts.geom.Polygon[] jtsPolygons =
                new org.locationtech.jts.geom.Polygon[polygons.length];
        for (int i = 0; i < polygons.length; i++) {
            jtsPolygons[i] = GeometryFactoryAdaptor.toJtsPolygon(jtsGeometryFactory, polygons[i]);
        }
        return jtsPolygons;

    }

    static public org.locationtech.jts.geom.GeometryCollection toJtsGeometryCollection(
            org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory,
            GeometryCollection geometryCollection) {

        switch (geometryCollection.getType()) {
            case GeometryCollection:
            {
                if (geometryCollection instanceof GeometryCollectionAdaptor) {
                    return ((GeometryCollectionAdaptor) geometryCollection).getJts();
                }

                org.locationtech.jts.geom.Geometry[] jtsGeometries
                        = new org.locationtech.jts.geom.Geometry[geometryCollection.size()];
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

    static public org.locationtech.jts.geom.MultiPoint toJtsMultiPoint(
            org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory,
            MultiPoint multiPoint) {

        if (multiPoint instanceof MultiPointAdaptor) {
            return ((MultiPointAdaptor) multiPoint).getJts();
        }

        org.locationtech.jts.geom.Point[] jtsPoints
                = new org.locationtech.jts.geom.Point[multiPoint.size()];
        for (int i = 0; i < jtsPoints.length; i++) {
            jtsPoints[i] = GeometryFactoryAdaptor.toJtsPoint(
                    jtsGeometryFactory, multiPoint.getPointAt(i));
        }

        return jtsGeometryFactory.createMultiPoint(jtsPoints);
    }

    static public org.locationtech.jts.geom.MultiLineString toJtsMultiLineString(
            org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory,
            MultiLineString multiLineString) {

        if (multiLineString instanceof MultiLineStringAdaptor) {
            return ((MultiLineStringAdaptor) multiLineString).getJts();
        }

        org.locationtech.jts.geom.LineString[] jtsLineStrings
                = new org.locationtech.jts.geom.LineString[multiLineString.size()];
        for (int i = 0; i < jtsLineStrings.length; i++) {
            jtsLineStrings[i] = GeometryFactoryAdaptor.toJtsLineString(
                    jtsGeometryFactory, multiLineString.getLineStringAt(i));
        }

        return jtsGeometryFactory.createMultiLineString(jtsLineStrings);
    }

    static public org.locationtech.jts.geom.MultiPolygon toJtsMultiPolygon(
            org.locationtech.jts.geom.GeometryFactory jtsGeometryFactory,
            MultiPolygon multiPolygon) {

        if (multiPolygon instanceof MultiPolygonAdaptor) {
            return ((MultiPolygonAdaptor) multiPolygon).getJts();
        }

        org.locationtech.jts.geom.Polygon[] jtsPolygons
                = new org.locationtech.jts.geom.Polygon[multiPolygon.size()];
        for (int i = 0; i < jtsPolygons.length; i++) {
            jtsPolygons[i] = GeometryFactoryAdaptor.toJtsPolygon(
                    jtsGeometryFactory, multiPolygon.getPolygonAt(i));
        }

        return jtsGeometryFactory.createMultiPolygon(jtsPolygons);
    }

    static public org.locationtech.jts.geom.PrecisionModel toJtsPrecisionModel(Precision precision) {
        switch (precision.getMode()) {
            case SingleFloat:
                return new org.locationtech.jts.geom.PrecisionModel(org.locationtech.jts.geom.PrecisionModel.FLOATING_SINGLE);
            case DoubleFloat:
                return new org.locationtech.jts.geom.PrecisionModel(org.locationtech.jts.geom.PrecisionModel.FLOATING);
            case CustomScale:
                return new org.locationtech.jts.geom.PrecisionModel(precision.getScale());
        }
        return new org.locationtech.jts.geom.PrecisionModel();
    }

}
