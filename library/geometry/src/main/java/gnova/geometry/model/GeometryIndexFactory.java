package gnova.geometry.model;

import gnova.geometry.index.GeometryIndex;
import gnova.geometry.index.GeometryIndexType;

/**
 * 几何索引工厂
 */
public interface GeometryIndexFactory {

    /**
     * 创建一个几何索引对象
     *
     * @param type 空间索引的类型
     * @param <E> 空间索引中存储的数据元素的类型
     * @return 空间索引对象
     */
    <E> GeometryIndex<E> createGeometryIndex(GeometryIndexType type);

}
