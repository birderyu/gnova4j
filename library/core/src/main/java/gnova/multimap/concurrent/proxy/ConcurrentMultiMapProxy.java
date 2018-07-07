package gnova.multimap.concurrent.proxy;

import gnova.annotation.NotNull;
import gnova.multimap.MultiMap;
import gnova.multimap.concurrent.ConcurrentMultiMap;
import gnova.multimap.proxy.MultiMapProxy;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class ConcurrentMultiMapProxy<K, V>
    extends MultiMapProxy<K, V> implements ConcurrentMultiMap<K, V> {

    @Override
    protected void setMap(@NotNull Map<K, Collection<V>> map) {
        synchronized (this) {
            super.setMap(map);
        }
    }

    @Override
    public boolean contains(K key, V value) {
        synchronized (this) {
            return super.contains(key, value);
        }
    }

    @Override
    public boolean containsValue(V value) {
        synchronized (this) {
            return super.containsValue(value);
        }
    }

    @Override
    public Collection<V> get(K key) {
        synchronized (this) {
            return super.get(key);
        }
    }

    @Override
    public boolean put(K key, V value) {
        synchronized (this) {
            return super.put(key, value);
        }
    }

    @Override
    public Collection<V> remove(K key) {
        synchronized (this) {
            return super.remove(key);
        }
    }

    @Override
    public boolean remove(K key, V value) {
        synchronized (this) {
            return super.remove(key, value);
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        synchronized (this) {
            super.putAll(m);
        }
    }

    @Override
    public void putAll(MultiMap<? extends K, ? extends V> mm) {
        synchronized (this) {
            super.putAll(mm);
        }
    }

    @Override
    public void clear() {
        synchronized (this) {
            super.clear();
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        synchronized (this) {
            return super.entrySet();
        }
    }

    @Override
    public boolean equals(Object o) {
        synchronized (this) {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        synchronized (this) {
            return super.hashCode();
        }
    }

    @Override
    public String toString() {
        synchronized (this) {
            return super.toString();
        }
    }

}
