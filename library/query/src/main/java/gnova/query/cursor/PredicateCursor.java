package gnova.query.cursor;

import gnova.core.annotation.NotNull;
import gnova.core.PredicateIterator;

import java.util.function.Predicate;

public class PredicateCursor<E>
        extends PredicateIterator<E> implements Cursor<E> {

    public PredicateCursor(@NotNull Cursor<E> cursor,
                           @NotNull Predicate<? super E> filter) {
        super(cursor, filter);
    }

    @Override
    public void close() {
        ((Cursor<E>) iterator).close();
    }
}
