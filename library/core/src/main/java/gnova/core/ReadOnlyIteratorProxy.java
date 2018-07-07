package gnova.core;

import gnova.core.annotation.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 只读迭代器的代理实现
 *
 * 使用一个其他类型的迭代器代理实现只读迭代器
 *
 * @param <E> 迭代器元素的类型
 * @see ReadOnlyIterator
 * @author birderyu
 * @version 1.0.0
 */
public class ReadOnlyIteratorProxy<E>
        implements ReadOnlyIterator<E> {

    /**
     * 迭代器，不会为null
     */
    @NotNull
    private final Iterator<E> iterator;

    /**
     * 构造一个只读迭代器的代理实现
     *
     * @param iterator 迭代器，不允许为null
     */
    public ReadOnlyIteratorProxy(@NotNull Iterator<E> iterator) {
        this.iterator = iterator;
    }

    /**
     * 判断是否还有下一个元素
     *
     * @return 若还有下一个元素，则返回true，否则返回false
     * @see Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * 返回下一个元素
     *
     * @return 元素对象
     * @throws NoSuchElementException 若没有下一个元素，则抛出此异常
     * @see Iterator#next()
     */
    @Override
    public E next() throws NoSuchElementException {
        return iterator.next();
    }
}
