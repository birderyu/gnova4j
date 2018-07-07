package gnova.multimap.concurrent;

import gnova.annotation.ThreadSafe;
import gnova.multimap.MultiMap;

/**
 * 支持并发操作的MultiMap
 *
 * ConcurrentMultiMap是一种特殊的MultiMap，它保证所有的操作都是保证线程安全的
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 * @see MultiMap
 * @author birderyu
 * @version 1.0.0
 */
@ThreadSafe
public interface ConcurrentMultiMap<K, V>
        extends MultiMap<K, V> {

}
