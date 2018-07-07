package gnova.geometry.model;

import gnova.util.ReadOnlyIterator;

import java.util.NoSuchElementException;

public class MultiLineStringIterator
        implements ReadOnlyIterator<LineString> {

    private MultiLineString multiLineString;
    private int cursor;

    public MultiLineStringIterator(MultiLineString multiLineString, int cursor) {
        this.multiLineString = multiLineString;
        this.cursor = cursor;
    }

    @Override
    public boolean hasNext() {
        return cursor < multiLineString.size();
    }

    @Override
    public LineString next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return multiLineString.getLineStringAt(cursor++);
    }
}
