package gnova.geometry.model;

import gnova.core.EmptyIterator;
import gnova.core.json.JsonArrayBuilder;
import gnova.core.json.JsonObjectBuilder;
import gnova.geometry.json.GeometryJSON;

public final class NullGeometry extends AbstractGeometry implements Geometry {

    public static final Geometry NONE = new NullGeometry(FactoryFinder.getDefaultGeometryFactory());

    private NullGeometry(GeometryFactory factory) {
        super(factory);
    }

    @Override
    public GeometryType getType() {
        return GeometryType.None;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return BoundingBox.NONE;
    }

    @Override
    public int getDimension() {
        return 0;
    }

    @Override
    public int getBoundaryDimension() {
        return 0;
    }

    @Override
    public int getCoordinateDimension() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isSimple() {
        return true;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public Coordinate getCoordinate() {
        return Coordinate.NONE;
    }

    @Override
    public Iterable<Coordinate> getCoordinates() {
        return () -> new EmptyIterator<>();
    }

    @Override
    public Geometry reverse() {
        return NONE;
    }

    @Override
    public Geometry normalize() {
        return NONE;
    }

    @Override
    public String toGeometryJSON() {
        return GeometryJSON.NONE.toString();
    }

    @Override
    public <JO, JA> JO toGeometryJSON(JsonObjectBuilder<JO> job, JsonArrayBuilder<JA> jab) {
        return GeometryJSON.NONE.toJsonObject(job, jab);
    }

    @Override
    public boolean exactlyEquals(Geometry other) {
        return false;
    }

    @Override
    public boolean exactlyEquals(Geometry other, double tolerance) {
        return false;
    }

    @Override
    public boolean equals(Geometry geometry) {
        return geometry == this;
    }

    @Override
    public boolean topologicallyEquals(Geometry other) {
        return false;
    }

    @Override
    public Geometry clone() {
        return NONE;
    }

    @Override
    public double distance(Geometry other) {
        return Double.NaN;
    }

    @Override
    public boolean isWithinDistance(Geometry other, double distance) {
        return false;
    }

    @Override
    public Coordinate[] nearestPoints(Geometry other) {
        return new Coordinate[0];
    }

    @Override
    public boolean contains(Geometry other) {
        return false;
    }

    @Override
    public boolean crosses(Geometry other) {
        return false;
    }

    @Override
    public boolean touches(Geometry other) {
        return false;
    }

    @Override
    public boolean disjoint(Geometry other) {
        return false;
    }

    @Override
    public boolean intersects(Geometry other) {
        return false;
    }

    @Override
    public boolean within(Geometry other) {
        return false;
    }

    @Override
    public boolean overlaps(Geometry other) {
        return false;
    }

    @Override
    public boolean covers(Geometry other) {
        return false;
    }

    @Override
    public boolean coveredBy(Geometry other) {
        return false;
    }

    @Override
    public Geometry getBoundary() {
        return NONE;
    }

    @Override
    public Geometry convexHull() {
        return NONE;
    }

    @Override
    public Geometry clip(BoundingBox bbox) {
        return NONE;
    }

    @Override
    public Geometry split(Geometry bladeIn) {
        return NONE;
    }

    @Override
    public Geometry cut(Geometry bladeIn) {
        return NONE;
    }

    @Override
    public Geometry buffer(double distance) {
        return NONE;
    }

    @Override
    public Geometry buffer(double distance, int quadrantSegments) {
        return NONE;
    }

    @Override
    public Geometry buffer(double distance, int quadrantSegments, int endCapStyle) {
        return NONE;
    }

    @Override
    public Geometry intersection(Geometry other) {
        return NONE;
    }

    @Override
    public Geometry union(Geometry other) {
        return NONE;
    }

    @Override
    public Geometry difference(Geometry other) {
        return NONE;
    }

    @Override
    public Geometry symmetricDifference(Geometry other) {
        return NONE;
    }

    @Override
    public Point getCentroid() {
        throw new UnsupportedOperationException("getCentroid to NONE");
    }

    @Override
    public Point getInterior() {
        throw new UnsupportedOperationException("getInterior to NONE");
    }

    @Override
    public Geometry simplify(double distanceTolerance) {
        return NONE;
    }

    @Override
    public Geometry triangulation(double distanceTolerance, GeometryType resultType) {
        return NONE;
    }

    @Override
    public Geometry translate(double offsetX, double offsetY) {
        return NONE;
    }

    @Override
    public Geometry scale(double baseX, double baseY, double scaleX, double scaleY) {
        return NONE;
    }

    @Override
    public Geometry scale(double scaleX, double scaleY) {
        return NONE;
    }

    @Override
    public Geometry rotate(double baseX, double baseY, double angle) {
        return NONE;
    }

    @Override
    public Geometry rotate(double angle) {
        return NONE;
    }

    @Override
    public Geometry shear(double shearX, double shearY) {
        return NONE;
    }

    @Override
    public Geometry reflection(double baseX, double baseY, double x, double y) {
        return NONE;
    }

    @Override
    public Geometry reflection(double x, double y) {
        return NONE;
    }

    @Override
    public int compareTo(Geometry o) {
        return o == NONE ? 0 : 1;
    }

}
