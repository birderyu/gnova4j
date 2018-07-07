package gnova.multimap.proxy;

import gnova.annotation.NotNull;
import gnova.multimap.MultiMap;

import java.util.Collection;
import java.util.HashMap;

/**
 * 使用HashMap作为代理去实现一个MultiMap
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 * @see MultiMapProxy
 * @see MultiMap
 * @author birderyu
 * @version 1.0.0
 */
public abstract class HashMultiMapProxy<K, V>
        extends MultiMapProxy<K, V> implements MultiMap<K, V> {

    /**
     * 构建一个使用HashMap作为代理实现的MultiMap对象
     */
    public HashMultiMapProxy() {
        setMap(new HashMap<>());
    }

    /**
     * 构建一个使用HashMap作为代理实现的MultiMap对象
     *
     * @param initialCapacity 初始容量
     */
    public HashMultiMapProxy(int initialCapacity) {
        setMap(new HashMap<>(initialCapacity));
    }

    /**
     * 构建一个使用HashMap作为代理实现的MultiMap对象
     *
     * @param initialCapacity 初始容量
     * @param loadFactor 加载阈值
     */
    public HashMultiMapProxy(int initialCapacity, float loadFactor) {
        setMap(new HashMap<>(initialCapacity, loadFactor));
    }

    /**
     * 创建一个空的HashMap，交由子类实现
     *
     * @return HashMap对象，不会返回null
     * @see MultiMapProxy#buildEmptyMap()
     */
    @Override
    @NotNull
    protected HashMap<K, Collection<V>> buildEmptyMap() {
        return new HashMap<>();
    }
}
