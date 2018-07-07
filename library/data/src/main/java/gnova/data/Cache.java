package gnova.data;

import gnova.annotation.NotNull;

import java.util.Iterator;

/**
 * 缓存
 *
 * @param <E> 缓存中数据元素的类型
 */
public interface Cache<E>
        extends Iterable<E>, Editable<E>, Indexable<E> {

    /**
     * 缓存中数据的数量
     *
     * @return 数据的数量
     */
    long size();

    /**
     * 缓存是否为空
     *
     * @return 若缓存为空，则返回true，否则返回false
     */
    default boolean isEmpty() {
        return size() == 0L;
    }

    /**
     * 清空缓存和索引中所有的数据，但保留索引的结构
     *
     * 如数据中包含了若干数据，并且对其中的某些字段构建了索引，那么在调用此方法之后只会将数据清除，索引将会保留，
     * 当再次插入数据时，依然会针对新增的数据维护这些字段的索引
     */
    void clear();

    /**
     * 清空索引和缓存
     */
    void drop();

    /**
     * 获取缓存中的一条数据
     *
     * 查询时，通过数据本身或数据的主键进行查询，这取决于具体的数据类型
     *
     * @param key 数据的主键，不允许为null
     * @return 缓存中的数据，若不存在该数据，则返回null
     */
    E get(@NotNull Object key);

    /**
     * 判断缓存中是否包含数据
     *
     * 查询时，通过数据本身或数据的主键进行查询，这取决于具体的数据类型
     *
     * @param key 数据的主键，不允许为null
     * @return 若缓存中包含该数据，则返回true，否则返回false
     */
    boolean contains(@NotNull Object key);

    /**
     * 从缓存中查询数据
     *
     * 过滤条件中包含占位符
     *
     * @param selection 查询过滤器，可以为null，且若为空或null，则表示全查询
     * @param params 替换selection中的占位符，必须与selection中的占位符一一对应
     * @return
     * @throws IllegalArgumentException 若表达式不合法，则抛出此异常
     */
    Iterator<E> query(String selection, Object[] params) throws IllegalArgumentException;

}
