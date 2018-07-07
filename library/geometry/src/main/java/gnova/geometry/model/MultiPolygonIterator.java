package gnova.geometry.model;

import gnova.util.ReadOnlyIterator;

import java.util.NoSuchElementException;

public class MultiPolygonIterator
        implements ReadOnlyIterator<Polygon> {

    private MultiPolygon multiPolygon;
    private int cursor;

    public MultiPolygonIterator(MultiPolygon multiPolygon, int cursor) {
        this.multiPolygon = multiPolygon;
        this.cursor = cursor;
    }

    @Override
    public boolean hasNext() {
        return cursor < multiPolygon.size();
    }

    @Override
    public Polygon next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return multiPolygon.getPolygonAt(cursor++);
    }
}
