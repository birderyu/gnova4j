package gnova.multimap;

import gnova.annotation.NotNull;
import gnova.util.ReadOnlyCollection;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * 键可以重复的关联容器
 *
 * <p><tt>MultiMap</tt>是一种存储键值对的关联容器，它的方法与{@link java.util.Map}基本一致，
 * 与之相比，它最大的特点是键是可以重复的，即一个键可以对应多个值。
 *
 * <p>接口{@link MultiMap.Entry}表示MultiMap中的一个键值对，它是<tt>MultiMap</tt>的一个内部接口，
 * 通过{@link MultiMap#entrySet()}方法，可以获得一个键值对的集合。
 *
 * <p>同时，<tt>MultiMap</tt>继承自{@link java.lang.Iterable}接口，且元素类型为{@link MultiMap.Entry}，
 * 这意味着可以通过{@link java.lang.Iterable#iterator()}方法直接获取键值对的迭代器，也可以使用
 * 增强的for循环遍历所有<tt>MultiMap</tt>中的键值对。
 *
 * <p><tt>MultiMap</tt>提供了三个视图：通过{@link MultiMap#keySet()}获取所有的键视图，重复的键在此视图中只会显示一次；
 * 通过{@link MultiMap#values()}获取所有的值视图；通过{@link MultiMap#entrySet()}获取所有的键值对视图；
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 * @author birderyu
 * @version 1.0.0
 */
public interface MultiMap<K, V> extends Iterable<MultiMap.Entry<K, V>> {

    /**
     * 获取值的总数
     *
     * @return 值的总数
     */
    int size();

    /**
     * 判断容器是否为空
     *
     * @return 若容器为空，则返回true，否则为false
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 判断是否包含键以及关联该键的值
     *
     * @param key 键
     * @param value 值
     * @return 若包含该键，并且包含关联该键的值，则返回true，否则返回false
     */
    boolean contains(K key, V value);

    /**
     * 判断是否包含键
     *
     * @param key 键
     * @return 若包含该键，则返回true，否则返回false
     */
    boolean containsKey(K key);

    /**
     * 判断是否包含值
     *
     * @param value 值
     * @return 若包含该值，则返回true，否则返回false
     */
    boolean containsValue(V value);

    /**
     * 获取键所关联的值的集合
     *
     * @param key 键
     * @return 该键所关联的值的集合，若不存在该键，则返回null
     */
    Collection<V> get(K key);

    /**
     * 将一个键值对放入容器
     *
     * @param key 键
     * @param value 值
     * @return 若成功，则返回true，否则返回false
     */
    boolean put(K key, V value);

    /**
     * 从容器中移除一个键，返回键所关联的值的集合
     *
     * @param key 键
     * @return 返回该键所关联的所有值，若键不存在，则返回null
     */
    Collection<V> remove(K key);

    /**
     * 移除键下面关联的一个值
     *
     * @param key 键
     * @param value 值
     * @return 若移除成功，则返回true，否则返回false
     */
    boolean remove(K key, V value);

    /**
     * 批量放置键值对
     *
     * @param m 容器
     */
    void putAll(Map<? extends K, ? extends V> m);

    /**
     * 批量放置键值对
     *
     * @param mm 容器
     */
    void putAll(MultiMap<? extends K, ? extends V> mm);

    /**
     * 清空键值对
     */
    void clear();

    /**
     * 返回键的集合
     * 重复的键只会返回一次
     *
     * @return 键的集合，若集合为空，则返回一个空的集合，不会返回null
     */
    @NotNull
    Set<K> keySet();

    /**
     * 返回所有值的容器
     * 这个集合将是一个只读容器{@link ReadOnlyCollection ReadOnlyCollection}，
     * 任何在该容器上的写操作，都将抛出UnsupportedOperationException异常
     *
     * @return 值的容器，若容器为空，则返回一个空的容器，不会返回null
     */
    @NotNull
    ReadOnlyCollection<V> values();

    /**
     * 返回所有键值对的集合
     *
     * @return 键值对的集合，若集合为空，则返回一个空的集合，不会返回null
     */
    @NotNull Set<Entry<K, V>> entrySet();

    /**
     * MultiMap中的一个键值对
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     */
    interface Entry<K, V> {

        /**
         * 获取键
         *
         * @return 键对象
         * @throws IllegalStateException 若当前MultiMap已经不包含当前的键值对，则抛出此异常
         */
        K getKey() throws IllegalStateException;

        /**
         * 获取值
         *
         * @return 值对象
         * @throws IllegalStateException 若当前MultiMap已经不包含当前的键值对，则抛出此异常
         */
        V getValue() throws IllegalStateException;

        /**
         * 判断两个对象是否相等
         *
         * @param o 对象
         * @return 若对象相等，则返回true，否则返回false
         * @see Object#equals(Object)
         */
        @Override
        boolean equals(Object o);

        /**
         * 获取当前对象的散列值
         *
         * @return 散列值
         * @see Object#hashCode()
         */
        @Override
        int hashCode();

        /**
         * 转换为字符串
         *
         * @return 字符串
         * @see Object#toString()
         */
        @Override
        @NotNull
        String toString();

        static <K extends Comparable<? super K>, V> Comparator<Entry<K, V>> comparingByKey() {
            return Comparator.comparing(Entry::getKey);
        }

        static <K, V extends Comparable<? super V>> Comparator<Entry<K,V>> comparingByValue() {
            return Comparator.comparing(Entry::getValue);
        }

        static <K, V> Comparator<Entry<K, V>> comparingByKey(@NotNull Comparator<? super K> cmp) {
            return (c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
        }

        static <K, V> Comparator<Entry<K, V>> comparingByValue(@NotNull Comparator<? super V> cmp) {
            return (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
        }

    }

    /**
     * 获取当前对象的散列值
     *
     * @return 散列值
     * @see Object#hashCode()
     */
    @Override
    int hashCode();

    /**
     * 判断两个对象是否相等
     *
     * @param o 对象
     * @return 若对象相等，则返回true，否则返回false
     * @see Object#equals(Object)
     */
    @Override
    boolean equals(Object o);

    /**
     * 转换为字符串
     *
     * @return 字符串
     * @see Object#toString()
     */
    @Override
    @NotNull
    String toString();

    /**
     * 获取键值对的迭代器
     *
     * @return 键值对的迭代器，不会返回null
     * @see java.lang.Iterable#iterator()
     * @see java.util.Iterator
     */
    @Override
    @NotNull
    default Iterator<MultiMap.Entry<K, V>> iterator() {
        return entrySet().iterator();
    }

    /**
     * 遍历MultiMap中的所有元素
     *
     * @param action 访问者，不允许为null
     */
    default void forEach(@NotNull BiConsumer<? super K, ? super V> action) {
        for (Entry<K, V> entry : this) {
            try {
                action.accept(entry.getKey(), entry.getValue());
            } catch(IllegalStateException ise) {
                throw new ConcurrentModificationException(ise);
            }
        }
    }
}
