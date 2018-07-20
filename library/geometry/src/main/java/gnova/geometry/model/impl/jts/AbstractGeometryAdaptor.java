package gnova.geometry.model.impl.jts;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.*;
import gnova.geometry.model.AbstractGeometry;

/**
 * Created by Birderyu on 2017/6/22.
 */
abstract class AbstractGeometryAdaptor
    extends AbstractGeometry {

    @NotNull
    private final com.vividsolutions.jts.geom.Geometry jtsGeometry;

    protected AbstractGeometryAdaptor(@NotNull com.vividsolutions.jts.geom.Geometry jtsGeometry) {
        super(new GeometryFactoryAdaptor(jtsGeometry.getFactory()));
        this.jtsGeometry = jtsGeometry;
    }

    @Override
    public GeometryFactoryAdaptor getFactory() {
        return (GeometryFactoryAdaptor) factory;
    }

    @Override
    public BoundingBox getBoundingBox() {
        com.vividsolutions.jts.geom.Envelope envelope = jtsGeometry.getEnvelopeInternal();
        return new BoundingBox(envelope.getMinX(), envelope.getMaxX(),
                envelope.getMinY(), envelope.getMaxY());
    }

    @Override
    public int getBoundaryDimension() {
        return jtsGeometry.getBoundaryDimension();
    }

    @Override
    public int getCoordinateDimension() {
        // JTS是一个二维几何包，因此其坐标不包含Z值
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return jtsGeometry.isEmpty();
    }

    @Override
    public boolean isSimple() {
        return jtsGeometry.isSimple();
    }

    @Override
    public boolean isValid() {
        return jtsGeometry.isValid();
    }

    @Override
    public Coordinate[] getCoordinates() {
        return CoordinateSequenceFactoryAdaptor.fromJtsCoordinateArray(jtsGeometry.getCoordinates());
    }

    @Override
    public int getSrid() {
        return jtsGeometry.getSRID();
    }

    @Override
    public void setSrid(int srid) {
        jtsGeometry.setSRID(srid);
    }

    @Override
    public Geometry reverse() {
        return getFactory().fromJtsGeometry(jtsGeometry.reverse());
    }

    @Override
    public Geometry normalize() {
        com.vividsolutions.jts.geom.Geometry newJtsGeometry
                = (com.vividsolutions.jts.geom.Geometry) jtsGeometry.clone();
        return getFactory().fromJtsGeometry(newJtsGeometry);
    }

    @Override
    public boolean equals(Geometry other) {
        return jtsGeometry.equals(getFactory().toJtsGeometry(other));
    }

    ////////////////////////////////////
    // RelationalOperator
    ////////////////////////////////////
    @Override
    public boolean intersects(Geometry other) {
        return jtsGeometry.intersects(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean disjoint(Geometry other) {
        return jtsGeometry.disjoint(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean touches(Geometry other) {
        return jtsGeometry.touches(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean crosses(Geometry other) {
        return jtsGeometry.crosses(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean within(Geometry other) {
        return jtsGeometry.within(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean contains(Geometry other) {
        return jtsGeometry.contains(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean overlaps(Geometry other) {
        return jtsGeometry.overlaps(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean covers(Geometry other) {
        return jtsGeometry.covers(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean coveredBy(Geometry other) {
        return jtsGeometry.coveredBy(getFactory().toJtsGeometry(other));
    }

    ////////////////////////////////////
    // TopologicalOperator
    ////////////////////////////////////
    @Override
    public Geometry getBoundary() {
        return getFactory().fromJtsGeometry(jtsGeometry.getBoundary());
    }

    @Override
    public Geometry convexHull() {
        return getFactory().fromJtsGeometry(jtsGeometry.convexHull());
    }

    @Override
    public Geometry clip(BoundingBox bbox) {
        throw new UnsupportedOperationException("clip");
    }

    @Override
    public Geometry split(Geometry bladeIn) {
        throw new UnsupportedOperationException("clip");
    }

    @Override
    public Geometry cut(Geometry bladeIn) {
        throw new UnsupportedOperationException("clip");
    }

    @Override
    public Polygon buffer(double distance) {
        return (Polygon) getFactory().fromJtsGeometry(jtsGeometry.buffer(distance));
    }

    @Override
    public Polygon buffer(double distance, int quadrantSegments) {
        return (Polygon) getFactory().fromJtsGeometry(jtsGeometry.buffer(distance, quadrantSegments));
    }

    @Override
    public Polygon buffer(double distance, int quadrantSegments, int endCapStyle) {
        return (Polygon) getFactory().fromJtsGeometry(jtsGeometry.buffer(
                distance, quadrantSegments, endCapStyle));
    }

    @Override
    public Geometry intersection(Geometry other) {
        return getFactory().fromJtsGeometry(
                jtsGeometry.intersection(getFactory().toJtsGeometry(other)));
    }

    @Override
    public Geometry union(Geometry other) {
        return getFactory().fromJtsGeometry(
                jtsGeometry.union(getFactory().toJtsGeometry(other)));
    }

    @Override
    public Geometry difference(Geometry other) {
        return getFactory().fromJtsGeometry(
                jtsGeometry.difference(getFactory().toJtsGeometry(other)));
    }

    @Override
    public Geometry symmetricDifference(Geometry other) {
        return getFactory().fromJtsGeometry(
                jtsGeometry.symDifference(getFactory().toJtsGeometry(other)));
    }

    @Override
    public Point getCentroid() {
        return getFactory().fromJtsPoint(jtsGeometry.getCentroid());
    }

    @Override
    public Point getInterior() {
        return getFactory().fromJtsPoint(jtsGeometry.getInteriorPoint());
    }

    @Override
    public Geometry simplify(double distanceTolerance) {
        return getFactory().fromJtsGeometry(
                com.vividsolutions.jts.simplify.TopologyPreservingSimplifier.simplify(
                        getFactory().toJtsGeometry(this), distanceTolerance));
    }

    @Override
    public Geometry triangulation(double distanceTolerance, GeometryType resultType) {
        throw new UnsupportedOperationException("triangulation");
    }

    ////////////////////////////////////
    // ProximityOperator
    ////////////////////////////////////
    @Override
    public double distance(Geometry other) {
        return jtsGeometry.distance(getFactory().toJtsGeometry(other));
    }

    @Override
    public Coordinate[] nearestPoints(Geometry other) {
        com.vividsolutions.jts.operation.distance.DistanceOp distanceOp
                = new com.vividsolutions.jts.operation.distance.DistanceOp(jtsGeometry, getFactory().toJtsGeometry(other));
        com.vividsolutions.jts.geom.Coordinate[] coordinates = distanceOp.nearestPoints();
        if (coordinates == null || coordinates.length != 2) {
            return null;
        }
        return new Coordinate[] {
                CoordinateSequenceFactoryAdaptor.fromJtsCoordinate(coordinates[0]),
                CoordinateSequenceFactoryAdaptor.fromJtsCoordinate(coordinates[1])};
    }

    ////////////////////////////////////
    // Object
    ////////////////////////////////////
    @Override
    public Geometry clone() {
        return getFactory().fromJtsGeometry(
                (com.vividsolutions.jts.geom.Geometry) jtsGeometry.clone());
    }

    @Override
    public int hashCode() {
        return jtsGeometry.hashCode();
    }

    @Override
    public int compareTo(Geometry geometry) {
        return jtsGeometry.compareTo(getFactory().toJtsGeometry(geometry));
    }

    public com.vividsolutions.jts.geom.Geometry getJts() {
        return jtsGeometry;
    }
}
