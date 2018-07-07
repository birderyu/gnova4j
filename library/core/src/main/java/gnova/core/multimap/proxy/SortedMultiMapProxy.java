package gnova.core.multimap.proxy;

import gnova.core.annotation.NotNull;
import gnova.core.multimap.SortedMultiMap;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

/**
 * 使用SortedMap作为代理去实现一个SortedMultiMap
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public abstract class SortedMultiMapProxy<K, V>
        extends MultiMapProxy<K, V> implements SortedMultiMap<K, V> {

    /**
     * 创建一个SortedMultiMapProxy，交由子类实现
     *
     * @param map Map对象
     * @return SortedMultiMapProxy对象，不允许为null
     */
    @NotNull
    protected abstract SortedMultiMapProxy<K, V> buildMultiMap(@NotNull Map<K, Collection<V>> map);

    @Override
    @NotNull
    protected abstract SortedMap<K, Collection<V>> buildEmptyMap();

    @Override
    @NotNull
    protected SortedMap<K, Collection<V>> getMap() {
        return (SortedMap<K, Collection<V>>) super.getMap();
    }

    @Override
    public Comparator<? super K> comparator() {
        return getMap().comparator();
    }

    @Override
    public SortedMultiMap<K, V> subMultiMap(K fromKey, K toKey) {
        return this.buildMultiMap(getMap().subMap(fromKey, toKey));
    }

    @Override
    public SortedMultiMap<K, V> headMultiMap(K toKey) {
        return this.buildMultiMap(getMap().headMap(toKey));
    }

    @Override
    public SortedMultiMap<K, V> tailMultiMap(K fromKey) {
        return this.buildMultiMap(getMap().tailMap(fromKey));
    }

    @Override
    public K firstKey() {
        return getMap().firstKey();
    }

    @Override
    public K lastKey() {
        return getMap().lastKey();
    }

}
