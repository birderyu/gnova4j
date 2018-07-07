package gnova.geometry.index.impl.jts;

import gnova.core.annotation.NotNull;
import gnova.geometry.factory.jts.GeometryFactoryAdaptor;
import gnova.geometry.index.GeometryIndexType;
import gnova.geometry.index.GeometryIndex;
import gnova.geometry.model.BoundingBox;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GeometryIndexAdaptor<E>
        implements GeometryIndex<E> {

    private com.vividsolutions.jts.index.SpatialIndex jtsSpatialIndex;
    private GeometryIndexType type;

    public GeometryIndexAdaptor(com.vividsolutions.jts.index.SpatialIndex jtsSpatialIndex) {
        this.jtsSpatialIndex = jtsSpatialIndex;
        if (jtsSpatialIndex instanceof com.vividsolutions.jts.index.quadtree.Quadtree) {
            type = GeometryIndexType.QTree;
        } else if (jtsSpatialIndex instanceof com.vividsolutions.jts.index.strtree.STRtree) {
            type = GeometryIndexType.RTree;
        } else {
            type = GeometryIndexType.Unknown;
        }
    }

    @Override
    public GeometryIndexType getType() {
        return type;
    }

    @Override
    public int size() {
        if (type == GeometryIndexType.QTree) {
            return ((com.vividsolutions.jts.index.quadtree.Quadtree) jtsSpatialIndex).size();
        } else if (type == GeometryIndexType.RTree) {
            return ((com.vividsolutions.jts.index.strtree.STRtree) jtsSpatialIndex).size();
        } else {
            return 0;
        }
    }

    @Override
    public void insert(BoundingBox bbox, E value) {
        jtsSpatialIndex.insert(getJtsEnvelope(bbox), value);
    }

    @Override
    public boolean delete(BoundingBox bbox, E value) {
        return jtsSpatialIndex.remove(getJtsEnvelope(bbox), value);
    }

    @Override
    public List<E> query(BoundingBox bbox) {
        return jtsSpatialIndex.query(getJtsEnvelope(bbox));
    }

    @Override
    public void query(@NotNull BoundingBox bbox, @NotNull Consumer<E> visitor) {
        jtsSpatialIndex.query(getJtsEnvelope(bbox), item -> visitor.accept((E) item));
    }

    @Override
    public void query(BoundingBox bbox, Predicate<E> visitor) {
        try {
            jtsSpatialIndex.query(getJtsEnvelope(bbox), item -> {
                if (!visitor.test((E) item)) {
                    // 停止访问
                    throw new RuntimeException("STOP");
                }
            });
        } catch (RuntimeException ex) {
            return;
        }
    }

    private com.vividsolutions.jts.geom.Envelope getJtsEnvelope(BoundingBox bbox) {
        return GeometryFactoryAdaptor.toJtsEnvelope(bbox);
    }
}
