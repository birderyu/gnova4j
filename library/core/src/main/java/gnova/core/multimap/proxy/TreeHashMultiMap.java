package gnova.core.multimap.proxy;

import gnova.core.annotation.NotNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;

/**
 * 使用TreeMap和HashSet实现的NavigableMultiMap
 *
 * 这种NavigableMultiMap在同一个key下的value不能够重复
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 */
public class TreeHashMultiMap<K, V>
        extends TreeMultiMap<K, V> {

    /**
     * 构造一个使用TreeMap和HashSet实现的NavigableMultiMap
     */
    public TreeHashMultiMap() {
        super();
    }

    /**
     * 构造一个使用TreeMap和HashSet实现的NavigableMultiMap
     *
     * @param comparator 排序器
     */
    public TreeHashMultiMap(Comparator<? super K> comparator) {
        super(comparator);
    }

    @Override
    @NotNull
    protected TreeHashMultiMap<K, V> buildMultiMap(@NotNull Map<K, Collection<V>> map) {
        return new TreeHashMultiMap<>(map);
    }

    @Override
    @NotNull
    protected CollectionProxy<V> buildEmptyCollection() {
        return new CollectionProxy<V>(new HashSet<>());
    }

    /**
     * 构造一个使用TreeMap和HashSet实现的NavigableMultiMap
     *
     * @param map Map对象，不允许为null
     */
    protected TreeHashMultiMap(@NotNull Map<K, Collection<V>> map) {
        super(map);
    }

}
