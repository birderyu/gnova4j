package gnova.geometry.model;

import gnova.core.ReadOnlyIterator;

import java.util.NoSuchElementException;

public class LineStringIterator
        implements ReadOnlyIterator<Point> {

    private LineString lineString;
    private int cursor;

    public LineStringIterator(LineString lineString, int cursor) {
        this.lineString = lineString;
        this.cursor = cursor;
    }

    @Override
    public boolean hasNext() {
        return cursor < lineString.size();
    }

    @Override
    public Point next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return lineString.getPointAt(cursor++);
    }
}
