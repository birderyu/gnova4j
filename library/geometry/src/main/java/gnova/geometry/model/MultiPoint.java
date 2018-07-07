package gnova.geometry.model;

import gnova.annotation.NotNull;
import gnova.geometry.model.pattern.Puntal;
import gnova.util.ReadOnlyIterator;

/**
 * 多点
 * 
 * @author Birderyu
 * @date 2017/6/21
 */
public interface MultiPoint
        extends GeometryCollection<Point>, Puntal {

    default Point getPointAt(int n) {
        return getGeometryAt(n);
    }

    @Override
    @NotNull
    default ReadOnlyIterator<Point> iterator() {
        return new MultiPointIterator(this, 0);
    }

    @Override
    default GeometryType getType() {
        return GeometryType.MultiPoint;
    }

    @Override
    default int getDimension() {
        return Puntal.DIMENSION;
    }

    @Override
    MultiPoint reverse();

    @Override
    MultiPoint normalize();

    @Override
    MultiPoint clone();

}
