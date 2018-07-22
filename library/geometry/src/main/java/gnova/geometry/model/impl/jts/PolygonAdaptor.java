package gnova.geometry.model.impl.jts;

import gnova.geometry.model.Geometry;
import gnova.geometry.model.LinearRing;
import gnova.geometry.model.Polygon;

/**
 * Created by Birderyu on 2017/6/23.
 */
final class PolygonAdaptor
        extends AbstractGeometryAdaptor implements Polygon {

    public PolygonAdaptor(org.locationtech.jts.geom.Polygon jtsPolygon) {
        super(jtsPolygon);
    }

    @Override
    public LinearRing getExteriorRing() {
        return new LinearRingAdaptor((org.locationtech.jts.geom.LinearRing) getJts().getExteriorRing());
    }

    @Override
    public int getInteriorRingSize() {
        return getJts().getNumInteriorRing();
    }

    @Override
    public LinearRing getInteriorRingAt(int n) throws ArrayIndexOutOfBoundsException {
        return new LinearRingAdaptor((org.locationtech.jts.geom.LinearRing) getJts().getInteriorRingN(n));
    }

    @Override
    public boolean isRectangle() {
        return getJts().isRectangle();
    }

    @Override
    public double getArea() {
        return getJts().getArea();
    }

    @Override
    public org.locationtech.jts.geom.Polygon getJts() {
        return (org.locationtech.jts.geom.Polygon) super.getJts();
    }

    @Override
    public Polygon reverse() {
        return (Polygon) super.reverse();
    }

    @Override
    public Polygon normalize() {
        return (Polygon) super.normalize();
    }

    @Override
    public Polygon clone() {
        return (Polygon) super.clone();
    }

}
