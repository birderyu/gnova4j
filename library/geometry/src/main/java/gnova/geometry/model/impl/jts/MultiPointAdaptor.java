package gnova.geometry.model.impl.jts;

import gnova.geometry.model.MultiPoint;
import gnova.geometry.model.Point;

/**
 * Created by Birderyu on 2017/6/23.
 */
final class MultiPointAdaptor
        extends GeometryCollectionAdaptor<Point> implements MultiPoint {

    public MultiPointAdaptor(com.vividsolutions.jts.geom.MultiPoint jtsMultiPoint) {
        super(jtsMultiPoint);
    }

    @Override
    protected com.vividsolutions.jts.geom.MultiPoint getJts() {
        return (com.vividsolutions.jts.geom.MultiPoint) super.getJts();
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
