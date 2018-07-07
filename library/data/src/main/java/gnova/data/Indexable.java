package gnova.data;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;
import gnova.data.index.IndexException;

import java.util.Iterator;

/**
 * 可构建索引的
 *
 * @param <E> 数据元素的类型
 */
public interface Indexable<E> {

    /**
     * 根据索引的名称获取索引
     *
     * @param name 索引的名称
     * @return 索引对象，若无该索引，则返回null
     */
    Index<E> getIndex(@NotNull String name);

    /**
     * 获取所有的索引对象
     *
     * @return 索引对象的迭代器
     */
    @NotNull
    Iterator<Index<E>> getIndices();

    /**
     * 构建普通字段索引
     *
     * @param name 索引的名字，不允许为null，也不可以与现有的索引名重复
     * @param fieldNames 建立索引的字段名，不允许为null，也不允许为空
     * @param ordered 是否是有序索引
     * @throws IndexException 若索引构建失败，则抛出异常
     */
    void buildIndex(@NotNull String name,
                    @Checked String[] fieldNames,
                    boolean ordered) throws IndexException;

    /**
     * 构建普通字段唯一约束索引
     *
     * @param name 索引的名字，不允许为null，也不可以与现有的索引名重复
     * @param fieldNames 建立索引的字段名，不允许为null，也不允许为空
     * @param ordered 是否是有序索引
     * @throws IndexException 若索引构建失败，则抛出异常
     */
    void buildUniqueIndex(@NotNull String name,
                          @Checked String[] fieldNames,
                          boolean ordered) throws IndexException;

    /**
     * 构建空间字段索引
     *
     * @param name 索引的名字，不允许为null，也不可以与现有的索引名重复
     * @param fieldName 建立空间索引的字段名，不允许为null
     * @throws IndexException 若索引构建失败，则抛出异常
     */
    void buildSpatialIndex(@NotNull String name,
                           @NotNull String fieldName) throws IndexException;

    /**
     * 删除一个索引
     *
     * @param name 索引的名字，不允许为null
     */
    void dropIndex(@NotNull String name);

    /**
     * 删除所有的索引
     */
    void dropIndices();

}
