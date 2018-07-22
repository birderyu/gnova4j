package gnova.geometry.model.impl.jts;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.*;
import org.locationtech.jts.linearref.LengthIndexedLine;

/**
 * Created by Birderyu on 2017/6/23.
 */
final class MultiLineStringAdaptor
        extends GeometryCollectionAdaptor<LineString> implements MultiLineString {

    public MultiLineStringAdaptor(org.locationtech.jts.geom.MultiLineString jtsMultiLineString) {
        super(jtsMultiLineString);
    }

    @Override
    public double getLength() {
        return getJts().getLength();
    }

    @Override
    public boolean isClosed() {
        return getJts().isClosed();
    }

    @Override
    public org.locationtech.jts.geom.MultiLineString getJts() {
        return (org.locationtech.jts.geom.MultiLineString) super.getJts();
    }

    @Override
    @NotNull
    public Geometry getBoundary() {
        return getFactory().fromJtsGeometry(getJts().getBoundary());
    }

    @Override
    public MultiLineString reverse() {
        return (MultiLineString) super.reverse();
    }

    @Override
    public MultiLineString normalize() {
        return (MultiLineString) super.normalize();
    }

    @Override
    public MultiLineString clone() {
        return (MultiLineString) super.clone();
    }

    @Override
    public MultiLineString extract(double startLength, double endLength) {
        org.locationtech.jts.geom.MultiLineString jts = getJts();
        LengthIndexedLine indexedLine = new LengthIndexedLine(jts);
        org.locationtech.jts.geom.MultiLineString result =
                (org.locationtech.jts.geom.MultiLineString) indexedLine.extractLine(startLength, endLength);
        return GeometryFactoryAdaptor.fromJtsMultiLineString(result);
    }

    @Override
    public Coordinate extractPoint(double length) {
        org.locationtech.jts.geom.MultiLineString jts = getJts();
        LengthIndexedLine indexedLine = new LengthIndexedLine(jts);
        org.locationtech.jts.geom.Coordinate result = indexedLine.extractPoint(length);
        return new Coordinate(result.x, result.y, result.z);
    }

    @Override
    public Coordinate extractPoint(double length, double offset) {
        org.locationtech.jts.geom.MultiLineString jts = getJts();
        LengthIndexedLine indexedLine = new LengthIndexedLine(jts);
        org.locationtech.jts.geom.Coordinate result = indexedLine.extractPoint(length, offset);
        return new Coordinate(result.x, result.y, result.z);
    }

    @Override
    public double lengthOf(double x, double y) {
        org.locationtech.jts.geom.MultiLineString jts = getJts();
        LengthIndexedLine indexedLine = new LengthIndexedLine(jts);
        return indexedLine.indexOf(new org.locationtech.jts.geom.Coordinate(x, y));
    }

    @Override
    public double lengthOfAfter(double x, double y, double minLength) {
        org.locationtech.jts.geom.MultiLineString jts = getJts();
        LengthIndexedLine indexedLine = new LengthIndexedLine(jts);
        return indexedLine.indexOfAfter(new org.locationtech.jts.geom.Coordinate(x, y), minLength);
    }

    @Override
    public double[] lengthsOf(MultiLineString subLine) {
        org.locationtech.jts.geom.MultiLineString jts = getJts();
        LengthIndexedLine indexedLine = new LengthIndexedLine(jts);
        org.locationtech.jts.geom.Geometry jtsGeometry = getFactory().toJtsGeometry(subLine);
        return indexedLine.indicesOf(jtsGeometry);
    }

    @Override
    public Geometry selfIntersections() {
        return LineStringAdaptor.selfIntersections(getFactory(), getJts());
    }

}