package gnova.geometry.model;

import gnova.util.ReadOnlyIterator;

import java.util.NoSuchElementException;

public class MultiPointIterator
        implements ReadOnlyIterator<Point> {

    private MultiPoint multiPoint;
    private int cursor;

    public MultiPointIterator(MultiPoint multiPoint, int cursor) {
        this.multiPoint = multiPoint;
        this.cursor = cursor;
    }

    @Override
    public boolean hasNext() {
        return cursor < multiPoint.size();
    }

    @Override
    public Point next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return multiPoint.getPointAt(cursor++);
    }
}
