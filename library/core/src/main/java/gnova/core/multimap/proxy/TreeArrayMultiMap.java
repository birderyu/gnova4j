package gnova.core.multimap.proxy;

import gnova.core.annotation.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

/**
 * 使用TreeMap和ArrayList实现的NavigableMultiMap
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 */
public class TreeArrayMultiMap<K, V>
        extends TreeMultiMap<K, V> {

    /**
     * 构造一个使用TreeMap和ArrayList实现的NavigableMultiMap
     */
    public TreeArrayMultiMap() {
        super();
    }

    /**
     * 构造一个使用TreeMap和ArrayList实现的NavigableMultiMap
     *
     * @param comparator 排序器
     */
    public TreeArrayMultiMap(Comparator<? super K> comparator) {
        super(comparator);
    }

    @Override
    @NotNull
    protected TreeArrayMultiMap<K, V> buildMultiMap(@NotNull Map<K, Collection<V>> map) {
        return new TreeArrayMultiMap<>(map);
    }

    @Override
    @NotNull
    protected CollectionProxy<V> buildEmptyCollection() {
        return new CollectionProxy<V>(new ArrayList<>());
    }

    /**
     * 构造一个使用TreeMap和ArrayList实现的NavigableMultiMap
     *
     * @param map Map对象，不允许为null
     */
    protected TreeArrayMultiMap(@NotNull Map<K, Collection<V>> map) {
        super(map);
    }

}
