package gnova.core.multimap.proxy;

import gnova.core.annotation.NotNull;

import java.util.HashSet;

/**
 * 使用HashMap和HashSet实现的MultiMap
 *
 * 这种MultiMap在同一个key下的value不能够重复
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 */
public class DoubleHashMultiMap<K, V>
        extends HashMultiMapProxy<K, V> {

    /**
     * 构建一个使用HashMap和HashSet实现的MultiMap对象
     */
    public DoubleHashMultiMap() {
        super();
    }

    /**
     * 构建一个使用HashMap和HashSet实现的MultiMap对象
     *
     * @param initialCapacity 初始容量
     */
    public DoubleHashMultiMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * 构建一个使用HashMap和HashSet实现的MultiMap对象
     *
     * @param initialCapacity 初始容量
     * @param loadFactor 加载阈值
     */
    public DoubleHashMultiMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    @NotNull
    protected CollectionProxy<V> buildEmptyCollection() {
        return new CollectionProxy<V>(new HashSet<>());
    }

}
