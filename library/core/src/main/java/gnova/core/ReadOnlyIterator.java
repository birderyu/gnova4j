package gnova.core;

import gnova.core.annotation.Unsupported;

import java.util.Iterator;

/**
 * 只读的迭代器
 *
 * @param <E> 迭代器元素的类型
 * @see Iterator
 * @author birderyu
 * @version 1.0.0
 */
public interface ReadOnlyIterator<E>
        extends Iterator<E> {

    /**
     * 只读的迭代器不再支持移除元素的方法
     *
     * @throws UnsupportedOperationException 调用该方法一定会抛出此异常
     */
    @Override
    @Unsupported
    default void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("remove");
    }

}
