package gnova.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 父类型元素迭代器
 *
 * 将迭代器中的元素类型转换成其父类的类型
 *
 * @param <E> 迭代器元素的类型
 * @see Iterator
 * @author birderyu
 * @version 1.0.0
 */
public class SuperIterator<E>
        implements Iterator<E> {

    /**
     * 子类型元素迭代器
     */
    protected final Iterator<? extends E> iterator;

    /**
     * 构造一个父类型元素迭代器
     *
     * @param iterator 子类型元素迭代器
     */
    public SuperIterator(Iterator<? extends E> iterator) {
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

    /**
     * 移除next方法调用之后的元素
     *
     * @throws UnsupportedOperationException 若迭代器不支持该方法，则抛出此异常
     * @throws IllegalStateException 若调用该方法前没有调用next方法，则抛出此异常
     * @see Iterator#remove()
     */
    @Override
    public void remove() throws UnsupportedOperationException, IllegalStateException {
        iterator.remove();
    }
}
