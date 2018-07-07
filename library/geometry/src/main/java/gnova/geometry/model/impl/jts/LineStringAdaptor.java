package gnova.geometry.model.impl.jts;

import com.vividsolutions.jts.linearref.LengthIndexedLine;
import gnova.geometry.factory.jts.CoordinateSequenceFactoryAdaptor;
import gnova.geometry.factory.jts.GeometryFactoryAdaptor;
import gnova.geometry.model.*;

/**
 * Created by Birderyu on 2017/6/23.
 */
public class LineStringAdaptor
        extends AbstractGeometryAdaptor implements LineString {

    public LineStringAdaptor(com.vividsolutions.jts.geom.LineString jtsLineString) {
        super(jtsLineString);
    }

    @Override
    public CoordinateSequence getCoordinateSequence() {
        return new CoordinateSequenceAdaptor(getJts().getCoordinateSequence());
    }

    @Override
    public Coordinate getCoordinateAt(int n) {
        return CoordinateSequenceFactoryAdaptor.fromJtsCoordinate(getJts().getCoordinateN(n));
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
    public Geometry extract(int start, int end) {
        com.vividsolutions.jts.geom.LineString jts = getJts();
        LengthIndexedLine indexedLine = new LengthIndexedLine(jts);
        return GeometryFactoryAdaptor.fromJtsGeometry(indexedLine.extractLine(start, end));
    }

    @Override
    public com.vividsolutions.jts.geom.LineString getJts() {
        return (com.vividsolutions.jts.geom.LineString) super.getJts();
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

}
