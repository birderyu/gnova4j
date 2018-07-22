package gnova.geometry.model.impl.jts;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.Geometry;
import gnova.geometry.model.MultiPoint;
import gnova.geometry.model.Point;

/**
 * Created by Birderyu on 2017/6/23.
 */
final class MultiPointAdaptor
        extends GeometryCollectionAdaptor<Point> implements MultiPoint {

    public MultiPointAdaptor(org.locationtech.jts.geom.MultiPoint jtsMultiPoint) {
        super(jtsMultiPoint);
    }

    @Override
    public org.locationtech.jts.geom.MultiPoint getJts() {
        return (org.locationtech.jts.geom.MultiPoint) super.getJts();
    }

    @Override
    @NotNull
    public Geometry getBoundary() {
        return NONE;
    }

    @Override
    public MultiPoint reverse() {
        return clone();
    }

    @Override
    public MultiPoint normalize() {
        return (MultiPoint) super.normalize();
    }

    @Override
    public MultiPoint clone() {
        return (MultiPoint) super.clone();
    }

}
