package gnova.geometry.model;

import gnova.annotation.Immutable;
import gnova.geometry.model.operator.CollectionOperator;
import gnova.util.ReadOnlyCollection;
import gnova.util.ReadOnlyIterator;

/**
 * 几何集合
 *
 * @param <G> 集合元素的类型
 */
@Immutable
public interface GeometryCollection<G extends Geometry>
        extends Geometry, ReadOnlyCollection<G>, CollectionOperator {

    @Override
    int size();

    G getGeometryAt(int n);

    double getLength();

    double getArea();

    double getVolume();

    @Override
    default GeometryType getType() {
        return GeometryType.GeometryCollection;
    }

    @Override
    default boolean isEmpty() {
        return size() == 0;
    }

    @Override
    GeometryCollection<G> reverse();

    @Override
    GeometryCollection<G> normalize();

    @Override
    GeometryCollection<G> clone();

    @Override
    default ReadOnlyIterator<G> iterator() {
        return new GeometryCollectionIterator(this, 0);
    }

    @Override
    default Geometry[] toArray() {
        return toArray(new Geometry[size()]);
    }
}
