package gnova.query.cursor;

import gnova.util.EmptyIterator;

public class EmptyCursor<E>
        extends EmptyIterator<E> implements Cursor<E> {

    @Override
    public void close() {
        // do nothing
    }
}
