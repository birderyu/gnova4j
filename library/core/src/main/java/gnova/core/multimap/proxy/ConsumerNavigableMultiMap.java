package gnova.core.multimap.proxy;

import gnova.core.annotation.NotNull;
import gnova.core.function.CollectionBuilder;
import gnova.core.function.NavigableMapBuilder;
import gnova.core.multimap.NavigableMultiMap;

import java.util.Collection;
import java.util.Map;
import java.util.NavigableMap;

public class ConsumerNavigableMultiMap<K, V>
        extends NavigableMultiMapProxy<K, V> implements NavigableMultiMap<K, V> {

    /**
     * NavigableMap构造器
     */
    private final NavigableMapBuilder<K, Collection<V>> nmb;

    /**
     * 集合构造器
     */
    private final CollectionBuilder<V, Collection<V>> cb;

    public ConsumerNavigableMultiMap(@NotNull NavigableMapBuilder<K, Collection<V>> nmb,
                                     @NotNull CollectionBuilder<V, Collection<V>> cb) {
        this.nmb = nmb;
        this.cb = cb;
    }

    @Override
    @NotNull
    protected ConsumerNavigableMultiMap<K, V> buildMultiMap(@NotNull Map<K, Collection<V>> map) {
        return new ConsumerNavigableMultiMap(nmb, cb, map);
    }

    @Override
    @NotNull
    protected NavigableMap<K, Collection<V>> buildEmptyMap() {
        return nmb.build();
    }

    @Override
    @NotNull
    protected CollectionProxy<V> buildEmptyCollection() {
        return new CollectionProxy<V>(cb.build());
    }

    protected ConsumerNavigableMultiMap(@NotNull NavigableMapBuilder<K, Collection<V>> nmb,
                                        @NotNull CollectionBuilder<V, Collection<V>> cb,
                                        @NotNull Map<K, Collection<V>> map) {
        this.nmb = nmb;
        this.cb = cb;
        setMap(map);
    }
}
