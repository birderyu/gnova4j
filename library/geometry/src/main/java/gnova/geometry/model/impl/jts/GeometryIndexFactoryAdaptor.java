package gnova.geometry.model.impl.jts;

import gnova.geometry.model.GeometryIndexFactory;
import gnova.geometry.index.GeometryIndex;
import gnova.geometry.index.GeometryIndexType;
import gnova.geometry.index.impl.jts.GeometryIndexAdaptor;

public final class GeometryIndexFactoryAdaptor
        implements GeometryIndexFactory {

    @Override
    public <E> GeometryIndex<E> createGeometryIndex(GeometryIndexType type) {
        switch (type) {
            case Unknown:
                throw new IllegalArgumentException("不支持的索引类型：" + type);
            case QTree:
                return new GeometryIndexAdaptor<>(new com.vividsolutions.jts.index.quadtree.Quadtree());
            case RTree:
                return new GeometryIndexAdaptor<>(new com.vividsolutions.jts.index.strtree.STRtree());
        }
        throw new IllegalArgumentException("不支持的索引类型：" + type);
    }

}
