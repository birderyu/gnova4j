package gnova.geometry.model.impl.jts;

import gnova.core.ArrayIterator;
import gnova.core.ConvertIterator;
import gnova.core.annotation.NotNull;
import gnova.geometry.model.*;
import gnova.geometry.model.AbstractGeometry;

/**
 * Created by Birderyu on 2017/6/22.
 */
abstract class AbstractGeometryAdaptor
    extends AbstractGeometry {

    @NotNull
    private final org.locationtech.jts.geom.Geometry jtsGeometry;

    protected AbstractGeometryAdaptor(@NotNull org.locationtech.jts.geom.Geometry jtsGeometry) {
        super(new GeometryFactoryAdaptor(jtsGeometry.getFactory()));
        this.jtsGeometry = jtsGeometry;
    }

    @Override
    public GeometryFactoryAdaptor getFactory() {
        return (GeometryFactoryAdaptor) factory;
    }

    @Override
    public BoundingBox getBoundingBox() {
        org.locationtech.jts.geom.Envelope envelope = jtsGeometry.getEnvelopeInternal();
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
    public Coordinate getCoordinate() {
        org.locationtech.jts.geom.Coordinate jtsCoordinate = jtsGeometry.getCoordinate();
        return new Coordinate(jtsCoordinate.x, jtsCoordinate.y, jtsCoordinate.z);
    }

    @Override
    public Iterable<Coordinate> getCoordinates() {
        org.locationtech.jts.geom.Coordinate[] jtsCoordinates = jtsGeometry.getCoordinates();
        return () -> new ConvertIterator<>(
                new ArrayIterator<>(jtsCoordinates),
                jtsCoordinate -> new Coordinate(jtsCoordinate.x, jtsCoordinate.y, jtsCoordinate.z));
    }

    @Override
    public int getSrid() {
        return jtsGeometry.getSRID();
    }

    @Override
    public Geometry reverse() {
        return getFactory().fromJtsGeometry(jtsGeometry.reverse());
    }

    @Override
    public Geometry normalize() {
        org.locationtech.jts.geom.Geometry newJtsGeometry = jtsGeometry.copy();
        newJtsGeometry.normalize();
        return getFactory().fromJtsGeometry(newJtsGeometry);
    }

    @Override
    public boolean exactlyEquals(@NotNull Geometry other) {
        return jtsGeometry.equalsExact(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean exactlyEquals(@NotNull Geometry other, double tolerance) {
        return jtsGeometry.equalsExact(getFactory().toJtsGeometry(other), tolerance);
    }

    ////////////////////////////////////
    // RelationalOperator
    ////////////////////////////////////

    @Override
    public boolean equals(Geometry other) {
        // 当前几何对象不可能为NONE
        if (this == other) {
            return true;
        } else if (other == NONE) {
            return false;
        }
        return topologicallyEquals(other);
    }

    @Override
    public boolean topologicallyEquals(@NotNull Geometry other) {
        if (other == NONE) {
            return false;
        }
        return jtsGeometry.equalsTopo(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean intersects(Geometry other) {
        if (other == NONE) {
            return false;
        }
        return jtsGeometry.intersects(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean disjoint(Geometry other) {
        if (other == NONE) {
            return false;
        }
        return jtsGeometry.disjoint(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean touches(Geometry other) {
        if (other == NONE) {
            return false;
        }
        return jtsGeometry.touches(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean crosses(Geometry other) {
        if (other == NONE) {
            return false;
        }
        return jtsGeometry.crosses(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean within(Geometry other) {
        if (other == NONE) {
            return false;
        }
        return jtsGeometry.within(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean contains(Geometry other) {
        if (other == NONE) {
            return false;
        }
        return jtsGeometry.contains(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean overlaps(Geometry other) {
        if (other == NONE) {
            return false;
        }
        return jtsGeometry.overlaps(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean covers(Geometry other) {
        if (other == NONE) {
            return false;
        }
        return jtsGeometry.covers(getFactory().toJtsGeometry(other));
    }

    @Override
    public boolean coveredBy(Geometry other) {
        if (other == NONE) {
            return false;
        }
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
    public Geometry buffer(double distance) {
        // 使用EnhancedPrecisionOp可以解决鲁棒性问题，避免抛出TopologyException异常
        return getFactory().fromJtsGeometry(
                org.locationtech.jts.precision.EnhancedPrecisionOp.buffer(
                        jtsGeometry, distance));
    }

    @Override
    public Geometry buffer(double distance, int quadrantSegments) {
        return getFactory().fromJtsGeometry(jtsGeometry.buffer(distance, quadrantSegments));
    }

    @Override
    public Geometry buffer(double distance, int quadrantSegments, int endCapStyle) {
        return getFactory().fromJtsGeometry(jtsGeometry.buffer(
                distance, quadrantSegments, endCapStyle));
    }

    @Override
    public Geometry intersection(Geometry other) {
        // 使用EnhancedPrecisionOp可以解决鲁棒性问题，避免抛出TopologyException异常
        return getFactory().fromJtsGeometry(
                org.locationtech.jts.precision.EnhancedPrecisionOp.intersection(
                        jtsGeometry, getFactory().toJtsGeometry(other)));
    }

    @Override
    public Geometry union(Geometry other) {
        // 使用EnhancedPrecisionOp可以解决鲁棒性问题，避免抛出TopologyException异常
        return getFactory().fromJtsGeometry(
                org.locationtech.jts.precision.EnhancedPrecisionOp.union(
                        jtsGeometry, getFactory().toJtsGeometry(other)));
    }

    @Override
    public Geometry difference(Geometry other) {
        // 使用EnhancedPrecisionOp可以解决鲁棒性问题，避免抛出TopologyException异常
        return getFactory().fromJtsGeometry(
                org.locationtech.jts.precision.EnhancedPrecisionOp.difference(
                        jtsGeometry, getFactory().toJtsGeometry(other)));
    }

    @Override
    public Geometry symmetricDifference(Geometry other) {
        // 使用EnhancedPrecisionOp可以解决鲁棒性问题，避免抛出TopologyException异常
        return getFactory().fromJtsGeometry(
                org.locationtech.jts.precision.EnhancedPrecisionOp.symDifference(
                        jtsGeometry, getFactory().toJtsGeometry(other)));
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
                org.locationtech.jts.simplify.TopologyPreservingSimplifier.simplify(
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
    public boolean isWithinDistance(Geometry other, double distance) {
        return jtsGeometry.isWithinDistance(getFactory().toJtsGeometry(other), distance);
    }

    @Override
    public Coordinate[] nearestPoints(Geometry other) {
        org.locationtech.jts.operation.distance.DistanceOp distanceOp
                = new org.locationtech.jts.operation.distance.DistanceOp(jtsGeometry, getFactory().toJtsGeometry(other));
        org.locationtech.jts.geom.Coordinate[] coordinates = distanceOp.nearestPoints();
        if (coordinates == null || coordinates.length != 2) {
            return null;
        }
        return new Coordinate[] {
                new Coordinate(coordinates[0].x, coordinates[0].y, coordinates[0].z),
                new Coordinate(coordinates[1].x, coordinates[1].y, coordinates[1].z)};
    }

    ////////////////////////////////////
    // AffineOperator
    ////////////////////////////////////
    @Override
    @NotNull
    public Geometry translate(double offsetX, double offsetY) {
        org.locationtech.jts.geom.util.AffineTransformation at = org.locationtech.jts.geom.util.AffineTransformation.translationInstance(offsetX, offsetX);
        return getFactory().fromJtsGeometry(at.transform(jtsGeometry));
    }

    @Override
    @NotNull
    public Geometry scale(double baseX, double baseY, double scaleX, double scaleY) {
        org.locationtech.jts.geom.util.AffineTransformation at = org.locationtech.jts.geom.util.AffineTransformation.scaleInstance(scaleX, scaleY, baseX, baseY);
        return getFactory().fromJtsGeometry(at.transform(jtsGeometry));
    }

    @Override
    @NotNull
    public Geometry scale(double scaleX, double scaleY) {
        org.locationtech.jts.geom.util.AffineTransformation at = org.locationtech.jts.geom.util.AffineTransformation.scaleInstance(scaleX, scaleY);
        return getFactory().fromJtsGeometry(at.transform(jtsGeometry));
    }

    @Override
    @NotNull
    public Geometry rotate(double baseX, double baseY, double angle) {
        double radian = angle * Math.PI / 180.0;
        org.locationtech.jts.geom.util.AffineTransformation at = org.locationtech.jts.geom.util.AffineTransformation.rotationInstance(radian, baseX, baseY);
        return getFactory().fromJtsGeometry(at.transform(jtsGeometry));
    }

    @Override
    @NotNull
    public Geometry rotate(double angle) {
        double radian = angle * Math.PI / 180.0;
        org.locationtech.jts.geom.util.AffineTransformation at = org.locationtech.jts.geom.util.AffineTransformation.rotationInstance(radian);
        return getFactory().fromJtsGeometry(at.transform(jtsGeometry));
    }

    @Override
    @NotNull
    public Geometry shear(double shearX, double shearY) {
        org.locationtech.jts.geom.util.AffineTransformation at = org.locationtech.jts.geom.util.AffineTransformation.shearInstance(shearX, shearY);
        return getFactory().fromJtsGeometry(at.transform(jtsGeometry));
    }

    @Override
    @NotNull
    public Geometry reflection(double baseX, double baseY, double x, double y) {
        org.locationtech.jts.geom.util.AffineTransformation at = org.locationtech.jts.geom.util.AffineTransformation.reflectionInstance(baseX, baseY, x, y);
        return getFactory().fromJtsGeometry(at.transform(jtsGeometry));
    }

    @Override
    @NotNull
    public Geometry reflection(double x, double y) {
        org.locationtech.jts.geom.util.AffineTransformation at = org.locationtech.jts.geom.util.AffineTransformation.reflectionInstance(x, y);
        return getFactory().fromJtsGeometry(at.transform(jtsGeometry));
    }


    ////////////////////////////////////
    // Object
    ////////////////////////////////////
    @Override
    public Geometry clone() {
        return getFactory().fromJtsGeometry(jtsGeometry.copy());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || obj == NONE || !(obj instanceof Geometry)) {
            return false;
        }
        return exactlyEquals((Geometry) obj);
    }

    @Override
    public int hashCode() {
        return jtsGeometry.hashCode();
    }

    @Override
    public int compareTo(Geometry geometry) {
        // 当前几何对象不可能为NONE
        if (geometry == NONE) {
            return 1;
        } else {
            return jtsGeometry.compareTo(getFactory().toJtsGeometry(geometry));
        }
    }

    public org.locationtech.jts.geom.Geometry getJts() {
        return jtsGeometry;
    }
}
