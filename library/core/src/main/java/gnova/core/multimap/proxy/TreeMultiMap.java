package gnova.core.multimap.proxy;

import gnova.core.annotation.NotNull;
import gnova.core.multimap.NavigableMultiMap;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 使用TreeMap实现的NavigableMultiMap
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 */
public abstract class TreeMultiMap<K, V>
        extends NavigableMultiMapProxy<K, V> implements NavigableMultiMap<K, V> {

    /**
     * 构造一个使用TreeMap实现的NavigableMultiMap
     */
    public TreeMultiMap() {
        setMap(new TreeMap<>());
    }

    /**
     * 构造一个使用TreeMap实现的NavigableMultiMap
     *
     * @param comparator 排序器
     */
    public TreeMultiMap(Comparator<? super K> comparator) {
        setMap(new TreeMap<>(comparator));
    }

    @Override
    @NotNull
    protected abstract TreeMultiMap<K, V> buildMultiMap(@NotNull Map<K, Collection<V>> map);

    @Override
    @NotNull
    protected TreeMap<K, Collection<V>> buildEmptyMap() {
        return new TreeMap<>(getMap().comparator());
    }

    /**
     * 构造一个使用TreeMap实现的NavigableMultiMap
     *
     * @param map Map对象，不允许为null
     */
    protected TreeMultiMap(@NotNull Map<K, Collection<V>> map) {
        setMap(map);
    }
}
