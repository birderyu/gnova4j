package gnova.query.cursor;

import gnova.annotation.NotNull;
import gnova.function.Action;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorCursor<E>
        implements Cursor<E> {

    private final Iterator<E> iterator;
    private final Action closeAction;

    public IteratorCursor(@NotNull Iterator<E> iterator) {
        this(iterator, null);
    }

    /**
     *
     * @param iterator
     * @param closeAction 可以为null
     */
    public IteratorCursor(@NotNull Iterator<E> iterator, Action closeAction) {
        this.iterator = iterator;
        this.closeAction = closeAction;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public E next() throws NoSuchElementException {
        return iterator.next();
    }

    @Override
    public void remove() throws UnsupportedOperationException {
        iterator.next();
    }

    @Override
    public void close() {
        if (closeAction != null) {
            closeAction.does();
        }
    }
}
