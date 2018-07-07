package gnova.core;

import gnova.core.annotation.Unsupported;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 空迭代器
 *
 * 不包含任何元素对象的迭代器
 *
 * @param <E> 迭代器元素的类型
 * @see Iterator
 * @see ReadOnlyIterator
 * @author birderyu
 * @version 1.0.0
 */
public class EmptyIterator<E>
        implements ReadOnlyIterator<E> {

    /**
     * 判断是否还有下一个元素
     *
     * 这个方法一定返回false，因为空迭代器不包含任何元素
     *
     * @return 若还有下一个元素，则返回true，否则返回false
     * @see Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return false;
    }

    /**
     * 返回下一个元素
     *
     * 这个方法一定会抛出{@link NoSuchElementException NoSuchElementException}异常，因为空迭代器不包含任何元素
     *
     * @return 元素对象
     * @throws NoSuchElementException 若没有下一个元素，则抛出此异常
     * @see Iterator#next()
     */
    @Override
    public E next() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    /**
     * 移除next方法调用之后的元素
     *
     * 这个方法一定会抛出{@link UnsupportedOperationException UnsupportedOperationException}异常，因为空迭代器不包含任何元素
     *
     * @throws UnsupportedOperationException 若迭代器不支持该方法，则抛出此异常
     * @see Iterator#remove()
     */
    @Override
    @Unsupported
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("remove");
    }

}
