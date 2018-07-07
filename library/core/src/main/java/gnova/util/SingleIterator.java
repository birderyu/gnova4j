package gnova.util;

import gnova.annotation.Unsupported;
import gnova.util.ReadOnlyIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 单元素迭代器
 *
 * 只包含一个元素对象的迭代器
 *
 * @param <E> 迭代器元素的类型
 * @see Iterator
 * @see ReadOnlyIterator
 * @author birderyu
 * @version 1.0.0
 */
public class SingleIterator<E>
        implements ReadOnlyIterator<E> {

    /**
     * 元素
     */
    private final E e;

    /**
     * 元素是否被访问过
     */
    private volatile boolean visited;

    /**
     * 构造一个单元素迭代器
     *
     * @param e 元素
     */
    public SingleIterator(E e) {
        this.e = e;
        visited = false;
    }

    /**
     * 判断是否还有下一个元素
     *
     * @return 若还有下一个元素，则返回true，否则返回false
     * @see Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return !visited;
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
        if (visited) {
            throw new NoSuchElementException();
        }
        visited = true;
        return e;
    }

    /**
     * 移除next方法调用之后的元素
     *
     * 这个方法一定会抛出{@link UnsupportedOperationException UnsupportedOperationException}异常，因为单元素迭代器不允许移除元素
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
