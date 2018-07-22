package gnova.geometry.model.impl.jts;

import org.locationtech.jts.linearref.LengthIndexedLine;
import gnova.geometry.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Birderyu on 2017/6/23.
 */
class LineStringAdaptor
        extends AbstractGeometryAdaptor implements LineString {

    public LineStringAdaptor(org.locationtech.jts.geom.LineString jtsLineString) {
        super(jtsLineString);
    }

    @Override
    public CoordinateSequence getCoordinateSequence() {
        return new CoordinateSequenceAdaptor(getJts().getCoordinateSequence());
    }

    @Override
    public Coordinate getCoordinateAt(int n) {
        org.locationtech.jts.geom.Coordinate jtsCoordinate = getJts().getCoordinateN(n);
        return new Coordinate(jtsCoordinate.x, jtsCoordinate.y, jtsCoordinate.z);
    }

    @Override
    public int size() {
        return getJts().getNumPoints();
    }

    @Override
    public Point getPointAt(int n) {
        return new PointAdaptor(getJts().getPointN(n));
    }

    @Override
    public Point getStartPoint() {
        return new PointAdaptor(getJts().getStartPoint());
    }

    @Override
    public Point getEndPoint() {
        return new PointAdaptor(getJts().getEndPoint());
    }

    @Override
    public boolean isClosed() {
        return getJts().isClosed();
    }

    @Override
    public boolean isRing() {
        return getJts().isRing();
    }

    @Override
    public double getLength() {
        return getJts().getLength();
    }

    @Override
    public org.locationtech.jts.geom.LineString getJts() {
        return (org.locationtech.jts.geom.LineString) super.getJts();
    }

    @Override
    public LineString reverse() {
        return (LineString) super.reverse();
    }

    @Override
    public LineString normalize() {
        return (LineString) super.clone();
    }

    @Override
    public LineString clone() {
        return (LineString) super.clone();
    }

    @Override
    public LineString extract(double startLength, double endLength) {
        org.locationtech.jts.geom.LineString jts = getJts();
        LengthIndexedLine indexedLine = new LengthIndexedLine(jts);
        org.locationtech.jts.geom.LineString result =
                (org.locationtech.jts.geom.LineString) indexedLine.extractLine(startLength, endLength);
        return GeometryFactoryAdaptor.fromJtsLineString(result);
    }

    @Override
    public Coordinate extractPoint(double length) {
        org.locationtech.jts.geom.LineString jts = getJts();
        LengthIndexedLine indexedLine = new LengthIndexedLine(jts);
        org.locationtech.jts.geom.Coordinate result = indexedLine.extractPoint(length);
        return new Coordinate(result.x, result.y, result.z);
    }

    @Override
    public Coordinate extractPoint(double length, double offset) {
        org.locationtech.jts.geom.LineString jts = getJts();
        LengthIndexedLine indexedLine = new LengthIndexedLine(jts);
        org.locationtech.jts.geom.Coordinate result = indexedLine.extractPoint(length, offset);
        return new Coordinate(result.x, result.y, result.z);
    }

    @Override
    public double lengthOf(double x, double y) {
        org.locationtech.jts.geom.LineString jts = getJts();
        LengthIndexedLine indexedLine = new LengthIndexedLine(jts);
        return indexedLine.indexOf(new org.locationtech.jts.geom.Coordinate(x, y));
    }

    @Override
    public double lengthOfAfter(double x, double y, double minLength) {
        org.locationtech.jts.geom.LineString jts = getJts();
        LengthIndexedLine indexedLine = new LengthIndexedLine(jts);
        return indexedLine.indexOfAfter(new org.locationtech.jts.geom.Coordinate(x, y), minLength);
    }

    @Override
    public double[] lengthsOf(LineString subLine) {
        org.locationtech.jts.geom.LineString jts = getJts();
        LengthIndexedLine indexedLine = new LengthIndexedLine(jts);
        org.locationtech.jts.geom.Geometry jtsGeometry = getFactory().toJtsGeometry(subLine);
        return indexedLine.indicesOf(jtsGeometry);
    }

    @Override
    public Geometry selfIntersections() {
        return selfIntersections(getFactory(), getJts());
    }

    public static Geometry selfIntersections(GeometryFactoryAdaptor gf, org.locationtech.jts.geom.Geometry l) {
        org.locationtech.jts.geom.Geometry lineEndPts = getEndPoints(l);
        org.locationtech.jts.geom.Geometry nodedLine = l.union(lineEndPts);
        org.locationtech.jts.geom.Geometry nodedEndPts = getEndPoints(nodedLine);
        org.locationtech.jts.geom.Geometry selfIntersections = nodedEndPts.difference(lineEndPts);
        return gf.fromJtsGeometry(selfIntersections);
    }

    public static org.locationtech.jts.geom.Geometry getEndPoints(org.locationtech.jts.geom.Geometry g) {
        List<org.locationtech.jts.geom.Coordinate> endPtList = new ArrayList<>();
        if (g instanceof org.locationtech.jts.geom.LineString) {
            org.locationtech.jts.geom.LineString line = (org.locationtech.jts.geom.LineString) g;

            endPtList.add(line.getCoordinateN(0));
            endPtList.add(line.getCoordinateN(line.getNumPoints() - 1));
        }
        else if (g instanceof org.locationtech.jts.geom.MultiLineString) {
            org.locationtech.jts.geom.MultiLineString mls = (org.locationtech.jts.geom.MultiLineString) g;
            for (int i = 0; i < mls.getNumGeometries(); i++) {
                org.locationtech.jts.geom.LineString line = (org.locationtech.jts.geom.LineString) mls.getGeometryN(i);
                endPtList.add(line.getCoordinateN(0));
                endPtList.add(line.getCoordinateN(line.getNumPoints() - 1));
            }
        }
        org.locationtech.jts.geom.Coordinate[] endPts = org.locationtech.jts.geom.CoordinateArrays.toCoordinateArray(endPtList);
        return g.getFactory().createMultiPointFromCoords(endPts);
    }
}
