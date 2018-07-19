package gnova.geometry.model.impl.jts;

import gnova.geometry.model.LinearRing;
import gnova.geometry.model.Polygon;

/**
 * Created by Birderyu on 2017/6/23.
 */
final class PolygonAdaptor
        extends AbstractGeometryAdaptor implements Polygon {

    public PolygonAdaptor(com.vividsolutions.jts.geom.Polygon jtsPolygon) {
        super(jtsPolygon);
    }

    @Override
    public LinearRing getExteriorRing() {
        return new LinearRingAdaptor((com.vividsolutions.jts.geom.LinearRing) getJts().getExteriorRing());
    }

    @Override
    public int getInteriorRingSize() {
        return getJts().getNumInteriorRing();
    }

    @Override
    public LinearRing getInteriorRingAt(int n) {
        return new LinearRingAdaptor((com.vividsolutions.jts.geom.LinearRing) getJts().getInteriorRingN(n));
    }

    @Override
    public double getArea() {
        return getJts().getArea();
    }

    @Override
    public com.vividsolutions.jts.geom.Polygon getJts() {
        return (com.vividsolutions.jts.geom.Polygon) super.getJts();
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
