package gnova.data.index;

import gnova.core.function.Getter;
import gnova.data.Indices;
import gnova.geometry.model.FactoryFinder;
import gnova.geometry.index.GeometryIndexType;
import gnova.core.multimap.proxy.HashArrayMultiMap;
import gnova.core.multimap.proxy.TreeArrayMultiMap;

import java.util.HashMap;

public class SimpleIndices<E extends Getter>
        extends AbstractIndices<E> implements Indices<E> {

    public SimpleIndices() {
        super(HashMap::new, HashMap::new, HashArrayMultiMap::new,
                (ordered, fieldNames) -> new AbstractGeneralIndex<E>(
                        ordered ? TreeArrayMultiMap::new : HashArrayMultiMap::new,
                        ordered,
                        fieldNames) {},
                (ordered, fieldNames) -> new AbstractUniqueIndex<E>(
                        ordered ? TreeArrayMultiMap::new : HashArrayMultiMap::new,
                        ordered,
                        fieldNames) {},
                (fieldName) -> new AbstractSpatialIndex<E>(
                        () -> FactoryFinder.getDefaultGeometryIndexFactory().createGeometryIndex(GeometryIndexType.RTree),
                        fieldName) {});
    }
}