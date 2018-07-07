package gnova.query.cursor;

import gnova.annotation.NotNull;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 可生成光标的
 *
 * <p>可生成光标的表示一类可以生成一个光标的对象，通过调用{@link Cursorable#cursor()}
 * 方法获取一个{@link Cursor 光标}，进而迭代所有的元素。
 *
 * <p>Cursorable继承自{@link java.lang.Iterable}接口，而{@link Cursor 光标}继承自
 * {@link java.util.Iterator}接口，这说明Cursorable对象可以使用一个增强的for循环进行遍历，
 * 但这样做是不安全的，因为{@link Cursor 光标}对象必须要用{@link Cursor#close()}回收资源，
 * 但在增强的for循环中不会这样做。
 *
 * @param <T> 光标元素的类型
 * @see java.lang.Iterable
 * @see java.util.Iterator
 * @see Cursor
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface Cursorable<T>
        extends Iterable<T> {

    /**
     * 获取光标
     *
     * @return 光标，不允许返回null
     */
    @NotNull
    Cursor<T> cursor();

    default SplitCursor<T> splitcursor() {
        // TODO
        return null;
    }

    @Override
    default Cursor<T> iterator() {
        return cursor();
    }

    @Override
    default SplitCursor<T> spliterator() {
        return splitcursor();
    }

    @Override
    default void forEach(@NotNull Consumer<? super T> action) {
        try(Cursor<T> cursor = cursor()) {
            cursor.forEachRemaining(action);
        }
    }

    default void forEach(@NotNull Predicate<? super T> action) {
        try(Cursor<T> cursor = cursor()) {
            cursor.forEachRemaining(action);
        }
    }

}
