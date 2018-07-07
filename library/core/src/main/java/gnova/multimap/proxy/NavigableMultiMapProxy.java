package gnova.multimap.proxy;

import gnova.annotation.NotNull;
import gnova.multimap.NavigableMultiMap;

import java.util.Collection;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;

/**
 * 使用NavigableMap作为代理去实现一个NavigableMultiMapProxy
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public abstract class NavigableMultiMapProxy<K, V>
        extends SortedMultiMapProxy<K, V> implements NavigableMultiMap<K, V> {

    @Override
    @NotNull
    protected abstract NavigableMultiMapProxy<K, V> buildMultiMap(@NotNull Map<K, Collection<V>> map);

    @Override
    @NotNull
    protected abstract NavigableMap<K, Collection<V>> buildEmptyMap();

    @Override
    @NotNull
    protected NavigableMap<K, Collection<V>> getMap() {
        return (NavigableMap<K, Collection<V>>) super.getMap();
    }

    @Override
    public Entry<K, Collection<V>> lowerEntry(K key) {
        Map.Entry<K, Collection<V>> e = getMap().lowerEntry(key);
        if (e == null) {
            return null;
        }
        return new SimpleEntry(e.getKey(), e.getValue());
    }

    @Override
    public K lowerKey(K key) {
        return getMap().lowerKey(key);
    }

    @Override
    public Entry<K, Collection<V>> floorEntry(K key) {
        Map.Entry<K, Collection<V>> e = getMap().floorEntry(key);
        if (e == null) {
            return null;
        }
        return new SimpleEntry(e.getKey(), e.getValue());
    }

    @Override
    public K floorKey(K key) {
        return getMap().floorKey(key);
    }

    @Override
    public Entry<K, Collection<V>> ceilingEntry(K key) {
        Map.Entry<K, Collection<V>> e = getMap().ceilingEntry(key);
        if (e == null) {
            return null;
        }
        return new SimpleEntry(e.getKey(), e.getValue());
    }

    @Override
    public K ceilingKey(K key) {
        return getMap().ceilingKey(key);
    }

    @Override
    public Entry<K, Collection<V>> higherEntry(K key) {
        Map.Entry<K, Collection<V>> e = getMap().higherEntry(key);
        if (e == null) {
            return null;
        }
        return new SimpleEntry(e.getKey(), e.getValue());
    }

    @Override
    public K higherKey(K key) {
        return getMap().higherKey(key);
    }

    @Override
    public Entry<K, Collection<V>> firstEntry() {
        Map.Entry<K, Collection<V>> e = getMap().firstEntry();
        if (e == null) {
            return null;
        }
        return new SimpleEntry(e.getKey(), e.getValue());
    }

    @Override
    public Entry<K, Collection<V>> lastEntry() {
        Map.Entry<K, Collection<V>> e = getMap().lastEntry();
        if (e == null) {
            return null;
        }
        return new SimpleEntry(e.getKey(), e.getValue());
    }

    @Override
    public Entry<K, Collection<V>> pollFirstEntry() {
        Map.Entry<K, Collection<V>> e = getMap().pollFirstEntry();
        if (e == null) {
            return null;
        }
        return new SimpleEntry(e.getKey(), e.getValue());
    }

    @Override
    public Entry<K, Collection<V>> pollLastEntry() {
        Map.Entry<K, Collection<V>> e = getMap().pollLastEntry();
        if (e == null) {
            return null;
        }
        return new SimpleEntry(e.getKey(), e.getValue());
    }

    @Override
    public NavigableMultiMap<K, V> descendingMultiMap() {
        return this.buildMultiMap(getMap().descendingMap());
    }

    @Override
    public NavigableSet<K> navigableKeySet() {
        return getMap().navigableKeySet();
    }

    @Override
    public NavigableSet<K> descendingKeySet() {
        return getMap().descendingKeySet();
    }

    @Override
    public NavigableMultiMap<K, V> subMultiMap(K fromKey, K toKey) {
        return this.buildMultiMap(getMap().subMap(fromKey, toKey));
    }

    @Override
    public NavigableMultiMap<K, V> subMultiMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        return this.buildMultiMap(getMap().subMap(fromKey, fromInclusive, toKey, toInclusive));
    }

    @Override
    public NavigableMultiMap<K, V> headMultiMap(K toKey) {
        return this.buildMultiMap(getMap().headMap(toKey));
    }

    @Override
    public NavigableMultiMap<K, V> headMultiMap(K toKey, boolean inclusive) {
        return this.buildMultiMap(getMap().headMap(toKey, inclusive));
    }

    @Override
    public NavigableMultiMap<K, V> tailMultiMap(K fromKey) {
        return this.buildMultiMap(getMap().tailMap(fromKey));
    }

    @Override
    public NavigableMultiMap<K, V> tailMultiMap(K fromKey, boolean inclusive) {
        return this.buildMultiMap(getMap().tailMap(fromKey, inclusive));
    }

}
