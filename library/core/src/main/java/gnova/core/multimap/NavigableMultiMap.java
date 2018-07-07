package gnova.core.multimap;

import gnova.core.annotation.NotNull;

import java.util.Collection;
import java.util.NavigableSet;

/**
 * 有序的MultiMap
 *
 * NavigableMultiMap是对SortedMultiMap的扩展
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @see MultiMap
 * @see SortedMultiMap
 * @author birderyu
 * @version 1.0.0
 */
public interface NavigableMultiMap<K, V>
        extends SortedMultiMap<K, V> {

    /**
     * 获取排列在指定的键之前的一个键以及该键关联的所有值
     *
     * @param key 键
     * @return 位于指定的键之前的一个键以及该键关联的所有值，若不存在则返回null
     */
    Entry<K, Collection<V>> lowerEntry(K key);

    /**
     * 获取排列在指定的键之前的一个键
     *
     * @param key 键
     * @return 位于指定的键之前的一个键，若不存在则返回null
     */
    K lowerKey(K key);

    /**
     * 获取小于或等于指定的键的一个键以及该键关联的所有值
     *
     * @param key 键
     * @return 小于或等于指定的键的一个键以及该键关联的所有值，若不存在则返回null
     */
    Entry<K, Collection<V>> floorEntry(K key);

    /**
     * 获取小于或等于指定的键的一个键
     *
     * @param key 键
     * @return 小于或等于指定的键的一个键，若不存在则返回null
     */
    K floorKey(K key);

    /**
     * 获取大于或等于指定的键的一个键以及该键关联的所有值
     *
     * @param key 键
     * @return 大于或等于指定的键的一个键以及该键关联的所有值，若不存在则返回null
     */
    Entry<K, Collection<V>> ceilingEntry(K key);

    /**
     * 获取大于或等于指定的键的一个键
     *
     * @param key 键
     * @return 大于或等于指定的键的一个键，若不存在则返回null
     */
    K ceilingKey(K key);

    /**
     * 获取排列在指定的键之后的一个键以及该键关联的所有值
     *
     * @param key 键
     * @return 位于指定的键之后的一个键以及该键关联的所有值，若不存在则返回null
     */
    Entry<K, Collection<V>> higherEntry(K key);

    /**
     * 获取排列在指定的键之后的一个键
     *
     * @param key 键
     * @return 位于指定的键之后的一个键，若不存在则返回null
     */
    K higherKey(K key);

    /**
     * 获取容器的第一个键以及该键关联的所有值
     *
     * @return 第一个键以及该键关联的所有值，若容器为空则返回null
     */
    Entry<K, Collection<V>> firstEntry();

    /**
     * 获取容器的最后一个键以及该键关联的所有值
     *
     * @return 最后一个键以及该键关联的所有值，若容器为空则返回null
     */
    Entry<K, Collection<V>> lastEntry();

    /**
     * 获取并移除容器的第一个键以及该键关联的所有值
     *
     * @return 第一个键以及该键关联的所有值，若容器为空则返回null
     */
    Entry<K, Collection<V>> pollFirstEntry();

    /**
     * 获取并移除容器的最后一个键以及该键关联的所有值
     *
     * @return 最后一个键以及该键关联的所有值，若容器为空则返回null
     */
    Entry<K, Collection<V>> pollLastEntry();

    /**
     * 获取当前容器的一个反向容器
     *
     * @return 容器，该容器不会为null
     */
    @NotNull NavigableMultiMap<K, V> descendingMultiMap();

    /**
     * 获取当前容器所有键的一个正向集合
     *
     * @return 键的集合
     */
    @NotNull NavigableSet<K> navigableKeySet();

    /**
     * 获取当前容器所有键的一个反向集合
     *
     * @return 键的集合
     */
    @NotNull NavigableSet<K> descendingKeySet();

    /**
     * 获取当前容器的一个子容器
     *
     * @param fromKey 起始键，子容器包括该键
     * @param toKey 终止键，子容器不包括该键
     * @return 子容器，若子容器中不包含元素，则返回一个空的容器，不会返回null
     * @see SortedMultiMap#subMultiMap(Object, Object)
     */
    @Override
    @NotNull
    NavigableMultiMap<K, V> subMultiMap(K fromKey, K toKey);

    /**
     * 获取当前容器的一个子容器
     *
     * @param fromKey 起始键
     * @param fromInclusive 子容器中是否包括起始键
     * @param toKey 终止键
     * @param toInclusive 子容器中是否包括终止键
     * @return 子容器，若子容器中不包含元素，则返回一个空的容器，不会返回null
     */
    @NotNull NavigableMultiMap<K,V> subMultiMap(K fromKey, boolean fromInclusive,
                                                K toKey, boolean toInclusive);

    /**
     * 获取当前容器的一个子容器
     *
     * @param toKey 终止键，子容器不包括该键
     * @return 子容器，若子容器中不包含元素，则返回一个空的容器，不会返回null
     * @see SortedMultiMap#headMultiMap(Object)
     */
    @Override
    @NotNull
    NavigableMultiMap<K, V> headMultiMap(K toKey);

    /**
     * 获取当前容器的一个子容器
     *
     * @param toKey 终止键
     * @param inclusive 子容器中是否包括终止键
     * @return 子容器，若子容器中不包含元素，则返回一个空的容器，不会返回null
     */
    @NotNull NavigableMultiMap<K,V> headMultiMap(K toKey, boolean inclusive);

    /**
     * 获取当前容器的一个子容器
     *
     * @param fromKey 起始键，子容器包括该键
     * @return 子容器，若子容器中不包含元素，则返回一个空的容器，不会返回null
     * @see SortedMultiMap#tailMultiMap(Object)
     */
    @Override
    @NotNull
    NavigableMultiMap<K, V> tailMultiMap(K fromKey);

    /**
     * 获取当前容器的一个子容器
     *
     * @param fromKey 起始键
     * @param inclusive 子容器中是否包括起始键
     * @return 子容器，若子容器中不包含元素，则返回一个空的容器，不会返回null
     */
    @NotNull NavigableMultiMap<K,V> tailMultiMap(K fromKey, boolean inclusive);

}
