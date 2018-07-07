package gnova.multimap.proxy;

import gnova.annotation.NotNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;

/**
 * 使用TreeMap和ArrayList实现的NavigableMultiMap
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 */
public class TreeLinkedMultiMap<K, V>
        extends TreeMultiMap<K, V> {

    /**
     * 使用TreeMap和ArrayList实现的NavigableMultiMap
     */
    public TreeLinkedMultiMap() {
        super();
    }

    /**
     * 使用TreeMap和ArrayList实现的NavigableMultiMap
     *
     * @param comparator 排序器
     */
    public TreeLinkedMultiMap(Comparator<? super K> comparator) {
        super(comparator);
    }

    @Override
    @NotNull
    protected TreeLinkedMultiMap<K, V> buildMultiMap(@NotNull Map<K, Collection<V>> map) {
        return new TreeLinkedMultiMap<>(map);
    }

    @Override
    @NotNull
    protected CollectionProxy<V> buildEmptyCollection() {
        return new CollectionProxy<V>(new LinkedList<>());
    }

    /**
     * 使用TreeMap和ArrayList实现的NavigableMultiMap
     *
     * @param map Map对象，不允许为null
     */
    protected TreeLinkedMultiMap(@NotNull Map<K, Collection<V>> map) {
        super(map);
    }

}
