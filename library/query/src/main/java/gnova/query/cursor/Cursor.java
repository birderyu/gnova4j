package gnova.query.cursor;

import gnova.core.annotation.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 光标
 *
 * <p>光标继承自{@link java.util.Iterator}接口，是一种迭代器，与普通迭代器的不同之处在于，
 * 光标同时继承了{@link java.lang.AutoCloseable}接口，这意味着光标需要显式调用
 * {@link java.lang.AutoCloseable#close()}方法进行数据清理、回收资源等收尾工作，这个方法不会抛出异常。
 *
 * <p>Java 1.7之后，Java语法上支持自动回收资源的写法（try with resources），光标也支持这种写法。
 *
 * <p>虽然光标也是一种迭代器，但将光标转换成迭代器是不安全的，因为这样做会使光标失去{@link Cursor#close()}方法。
 *
 * @param <E> 元素的类型
 * @see java.util.Iterator
 * @see java.lang.AutoCloseable
 * @author birderyu
 * @version 1.0.0
 */
public interface Cursor<E>
        extends Iterator<E>, AutoCloseable {

    /**
     * 判断是否具有下一个元素
     *
     * @return 若具有下一个元素，则返回true，否则返回false
     */
    @Override
    boolean hasNext();

    /**
     * 获取下一个元素，若获取失败，则抛出异常
     *
     * @return 元素
     * @throws NoSuchElementException 若没有下一个元素，则抛出此异常
     */
    @Override
    E next() throws NoSuchElementException;

    /**
     * 关闭光标，回收资源
     */
    @Override
    void close();

    /**
     * 移除当前元素
     *
     * <p>当前的元素指的是调用{@link Cursor#next()}方法后返回的元素，
     * 即，调用此方法前必须调用{@link Cursor#next()}方法，否则光标将无法获知应该移除哪一个元素，
     * 此时，会抛出{@link java.lang.IllegalStateException}异常。
     *
     * @throws UnsupportedOperationException 若不支持移除元素的方法，则抛出此异常
     * @throws IllegalStateException 调用此方法前必须调用{@link Cursor#next()}方法，否则抛出此异常
     */
    @Override
    default void remove()
            throws UnsupportedOperationException, IllegalStateException {
        throw new UnsupportedOperationException("remove");
    }

    /**
     * 尝试获取下一个元素
     *
     * <p>该方法不会抛出异常，若获取下一个元素失败，则返回null
     *
     * @return 元素，若获取失败，则返回null
     */
    default E tryNext() {
        try {
            return next();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * 遍历当前光标剩余的元素
     *
     * <p>注意，这个方法在遍历元素之后，不会关闭光标，需要显式调用{@link Cursor#close()}关闭光标。
     *
     * @param action 消费者
     */
    @Override
    default void forEachRemaining(@NotNull Consumer<? super E> action) {
        while (hasNext()) {
            action.accept(next());
        }
    }

    /**
     * 遍历当前迭代器剩余的元素
     *
     * <p>注意，这个方法在遍历元素之后，不会关闭光标，需要显式调用{@link Cursor#close()}关闭光标。
     *
     * @param action 谓词，返回true表示继续遍历下面的元素，返回false表示立刻停止遍历
     */
    default void forEachRemaining(@NotNull Predicate<? super E> action) {
        while (hasNext()) {
            if (!action.test(next())) {
                break;
            }
        }
    }
}
