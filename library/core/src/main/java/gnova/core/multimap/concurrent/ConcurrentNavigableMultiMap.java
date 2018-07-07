package gnova.core.multimap.concurrent;

import gnova.core.annotation.NotNull;
import gnova.core.annotation.ThreadSafe;
import gnova.core.multimap.MultiMap;
import gnova.core.multimap.SortedMultiMap;
import gnova.core.multimap.NavigableMultiMap;

/**
 * 支持并发操作的有序MultiMap有序MultiMap
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 * @see MultiMap
 * @see SortedMultiMap
 * @see NavigableMultiMap
 * @see ConcurrentMultiMap
 * @author birderyu
 * @version 1.0.0
 */
@ThreadSafe
public interface ConcurrentNavigableMultiMap<K, V>
        extends ConcurrentMultiMap<K, V>, NavigableMultiMap<K, V> {

    /**
     * 获取当前容器的一个子容器
     *
     * @param fromKey 起始键，子容器包括该键
     * @param toKey 终止键，子容器不包括该键
     * @return 支持并发操作的子容器，若子容器中不包含元素，则返回一个空的容器，不会返回null
     * @see SortedMultiMap#subMultiMap(Object, Object)
     */
    @Override
    @NotNull
    ConcurrentNavigableMultiMap<K, V> subMultiMap(K fromKey, K toKey);

    /**
     * 获取当前容器的一个子容器
     *
     * @param fromKey 起始键
     * @param fromInclusive 子容器中是否包括起始键
     * @param toKey 终止键
     * @param toInclusive 子容器中是否包括终止键
     * @return 支持并发操作的子容器，若子容器中不包含元素，则返回一个空的容器，不会返回null
     * @see NavigableMultiMap#subMultiMap(Object, boolean, Object, boolean)
     */
    @Override
    @NotNull
    ConcurrentNavigableMultiMap<K, V> subMultiMap(K fromKey, boolean fromInclusive,
                                                  K toKey, boolean toInclusive);
    /**
     * 获取当前容器的一个子容器
     *
     * @param toKey 终止键，子容器不包括该键
     * @return 支持并发操作的子容器，若子容器中不包含元素，则返回一个空的容器，不会返回null
     * @see SortedMultiMap#headMultiMap(Object)
     */
    @Override
    @NotNull
    ConcurrentNavigableMultiMap<K, V> headMultiMap(K toKey);

    /**
     * 获取当前容器的一个子容器
     *
     * @param toKey 终止键
     * @param inclusive 子容器中是否包括终止键
     * @return 支持并发操作的子容器，若子容器中不包含元素，则返回一个空的容器，不会返回null
     * @see NavigableMultiMap#headMultiMap(Object, boolean)
     */
    @Override
    @NotNull
    ConcurrentNavigableMultiMap<K, V> headMultiMap(K toKey, boolean inclusive);

    /**
     * 获取当前容器的一个子容器
     *
     * @param fromKey 起始键，子容器包括该键
     * @return 支持并发操作的子容器，若子容器中不包含元素，则返回一个空的容器，不会返回null
     * @see SortedMultiMap#tailMultiMap(Object)
     */
    @Override
    @NotNull
    ConcurrentNavigableMultiMap<K, V> tailMultiMap(K fromKey);

    /**
     * 获取当前容器的一个子容器
     *
     * @param fromKey 起始键
     * @param inclusive 子容器中是否包括起始键
     * @return 支持并发操作的子容器，若子容器中不包含元素，则返回一个空的容器，不会返回null
     * @see NavigableMultiMap#tailMultiMap(Object, boolean)
     */
    @Override
    @NotNull
    ConcurrentNavigableMultiMap<K, V> tailMultiMap(K fromKey, boolean inclusive);

    /**
     * 获取当前容器的一个反向容器
     *
     * @return 支持并发操作的容器，该容器不会为null
     * @see NavigableMultiMap#descendingMultiMap()
     */
    @Override
    @NotNull
    ConcurrentNavigableMultiMap<K, V> descendingMultiMap();

}
