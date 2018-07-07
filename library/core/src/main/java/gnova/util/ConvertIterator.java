package gnova.util;

import gnova.annotation.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * 元素转换迭代器
 *
 * 元素转换迭代器接受一个原始元素的迭代器和一个由原始元素类型向新类型转换的方法
 *
 * @param <F> from 原始元素的类型
 * @param <T> to 转换之后的元素的类型
 * @see Iterator
 * @author birderyu
 * @version 1.0.0
 */
public class ConvertIterator<F, T>
        implements Iterator<T> {

    /**
     * 原始元素的迭代器，不会为null
     */
    @NotNull
    protected final Iterator<F> iterator;

    /**
     * 从原始元素向新元素转换的方法，不会为null
     */
    @NotNull
    private final Function<? super F, ? extends T> converter;

    /**
     * 构造一个元素转换迭代器
     *
     * @param iterator 原始元素的迭代器，不允许为null
     * @param converter 从原始元素向新元素转换的方法，不允许为null
     */
    public ConvertIterator(@NotNull Iterator<F> iterator,
                           @NotNull Function<? super F, ? extends T> converter) {
        this.iterator = iterator;
        this.converter = converter;
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
    public T next() throws NoSuchElementException {
        return converter.apply(iterator.next());
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
