package gnova.core.multimap.concurrent.proxy;

import gnova.core.annotation.NotNull;
import gnova.core.multimap.concurrent.ConcurrentNavigableMultiMap;

import java.util.*;

public abstract class ConcurrentNavigableMultiMapProxy<K, V>
        extends ConcurrentMultiMapProxy<K, V>
        implements ConcurrentNavigableMultiMap<K, V> {

    protected abstract ConcurrentNavigableMultiMapProxy<K, V> buildMultiMap(@NotNull Map<K, Collection<V>> map);

    @Override
    protected abstract NavigableMap<K, Collection<V>> buildEmptyMap();

    @Override
    protected NavigableMap<K, Collection<V>> getMap() {
        return (NavigableMap<K, Collection<V>>) super.getMap();
    }

    @Override
    public Comparator<? super K> comparator() {
        return getMap().comparator();
    }

    @Override
    public ConcurrentNavigableMultiMap<K, V> subMultiMap(K fromKey, K toKey) {
        synchronized (this) {
            return this.buildMultiMap(getMap().subMap(fromKey, toKey));
        }
    }

    @Override
    public ConcurrentNavigableMultiMap<K, V> subMultiMap(K fromKey, boolean fromInclusive,
                                                         K toKey, boolean toInclusive) {
        synchronized (this) {
            return this.buildMultiMap(getMap().subMap(fromKey, fromInclusive, toKey, toInclusive));
        }
    }

    @Override
    public ConcurrentNavigableMultiMap<K, V> headMultiMap(K toKey) {
        synchronized (this) {
            return this.buildMultiMap(getMap().headMap(toKey));
        }
    }

    @Override
    public ConcurrentNavigableMultiMap<K, V> headMultiMap(K toKey, boolean inclusive) {
        synchronized (this) {
            return this.buildMultiMap(getMap().headMap(toKey, inclusive));
        }
    }

    @Override
    public ConcurrentNavigableMultiMap<K, V> tailMultiMap(K fromKey) {
        synchronized (this) {
            return this.buildMultiMap(getMap().tailMap(fromKey));
        }
    }

    @Override
    public K firstKey() {
        synchronized (this) {
            return getMap().firstKey();
        }
    }

    @Override
    public K lastKey() {
        synchronized (this) {
            return getMap().lastKey();
        }
    }

    @Override
    public ConcurrentNavigableMultiMap<K, V> tailMultiMap(K fromKey, boolean inclusive) {
        synchronized (this) {
            return this.buildMultiMap(getMap().tailMap(fromKey, inclusive));
        }
    }

    @Override
    public Entry<K, Collection<V>> lowerEntry(K key) {
        synchronized (this) {
            Map.Entry<K, Collection<V>> e = getMap().lowerEntry(key);
            return new SimpleEntry(e.getKey(), e.getValue());
        }
    }

    @Override
    public K lowerKey(K key) {
        synchronized (this) {
            return getMap().lowerKey(key);
        }
    }

    @Override
    public Entry<K, Collection<V>> floorEntry(K key) {
        synchronized (this) {
            Map.Entry<K, Collection<V>> e = getMap().floorEntry(key);
            return new SimpleEntry(e.getKey(), e.getValue());
        }
    }

    @Override
    public K floorKey(K key) {
        synchronized (this) {
            return getMap().floorKey(key);
        }
    }

    @Override
    public Entry<K, Collection<V>> ceilingEntry(K key) {
        synchronized (this) {
            Map.Entry<K, Collection<V>> e = getMap().ceilingEntry(key);
            return new SimpleEntry(e.getKey(), e.getValue());
        }
    }

    @Override
    public K ceilingKey(K key) {
        synchronized (this) {
            return getMap().ceilingKey(key);
        }
    }

    @Override
    public Entry<K, Collection<V>> higherEntry(K key) {
        synchronized (this) {
            Map.Entry<K, Collection<V>> e = getMap().higherEntry(key);
            return new SimpleEntry(e.getKey(), e.getValue());
        }
    }

    @Override
    public K higherKey(K key) {
        synchronized (this) {
            return getMap().higherKey(key);
        }
    }

    @Override
    public Entry<K, Collection<V>> firstEntry() {
        synchronized (this) {
            Map.Entry<K, Collection<V>> e = getMap().firstEntry();
            return new SimpleEntry(e.getKey(), e.getValue());
        }
    }

    @Override
    public Entry<K, Collection<V>> lastEntry() {
        synchronized (this) {
            Map.Entry<K, Collection<V>> e = getMap().lastEntry();
            return new SimpleEntry(e.getKey(), e.getValue());
        }
    }

    @Override
    public Entry<K, Collection<V>> pollFirstEntry() {
        synchronized (this) {
            Map.Entry<K, Collection<V>> e = getMap().pollFirstEntry();
            return new SimpleEntry(e.getKey(), e.getValue());
        }
    }

    @Override
    public Entry<K, Collection<V>> pollLastEntry() {
        synchronized (this) {
            Map.Entry<K, Collection<V>> e = getMap().pollLastEntry();
            return new SimpleEntry(e.getKey(), e.getValue());
        }
    }

    @Override
    public ConcurrentNavigableMultiMap<K, V> descendingMultiMap() {
        synchronized (this) {
            return this.buildMultiMap(getMap().descendingMap());
        }
    }

    @Override
    public NavigableSet<K> navigableKeySet() {
        synchronized (this) {
            return getMap().navigableKeySet();
        }
    }

    @Override
    public NavigableSet<K> descendingKeySet() {
        synchronized (this) {
            return getMap().descendingKeySet();
        }
    }
}
