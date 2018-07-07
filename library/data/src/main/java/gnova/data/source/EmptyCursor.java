package gnova.data.source;

import gnova.core.EmptyIterator;
import gnova.query.cursor.Cursor;

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
