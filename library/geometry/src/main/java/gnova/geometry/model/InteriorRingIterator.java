package gnova.geometry.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class InteriorRingIterator
        implements Iterator<LinearRing> {

    private Polygon polygon;
    private int cursor;

    public InteriorRingIterator(Polygon polygon, int cursor) {
        this.polygon = polygon;
        this.cursor = cursor;
    }

    @Override
    public boolean hasNext() {
        return cursor < polygon.getInteriorRingSize();
    }

    @Override
    public LinearRing next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return polygon.getInteriorRingAt(cursor++);
    }
}
