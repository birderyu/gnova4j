package gnova.geometry.model;

import gnova.annotation.NotNull;
import gnova.geometry.model.pattern.Polygonal;
import gnova.util.ReadOnlyIterator;

/**
 * 多多边形
 * 
 * @author Birderyu
 * @date 2017/6/21
 */
public interface MultiPolygon
        extends GeometryCollection<Polygon>, Polygonal {

    default Polygon getPolygonAt(int n) {
        return getGeometryAt(n);
    }

    @Override
    @NotNull
    default ReadOnlyIterator<Polygon> iterator() {
        return new MultiPolygonIterator(this, 0);
    }

    @Override
    default GeometryType getType() {
        return GeometryType.MultiPolygon;
    }

    @Override
    default int getDimension() {
        return Polygonal.DIMENSION;
    }

    @Override
    MultiPolygon reverse();

    @Override
    MultiPolygon normalize();

    @Override
    MultiPolygon clone();

}
