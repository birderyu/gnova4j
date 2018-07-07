package gnova.util;

import gnova.annotation.NotNull;

/**
 * 只读的可迭代的
 *
 * 只读的可迭代的接口在调用获取迭代器的方法时，会返回一个只读的迭代器
 *
 * @param <T> 迭代器元素的类型
 * @see Iterable
 * @author birderyu
 * @version 1.0.0
 */
public interface ReadOnlyIterable<T>
        extends Iterable<T> {

    /**
     * 获取只读的迭代器
     *
     * @return 只读的迭代器，不会返回null
     * @see Iterable#iterator()
     * @see ReadOnlyIterator
     */
    @Override
    @NotNull
    ReadOnlyIterator<T> iterator();

}
