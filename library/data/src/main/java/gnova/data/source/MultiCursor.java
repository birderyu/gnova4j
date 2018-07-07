package gnova.data.source;

import gnova.core.MultiIterator;
import gnova.query.cursor.Cursor;

import java.util.Iterator;

public class MultiCursor<E>
        extends MultiIterator<E> implements Cursor<E> {

    public MultiCursor(Cursor<E>... cursors) {
        super(cursors);
    }

    @Override
    public void close() {
        if (iterators == null || iterators.length <= 0) {
            return;
        }

        for (Iterator<E> iterator : iterators) {
            Cursor<E> cursor = (Cursor<E>) iterator;
            cursor.close();
        }
    }
}
