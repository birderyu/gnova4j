package gnova.geometry.index;

import gnova.core.annotation.NotNull;
import gnova.core.function.ObjectBuilder;

/**
 * 几何索引构造器
 *
 * @param <E> 几何索引中存储的数据元素的类型
 */
@FunctionalInterface
public interface GeometryIndexBuilder<E>
        extends ObjectBuilder<GeometryIndex<E>> {

    /**
     * 构造一个空间索引
     *
     * @return
     */
    @Override
    @NotNull
    GeometryIndex<E> build();

}
