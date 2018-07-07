package gnova.geometry.model.impl.jts;

import gnova.geometry.model.GeometryType;
import gnova.geometry.model.LineString;
import gnova.geometry.model.MultiLineString;

/**
 * Created by Birderyu on 2017/6/23.
 */
public final class MultiLineStringAdaptor
        extends GeometryCollectionAdaptor<LineString> implements MultiLineString {

    public MultiLineStringAdaptor(com.vividsolutions.jts.geom.MultiLineString jtsMultiLineString) {
        super(jtsMultiLineString);
    }

    @Override
    public GeometryType getType() {
        return GeometryType.MultiLineString;
    }

    @Override
    public boolean isClosed() {
        return getJts().isClosed();
    }

    @Override
    public com.vividsolutions.jts.geom.MultiLineString getJts() {
        return (com.vividsolutions.jts.geom.MultiLineString) super.getJts();
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

}