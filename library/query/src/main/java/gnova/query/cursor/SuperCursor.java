package gnova.query.cursor;

import gnova.util.SuperIterator;

public class SuperCursor<E>
        extends SuperIterator<E> implements Cursor<E> {

    public SuperCursor(Cursor<? extends E> cursor) {
        super(cursor);
    }

    @Override
    public void close() {
        ((Cursor<? extends E>) iterator).close();
    }
}
