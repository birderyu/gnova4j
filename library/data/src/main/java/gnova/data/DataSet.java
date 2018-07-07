package gnova.data;

import gnova.core.annotation.NotNull;
import gnova.core.PredicateIterator;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * 数据的集合
 *
 * 数据集是数据的容器，
 * 每当获取一个数据时，都是该数据在数据集中的引用，若修改了该数据，则数据集中的数据也会被修改
 *
 * @param <E> 数据集中数据元素的类型
 */
public interface DataSet<E>
        extends Iterable<E> {

    /**
     * 数据集中数据的数量
     *
     * @return 数据的数量
     */
    long size();

    /**
     * 数据集是否为空
     *
     * @return 若数据集为空，则返回true，否则返回false
     */
    default boolean isEmpty()  {
        return size() == 0L;
    }

    /**
     * 清空数据集中的所有数据
     */
    void clear();

    /**
     * 获取数据集中的一条数据
     *
     * @param e 数据，不允许为null
     * @return 数据集中的数据，若不存在该数据，则返回null
     */
    E get(@NotNull E e);

    /**
     * 判断数据集中是否包含数据
     *
     * @param e 数据，不允许为null
     * @return 若缓存中包含该数据，则返回true，否则返回false
     */
    boolean contains(@NotNull E e);

    /**
     * 将数据合并进数据集合
     *
     * 若数据不存在，则新增，并且返回null
     * 若数据已经存在，则更新，并返回更新之前的数据
     *
     * @param e 数据，不允许为null
     * @return 若数据集合中已经包含了该数据，则返回更新前的数据，否则返回null
     */
    E put(@NotNull E e);

    /**
     * 移除一个数据
     *
     * @param e 数据
     * @return 若数据移除成功，则返回该数据，否则返回null
     */
    E remove(@NotNull E e);

    /**
     * 移除符合条件的所有数据
     *
     * @param filter 过滤条件
     * @return 移除掉的数据的数量
     */
    long removeIf(@NotNull Predicate<E> filter);

    /**
     * 获取符合条件的所有数据
     *
     * @param filter 过滤条件
     * @return
     */
    default Iterator<E> iterator(Predicate<E> filter) {
        return filter == null ?
                iterator() :
                new PredicateIterator<>(iterator(), filter);
    }

}
