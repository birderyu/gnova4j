package gnova.geometry.model.impl.jts;

import gnova.geometry.factory.jts.CoordinateSequenceFactoryAdaptor;
import gnova.geometry.model.Coordinate;
import gnova.geometry.model.GeometryType;
import gnova.geometry.model.Point;

/**
 * Created by Birderyu on 2017/6/22.
 */
public final class PointAdaptor
        extends AbstractGeometryAdaptor implements Point {

    public PointAdaptor(com.vividsolutions.jts.geom.Point jtsPoint) {
        super(jtsPoint);
    }

    @Override
    public GeometryType getType() {
        return GeometryType.Point;
    }

    @Override
    public double getX() {
        return getJts().getX();
    }

    @Override
    public double getY() {
        return getJts().getY();
    }

    @Override
    public Coordinate getCoordinate() {
        return CoordinateSequenceFactoryAdaptor.fromJtsCoordinate(getJts().getCoordinate());
    }

    @Override
    public Point reverse() {
        return (Point) super.reverse();
    }

    @Override
    public Point normalize() {
        return (Point) super.normalize();
    }

    @Override
    public Point clone() {
        return (Point) super.clone();
    }

    @Override
    public com.vividsolutions.jts.geom.Point getJts() {
        return (com.vividsolutions.jts.geom.Point) super.getJts();
    }

}
