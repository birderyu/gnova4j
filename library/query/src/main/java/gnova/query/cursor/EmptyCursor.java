package gnova.query.cursor;

import gnova.core.EmptyIterator;

public class EmptyCursor<E>
        extends EmptyIterator<E> implements Cursor<E> {

    @Override
    public E tryNext() {
        return null;
    }

    @Override
    public void close() {
        // do nothing
    }
}
