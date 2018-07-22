package gnova.geometry.model.impl.jts;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.Coordinate;
import gnova.geometry.model.Geometry;
import gnova.geometry.model.GeometryType;
import gnova.geometry.model.Point;

/**
 * Created by Birderyu on 2017/6/22.
 */
final class PointAdaptor
        extends AbstractGeometryAdaptor implements Point {

    public PointAdaptor(org.locationtech.jts.geom.Point jtsPoint) {
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
        org.locationtech.jts.geom.Coordinate jtsCoordinate = getJts().getCoordinate();
        if (jtsCoordinate == null) {
            return Coordinate.NONE;
        }
        return new Coordinate(jtsCoordinate.x, jtsCoordinate.y, jtsCoordinate.z);
    }

    @Override
    @NotNull
    public Geometry getBoundary() {
        return NONE;
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
    public org.locationtech.jts.geom.Point getJts() {
        return (org.locationtech.jts.geom.Point) super.getJts();
    }

}
