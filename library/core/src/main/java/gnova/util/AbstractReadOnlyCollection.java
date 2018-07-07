package gnova.util;

import gnova.annotation.Unsupported;

import java.util.AbstractCollection;
import java.util.Collection;

/**
 * 一个抽象的只读容器
 *
 * @param <E> 元素的类型
 * @see AbstractCollection
 * @see ReadOnlyCollection
 * @author birderyu
 * @version 1.0.0
 */
public abstract class AbstractReadOnlyCollection<E>
        extends AbstractCollection<E> implements ReadOnlyCollection<E> {

    /**
     * 向容器中添加一个元素
     *
     * 只读的容器中不再支持此方法，指定该方法会抛出{@link UnsupportedOperationException UnsupportedOperationException}异常
     *
     * @param e 元素
     * @return 若添加成功，则返回true，否则返回false
     * @throws UnsupportedOperationException 不支持的方法
     * @see ReadOnlyCollection#add(Object)
     */
    @Override
    @Unsupported
    public boolean add(E e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("add");
    }

    /**
     * 向容器中添加若干个元素
     *
     * 只读的容器中不再支持此方法，指定该方法会抛出{@link UnsupportedOperationException UnsupportedOperationException}异常
     *
     * @param c 元素的容器
     * @return 若添加成功，则返回true，否则返回false
     * @throws UnsupportedOperationException 不支持的方法
     * @see ReadOnlyCollection#addAll(Collection)
     */
    @Override
    @Unsupported
    public boolean addAll(Collection<? extends E> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("addAll");
    }

    /**
     * 从容器中移除一个对象
     *
     * 只读的容器中不再支持此方法，指定该方法会抛出{@link UnsupportedOperationException UnsupportedOperationException}异常
     *
     * @param o 对象
     * @return 若移除成功，则返回true，否则返回false
     * @throws UnsupportedOperationException 不支持的方法
     * @see ReadOnlyCollection#remove(Object)
     */
    @Override
    @Unsupported
    public boolean remove(Object o) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("remove");
    }

    /**
     * 从容器中移除若干个对象
     *
     * 只读的容器中不再支持此方法，指定该方法会抛出{@link UnsupportedOperationException UnsupportedOperationException}异常
     *
     * @param c 对象的容器
     * @return 若移除成功，则返回true，否则返回false
     * @throws UnsupportedOperationException 不支持的方法
     * @see ReadOnlyCollection#removeAll(Collection)
     */
    @Override
    @Unsupported
    public boolean removeAll(Collection<?> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("removeAll");
    }

    /**
     * 保留容器中的若干元素，移除其余元素
     *
     * 只读的容器中不再支持此方法，指定该方法会抛出{@link UnsupportedOperationException UnsupportedOperationException}异常
     *
     * @param c 需要保留的元素的容器
     * @return 若执行成功，则返回true，否则返回false
     * @throws UnsupportedOperationException 不支持的方法
     * @see ReadOnlyCollection#retainAll(Collection)
     */
    @Override
    @Unsupported
    public boolean retainAll(Collection<?> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("retainAll");
    }

    /**
     * 清空容器中的所有元素
     *
     * 只读的容器中不再支持此方法，指定该方法会抛出{@link UnsupportedOperationException UnsupportedOperationException}异常
     *
     * @throws UnsupportedOperationException 不支持的方法
     * @see ReadOnlyCollection#clear()
     */
    @Override
    @Unsupported
    public void clear() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("clear");
    }

}
