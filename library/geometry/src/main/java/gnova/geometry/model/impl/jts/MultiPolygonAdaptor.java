package gnova.geometry.model.impl.jts;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.Geometry;
import gnova.geometry.model.MultiPolygon;
import gnova.geometry.model.Polygon;

/**
 * Created by Birderyu on 2017/6/23.
 */
final class MultiPolygonAdaptor
        extends GeometryCollectionAdaptor<Polygon> implements MultiPolygon {

    public MultiPolygonAdaptor(org.locationtech.jts.geom.MultiPolygon jtsMultiPolygon) {
        super(jtsMultiPolygon);
    }

    @Override
    public org.locationtech.jts.geom.MultiPolygon getJts() {
        return (org.locationtech.jts.geom.MultiPolygon) super.getJts();
    }

    @Override
    public double getArea() {
        return getJts().getArea();
    }

    @Override
    @NotNull
    public Geometry getBoundary() {
        return getFactory().fromJtsGeometry(getJts().getBoundary());
    }

    @Override
    public MultiPolygon reverse() {
        return (MultiPolygon) super.reverse();
    }

    @Override
    public MultiPolygon normalize() {
        return (MultiPolygon) super.normalize();
    }

    @Override
    public MultiPolygon clone() {
        return (MultiPolygon) super.clone();
    }

}
