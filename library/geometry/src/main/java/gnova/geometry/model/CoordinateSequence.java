package gnova.geometry.model;

import gnova.annotation.Immutable;
import gnova.annotation.NotNull;
import gnova.util.ReadOnlyCollection;
import gnova.util.ReadOnlyIterator;

/**
 * 坐标序列
 *
 * <p>坐标序列是一个{@link ReadOnlyCollection 只读的容器}，且容器的元素类型为{@link Coordinate 坐标}
 *
 * @see ReadOnlyCollection
 * @see Coordinate
 * @see Cloneable
 * @author birderyu
 * @date 2017/6/21
 * @version 1.0.0
 */
@Immutable
public interface CoordinateSequence
        extends ReadOnlyCollection<Coordinate>, Cloneable {

    /**
     * 获取坐标序列的维度值
     *
     * @return 维度值
     */
    int getDimension();

    @NotNull
    Coordinate getCoordinateAt(int n);

    default double getXAt(int n) {
        return getCoordinateAt(n).getX();
    }

    default double getYAt(int n) {
        return getCoordinateAt(n).getY();
    }

    default double getZAt(int n) {
        return getCoordinateAt(n).getZ();
    }

    default double getMAt(int n) {
        return getCoordinateAt(n).getM();
    }

    default double getOrdinateAt(int n, int ordinateId) {
        return getCoordinateAt(n).getOrdinate(ordinateId);
    }

    @NotNull
    default BoundingBox getBoundingBox() {
        BoundingBox bbox = new BoundingBox();
        for (Coordinate coord : this) {
            bbox = bbox.expandToInclude(coord);
        }
        return bbox;
    }

    @Override
    default ReadOnlyIterator<Coordinate> iterator() {
        return new CoordinateSequenceIterator(this, 0);
    }

    @Override
    @NotNull
    default Coordinate[] toArray() {
        return toArray(new Coordinate[size()]);
    }

    @NotNull
    CoordinateSequence clone();

}
