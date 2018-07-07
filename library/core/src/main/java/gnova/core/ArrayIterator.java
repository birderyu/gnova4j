package gnova.core;

import gnova.core.annotation.NotNull;
import gnova.core.annotation.Unsupported;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * 数组迭代器
 *
 * 使用数组构建的迭代器
 *
 * @param <E> 迭代器元素的类型
 * @see ListIterator
 * @author birderyu
 * @version 1.0.0
 */
public class ArrayIterator<E>
        implements ListIterator<E> {

    /**
     * 数组元素，不会为null
     */
    @NotNull
    private E[] es;

    /**
     * 元素的光标，指向当前访问的元素
     */
    private int cursor;

    /**
     * state大于0，表示正向遍历
     * state小于0，表示反向遍历
     * state等于0，表示还未开始遍历
     */
    private volatile int state;

    /**
     * 构造一个数组迭代器
     *
     * @param es 数组，不允许为null
     */
    public ArrayIterator(@NotNull E ...es) {
        this.es = es;
        this.cursor = 0;
        this.state = 0;
    }

    /**
     * 构造一个数组迭代器
     *
     * @param es 数组，不允许为null
     * @param cursor 起始迭代的元素的下标，从0开始计数，最大不能超过数组的长度
     * @throws IndexOutOfBoundsException 若下标小于0或者大于数组的长度，则抛出此异常
     */
    public ArrayIterator(@NotNull E[] es, int cursor) throws IndexOutOfBoundsException {
        this.es = es;
        reset(cursor);
    }

    /**
     * 获取数组
     *
     * @return 数组
     */
    public E[] getArray() {
        return es;
    }

    /**
     * 重置迭代器的起始下标
     *
     * @param cursor 起始迭代的元素的下标，从0开始计数，最大不能超过数组的长度
     * @throws IndexOutOfBoundsException 若下标小于0或者大于数组的长度，则抛出此异常
     */
    public void reset(int cursor) throws IndexOutOfBoundsException {
        if (cursor < 0 || cursor > es.length) {
            throw new IndexOutOfBoundsException();
        }
        this.cursor = cursor;
        this.state = 0;
    }

    /**
     * 判断是否还有下一个元素
     *
     * @return 若还有下一个元素，则返回true，否则返回false
     * @see ListIterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return cursor < es.length;
    }

    /**
     * 获取下一个元素
     *
     * @return 元素对象
     * @throws NoSuchElementException 若没有下一个元素，则抛出此异常
     * @see ListIterator#next()
     */
    @Override
    public E next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        state = 1;
        return es[cursor++];
    }

    /**
     * 判断是否还有上一个元素
     *
     * @return 若还有上一个元素，则返回true，否则返回false
     * @see ListIterator#hasPrevious()
     */
    @Override
    public boolean hasPrevious() {
        return cursor != 0;
    }

    /**
     * 获取上一个元素
     *
     * @return 元素对象
     * @throws NoSuchElementException 若没有上一个元素，则抛出此异常
     * @see ListIterator#previous()
     */
    @Override
    public E previous() throws NoSuchElementException {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        state = -1;
        return es[--cursor];
    }

    /**
     * 获取下一个元素的下标
     *
     * @return 下标
     * @see ListIterator#nextIndex()
     */
    @Override
    public int nextIndex() {
        return cursor;
    }

    /**
     * 获取上一个元素的下标
     *
     * @return 下标
     * @see ListIterator#previousIndex()
     */
    @Override
    public int previousIndex() {
        return cursor - 1;
    }

    /**
     * 移除next或previous方法调用之后返回的元素
     *
     * @throws IllegalStateException 若调用该方法前没有调用next或previous方法，则抛出此异常
     * @see ListIterator#remove()
     */
    @Override
    public void remove() throws IllegalStateException {
        if (state == 0) {
            throw new IllegalStateException();
        }
        if (state > 0) {
            es = ArrayUtil.removeAt(es, --cursor);
            state = 0;
        } else {
            es = ArrayUtil.removeAt(es, cursor);
            state = 0;
        }
    }

    /**
     * 重新设置next或previous方法调用之后返回的元素
     *
     * @param e 元素对象
     * @throws IllegalStateException 若没有调用next或previous方法，或调用next或previous方法之后调用了reset、remove或add方法，则抛出此异常
     * @see ListIterator#set(Object)
     */
    @Override
    public void set(E e) throws IllegalStateException {
        if (state == 0) {
            throw new IllegalStateException();
        }
        if (state > 0) {
            es[cursor - 1] = e;
        } else {
            es[cursor] = e;
        }
    }

    /**
     * 添加一个元素到当前下标位置之前
     *
     * @param e 元素对象
     * @see ListIterator#add(Object)
     */
    @Override
    @Unsupported
    public void add(E e) {
        es = ArrayUtil.addAt(es, e, cursor);
        cursor++;
        state = 0;
    }

}
