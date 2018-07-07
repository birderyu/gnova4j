package gnova.geometry.model.impl.jts;

import gnova.geometry.model.MultiPolygon;
import gnova.geometry.model.Polygon;

/**
 * Created by Birderyu on 2017/6/23.
 */
public final class MultiPolygonAdaptor
        extends GeometryCollectionAdaptor<Polygon> implements MultiPolygon {

    public MultiPolygonAdaptor(com.vividsolutions.jts.geom.MultiPolygon jtsMultiPolygon) {
        super(jtsMultiPolygon);
    }

    @Override
    public com.vividsolutions.jts.geom.MultiPolygon getJts() {
        return (com.vividsolutions.jts.geom.MultiPolygon) super.getJts();
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
