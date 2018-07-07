package gnova.query.cursor;

import gnova.query.cursor.Cursor;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 可分割的光标
 *
 * @param <E>
 */
public interface SplitCursor<E>
        extends Spliterator<E>, Cursor<E> {

    @Override
    default boolean tryAdvance(Consumer<? super E> action) {
        E e = tryNext();
        if (e == null) {
            return false;
        }
        action.accept(e);
        return hasNext();
    }

    @Override
    default void forEachRemaining(Consumer<? super E> action) {
        do { } while (tryAdvance(action));
    }

}
