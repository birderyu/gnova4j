package gnova.core;

import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;
import gnova.core.annotation.Unsupported;

import java.util.Collection;
import java.util.Iterator;

/**
 * 只读的容器
 *
 * <p>只读的容器继承自{@link java.util.Collection}接口，与普通容器的不同之处在于，
 * 只读的容器不可以调用任何写方法，如{@link java.util.Collection#add(Object)}、{@link java.util.Collection#remove(Object)}
 * 等方法，当调用这些方法时，会抛出{@link java.lang.UnsupportedOperationException}异常。
 *
 * <p>只读的容器同时继承自{@link ReadOnlyIterable}接口，它的{@link ReadOnlyIterable#iterator()}方法
 * 返回一个只读的迭代器{@link ReadOnlyIterator}，这个迭代器也不能进行写操作。
 *
 * @param <E> 元素的类型
 * @see Collection
 * @see ReadOnlyIterable
 * @author birderyu
 * @version 1.0.0
 */
@Immutable
public interface ReadOnlyCollection<E>
        extends Collection<E>, ReadOnlyIterable<E> {

    /**
     * 获取元素的迭代器
     *
     * @return 只读的迭代器，不会返回null
     * @see ReadOnlyIterable#iterator()
     * @see ReadOnlyIterator
     */
    @Override
    @NotNull
    ReadOnlyIterator<E> iterator();

    /**
     * 判断容器是否为空
     *
     * @return 若容器为空，则返回true，否则返回false
     * @see Collection#isEmpty()
     */
    @Override
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 判断容器中是否包含对象
     *
     * @param o 对象
     * @return 若包含该对象，则返回true，否则返回false
     * @see Collection#contains(Object)
     */
    @Override
    default boolean contains(Object o) {
        Iterator<E> it = iterator();
        if (o == null) {
            while (it.hasNext())
                if (it.next()==null)
                    return true;
        } else {
            while (it.hasNext())
                if (o.equals(it.next()))
                    return true;
        }
        return false;
    }

    /**
     * 判断容器中是否包含另一个容器中的所有对象
     *
     * @param c 容器对象
     * @return 若包含该容器中的所有对象，则返回true，否则返回false
     * @see Collection#containsAll(Collection)
     */
    @Override
    default boolean containsAll(Collection<?> c) {
        for (Object e : c)
            if (!contains(e))
                return false;
        return true;
    }

    /**
     * 转换为数组
     *
     * @return 数组，不会返回null
     * @see Collection#toArray()
     */
    @Override
    @NotNull
    default Object[] toArray() {
        Object[] a = new Object[size()];
        int index = 0;
        for (E e : this) {
            a[index++] = e;
        }
        return a;
    }

    /**
     * 转换为特定元素类型的数组
     *
     * @param a 数组，不允许为null
     * @param <T> 数组元素的类型
     * @return 数组，不会返回null
     * @throws ClassCastException 若元素转换失败，则抛出此异常
     * @see Collection#toArray(Object[])
     */
    @Override
    @NotNull
    default <T> T[] toArray(@NotNull T[] a) throws ClassCastException {
        int size = size();
        T[] r = a.length >= size ? a :
                (T[])java.lang.reflect.Array
                        .newInstance(a.getClass().getComponentType(), size);
        int index = 0;
        for (E e : this) {
            r[index++] = (T) e;
        }
        for (; index < r.length; index++) {
            r[index] = null;
        }
        return r;

    }

    /**
     * 向容器中添加一个元素
     *
     * 只读的容器中不再支持此方法，指定该方法会抛出{@link UnsupportedOperationException}异常
     *
     * @param e 元素
     * @return 若添加成功，则返回true，否则返回false
     * @throws UnsupportedOperationException 不支持的方法
     * @see Collection#add(Object)
     */
    @Override
    @Unsupported
    default boolean add(E e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("add");
    }

    /**
     * 向容器中添加若干个元素
     *
     * 只读的容器中不再支持此方法，指定该方法会抛出{@link UnsupportedOperationException}异常
     *
     * @param c 元素的容器
     * @return 若添加成功，则返回true，否则返回false
     * @throws UnsupportedOperationException 不支持的方法
     * @see Collection#addAll(Collection)
     */
    @Override
    @Unsupported
    default boolean addAll(Collection<? extends E> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("addAll");
    }

    /**
     * 从容器中移除一个对象
     *
     * 只读的容器中不再支持此方法，指定该方法会抛出{@link UnsupportedOperationException}异常
     *
     * @param o 对象
     * @return 若移除成功，则返回true，否则返回false
     * @throws UnsupportedOperationException 不支持的方法
     * @see Collection#remove(Object)
     */
    @Override
    @Unsupported
    default boolean remove(Object o) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("remove");
    }

    /**
     * 从容器中移除若干个对象
     *
     * 只读的容器中不再支持此方法，指定该方法会抛出{@link UnsupportedOperationException}异常
     *
     * @param c 对象的容器
     * @return 若移除成功，则返回true，否则返回false
     * @throws UnsupportedOperationException 不支持的方法
     * @see Collection#removeAll(Collection)
     */
    @Override
    @Unsupported
    default boolean removeAll(Collection<?> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("removeAll");
    }

    /**
     * 保留容器中的若干元素，移除其余元素
     *
     * 只读的容器中不再支持此方法，指定该方法会抛出{@link UnsupportedOperationException}异常
     *
     * @param c 需要保留的元素的容器
     * @return 若执行成功，则返回true，否则返回false
     * @throws UnsupportedOperationException 不支持的方法
     * @see Collection#retainAll(Collection)
     */
    @Override
    @Unsupported
    default boolean retainAll(Collection<?> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("retainAll");
    }

    /**
     * 清空容器中的所有元素
     *
     * 只读的容器中不再支持此方法，指定该方法会抛出{@link UnsupportedOperationException }异常
     *
     * @throws UnsupportedOperationException 不支持的方法
     * @see Collection#clear()
     */
    @Override
    @Unsupported
    default void clear() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("clear");
    }

}
