package gnova.core;

import gnova.core.annotation.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * 谓词迭代器
 *
 * 谓词迭代器仅会返回迭代器中符合过滤条件的项，不符合的项会被忽略
 *
 * @param <E> 迭代器中的元素
 * @see Iterator
 * @author birderyu
 * @version 1.0.0
 */
public class PredicateIterator<E>
        implements Iterator<E> {

    /**
     * 原始迭代器，不会为null
     */
    @NotNull
    protected final Iterator<E> iterator;

    /**
     * 过滤条件，不会为null
     */
    @NotNull
    private final Predicate<? super E> filter;

    /**
     * 符合条件的下一个元素
     */
    private E next = null;

    /**
     * 构造一个谓词迭代器
     *
     * @param iterator 原始迭代器，不允许为null
     * @param filter 过滤条件，不允许为null
     */
    public PredicateIterator(@NotNull Iterator<E> iterator,
                             @NotNull Predicate<? super E> filter) {
        this.iterator = iterator;
        this.filter = filter;
        next0();
    }

    /**
     * 判断是否还有下一个元素
     *
     * @return 若还有下一个元素，则返回true，否则返回false
     * @see Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return next != null;
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
        if (next == null) {
            throw new NoSuchElementException();
        }
        E last = next;
        next0();
        return last;
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
        if (next == null) {
            throw new IllegalStateException();
        }
        this.iterator.remove();
    }

    /**
     * 获取迭代器符合谓词条件的下一个元素
     */
    private void next0() {
        while (iterator.hasNext()) {
            next = iterator.next();
            if (filter.test(next)) {
                return;
            }
        }
        next = null;
    }
}
