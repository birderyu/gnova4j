package gnova.geometry.index;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.BoundingBox;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 几何索引
 *
 * @param <E> 几何索引中存储的数据元素的类型
 */
public interface GeometryIndex<E> {

    /**
     * 空间索引的类型
     *
     * @return 空间索引的类型
     */
    GeometryIndexType getType();

    /**
     * 空间索引中数据元素的数量
     *
     * @return 数据元素的数量
     */
    int size();

    /**
     * 几何索引中数据元素的数量是否为空
     *
     * @return 若为空，则返回true，否则返回false
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 插入一条数据
     *
     * @param bbox 包围盒，不允许为null
     * @param value 数据
     */
    void insert(@NotNull BoundingBox bbox, E value);

    /**
     * 移除一条数据
     *
     * @param bbox 包围盒，不允许为null
     * @param value 数据
     * @return 若移除成功，则返回true，否则返回false
     */
    boolean delete(@NotNull BoundingBox bbox, E value);

    /**
     * 根据包围盒查询数据，返回数据的集合
     *
     * @param bbox 包围盒，不允许为null
     * @return 数据的集合
     */
    @NotNull
    Collection<E> query(@NotNull BoundingBox bbox);

    /**
     * 根据包围盒查询数据
     *
     * @param bbox 包围盒
     * @param visitor 数据访问者
     */
    void query(@NotNull BoundingBox bbox, @NotNull Consumer<E> visitor);

    /**
     * 根据包围盒查询数据
     *
     * @param bbox 包围盒
     * @param visitor 数据访问者
     */
    void query(@NotNull BoundingBox bbox, @NotNull Predicate<E> visitor);


}
