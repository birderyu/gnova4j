package gnova.geometry.model.impl.jts;

import gnova.geometry.model.LinearRing;

/**
 * Created by Birderyu on 2017/6/23.
 */
final class LinearRingAdaptor
        extends LineStringAdaptor implements LinearRing {

    public LinearRingAdaptor(com.vividsolutions.jts.geom.LinearRing jtsLinearRing) {
        super(jtsLinearRing);
    }

    @Override
    protected com.vividsolutions.jts.geom.LinearRing getJts() {
        return (com.vividsolutions.jts.geom.LinearRing) super.getJts();
    }

    @Override
    public LinearRing reverse() {
        return (LinearRing) super.reverse();
    }

    @Override
    public LinearRing normalize() {
        return (LinearRing) super.clone();
    }

    @Override
    public LinearRing clone() {
        return (LinearRing) super.clone();
    }
}
