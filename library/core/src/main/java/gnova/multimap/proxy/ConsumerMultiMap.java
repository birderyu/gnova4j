package gnova.multimap.proxy;

import gnova.annotation.NotNull;
import gnova.function.CollectionBuilder;
import gnova.function.MapBuilder;
import gnova.multimap.MultiMap;

import java.util.Collection;
import java.util.Map;

/**
 * 由用户自定义的MultiMap
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 */
public class ConsumerMultiMap<K, V>
        extends MultiMapProxy<K, V> implements MultiMap<K, V> {

    /**
     * Map构造器
     */
    private final MapBuilder<K, Collection<V>> mb;

    /**
     * 集合构造器
     */
    private final CollectionBuilder<V, Collection<V>> cb;

    /**
     * 创建一个由用户自定义的MultiMap
     *
     * @param mb Map构造器
     * @param cb 集合构造器
     */
    public ConsumerMultiMap(@NotNull MapBuilder<K, Collection<V>> mb,
                            @NotNull CollectionBuilder<V, Collection<V>> cb) {
        this.mb = mb;
        this.cb = cb;
    }

    @Override
    @NotNull
    protected Map<K, Collection<V>> buildEmptyMap() {
        return mb.build();
    }

    @Override
    @NotNull
    protected CollectionProxy<V> buildEmptyCollection() {
        return new CollectionProxy<V>(cb.build());
    }

}
