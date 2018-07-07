package gnova.core;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 组合迭代器
 *
 * 将多个迭代器组合成为一个迭代器
 *
 * @param <E> 迭代器元素的类型
 * @see Iterator
 * @author birderyu
 * @version 1.0.0
 */
public class MultiIterator<E>
        implements Iterator<E> {

    /**
     * 迭代器的数组
     */
    protected final Iterator<E>[] iterators;

    /**
     * 迭代器的光标，指向当前访问的迭代器
     */
    private int cursor;

    /**
     * 构造一个迭代器组合迭代器
     *
     * @param iterators 迭代器的数组
     */
    public MultiIterator(Iterator<E>... iterators) {
        this.iterators = iterators;
        this.cursor = 0;
    }

    /**
     * 判断是否还有下一个元素
     *
     * @return 若还有下一个元素，则返回true，否则返回false
     * @see Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        if (iterators == null
                || iterators.length <= 0
                || cursor >= iterators.length) {
            return false;
        }
        while (cursor < iterators.length) {
            if (iterators[cursor].hasNext()) {
                return true;
            }
            cursor++;
        }
        return false;
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
        if (iterators == null
                || iterators.length <= 0
                || cursor >= iterators.length) {
            throw new NoSuchElementException();
        }
        return iterators[cursor].next();
    }

    /**
     * 移除next方法调用之后的元素
     *
     * @throws UnsupportedOperationException 若迭代器不支持该方法，则抛出此异常
     * @throws IllegalStateException 若调用该方法前没有调用next方法，则抛出此异常
     * @see Iterator#remove()
     */
    @Override
    public void remove() throws UnsupportedOperationException, IllegalStateException {
        if (iterators == null
                || iterators.length <= 0
                || cursor >= iterators.length) {
            throw new IllegalStateException();
        }
        iterators[cursor].remove();
    }

}
