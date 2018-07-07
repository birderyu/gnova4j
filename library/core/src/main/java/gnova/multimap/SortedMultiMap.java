package gnova.multimap;

import gnova.annotation.NotNull;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * 有序的MultiMap
 *
 * 有序的MultiMap是一种特殊的MultiMap，它的键必须是可以排序的
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @see MultiMap
 * @author birderyu
 * @version 1.0.0
 */
public interface SortedMultiMap<K, V>
        extends MultiMap<K, V> {

    /**
     * 获取键的排序器
     *
     * 通过重写该方法，指定不同的排序器
     * 若不指定键的排序器，可以令该方法返回null，此时键本身应该是可以排序的
     *
     * @return 排序器
     */
    Comparator<? super K> comparator();

    /**
     * 获取当前容器的一个子容器
     *
     * @param fromKey 起始键，子容器包括该键
     * @param toKey 终止键，子容器不包括该键
     * @return 子容器，若子容器中不包含元素，则返回一个空的容器，不会返回null
     */
    @NotNull SortedMultiMap<K, V> subMultiMap(K fromKey, K toKey);

    /**
     * 获取当前容器的一个子容器
     *
     * @param toKey 终止键，子容器不包括该键
     * @return 子容器，若子容器中不包含元素，则返回一个空的容器，不会返回null
     */
    @NotNull SortedMultiMap<K, V> headMultiMap(K toKey);

    /**
     * 获取当前容器的一个子容器
     *
     * @param fromKey 起始键，子容器包括该键
     * @return 子容器，若子容器中不包含元素，则返回一个空的容器，不会返回null
     */
    @NotNull SortedMultiMap<K, V> tailMultiMap(K fromKey);

    /**
     * 获取当前容器的第一个键
     *
     * @return 键
     * @throws NoSuchElementException 若容器为空，则抛出此异常
     */
    K firstKey();

    /**
     * 获取当前容器的最后一个键
     *
     * @return 键
     * @throws NoSuchElementException 若容器为空，则抛出此异常
     */
    K lastKey();

}
