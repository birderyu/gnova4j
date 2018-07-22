package gnova.geometry.model;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.operator.LinearOperator;
import gnova.geometry.model.pattern.Lineal;
import gnova.core.ReadOnlyIterator;

import java.util.Iterator;

/**
 * 多线串
 * 
 * @author Birderyu
 * @date 2017/6/21
 */
public interface MultiLineString
    extends GeometryCollection<LineString>, Lineal, LinearOperator<MultiLineString> {

    default boolean isClosed() {
        if (isEmpty()) {
            return false;
        }
        Iterator<LineString> lineStrings = iterator();
        while (lineStrings.hasNext()) {
            if (!(lineStrings.next().isClosed())) {
                return false;
            }
        }
        return true;
    }

    default LineString getLineStringAt(int n) {
        return getGeometryAt(n);
    }

    @Override
    @NotNull
    default ReadOnlyIterator<LineString> iterator() {
        return new MultiLineStringIterator(this, 0);
    }

    @Override
    default GeometryType getType() {
        return GeometryType.MultiLineString;
    }

    @Override
    default int getDimension() {
        return Lineal.DIMENSION;
    }

    @Override
    default double getEndLength() {
        return getLength();
    }

    @Override
    MultiLineString reverse();

    @Override
    MultiLineString normalize();

    @Override
    MultiLineString clone();

}
