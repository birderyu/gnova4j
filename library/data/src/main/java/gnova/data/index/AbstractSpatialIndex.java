package gnova.data.index;

import gnova.core.annotation.NotNull;
import gnova.geometry.index.GeometryIndex;
import gnova.geometry.index.GeometryIndexBuilder;
import gnova.geometry.model.BoundingBox;
import gnova.geometry.model.Geometry;
import gnova.query.LogicalExpression;
import gnova.query.SimpleExpression;
import gnova.core.EmptyIterator;
import gnova.core.function.Getter;
import gnova.core.PredicateIterator;

import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractSpatialIndex<E extends Getter>
        extends AbstractIndex<E> implements SpatialIndex<E> {

    private final GeometryIndexBuilder<E> gib;
    private GeometryIndex<E> geometryIndex;

    public AbstractSpatialIndex(@NotNull GeometryIndexBuilder<E> gib, @NotNull String fieldName) {
        super(fieldName);
        this.gib = gib;
    }

    @NotNull
    protected GeometryIndex<E> buildSpatialIndex() {
        return gib.build();
    }

    /**
     * 获取包围盒
     *
     * @param e 数据，不允许为null
     * @return 包围盒，若该数据无空间字段，则返回null
     */
    @NotNull
    private BoundingBox getBoundingBox(@NotNull E e) {
        Geometry geometry = e.getValue(getFieldName());
        if (geometry == null) {
            return null;
        }
        return geometry.getBoundingBox();
    }

    @Override
    public String getFieldName() {
        return fieldNames[0];
    }

    @Override
    public boolean isSingle() {
        // 空间索引一定是一个单字段索引
        return true;
    }

    @Override
    public boolean isUnique() {
        // 空间索引一定不会是一个唯一索引
        return false;
    }

    @Override
    public boolean isSpatial() {
        return true;
    }

    @Override
    public SingleIndex<E> asSingle() {
        return this;
    }

    @Override
    public MultiIndex<E> asMulti() {
        return null;
    }

    @Override
    public void clear() {
        geometryIndex = null;
    }

    @Override
    public void insert(E e) {
        if (geometryIndex == null) {
            geometryIndex = buildSpatialIndex();
        }
        BoundingBox bbox = getBoundingBox(e);
        if (bbox == null) {
            return;
        }
        geometryIndex.insert(bbox, e);
    }

    @Override
    public void delete(E e) {
        if (geometryIndex == null) {
            return;
        }
        BoundingBox bbox = getBoundingBox(e);
        if (bbox == null) {
            return;
        }
        geometryIndex.delete(bbox, e);
    }

    @Override
    public Iterator<E> search(Object... values) {

        if (geometryIndex == null || geometryIndex.isEmpty()) {
            // 索引还没有构建起来
            return new EmptyIterator<>();
        }

        Geometry geometry = (Geometry) values[0];
        Collection<E> es = geometryIndex.query(geometry.getBoundingBox());

        // 将第一步得到的结果集，进行空间进一步筛选
        return new PredicateIterator<>(es.iterator(),
                val -> {
                    Geometry _geometry = val.getValue(getFieldName());
                    return geometry.equals(_geometry);
                });
    }

    @Override
    public Iterator<E> query(LogicalExpression expression) {

        if (geometryIndex == null || geometryIndex.isEmpty()) {
            // 索引还没有构建起来
            return new EmptyIterator<>();
        }

        // 进行空间初步筛选，得到初步筛选的结果集
        SimpleExpression keyValue = expression.asSimple();
        Geometry geometry = keyValue.getRightValue().asGeometry();
        Collection<E> es = geometryIndex.query(geometry.getBoundingBox());

        // 将第一步得到的结果集，进行空间进一步筛选
        return new PredicateIterator<>(es.iterator(),
                val -> expression.fit(val));
    }
}
