package gnova.geometry.model;

import gnova.util.ReadOnlyIterator;

import java.util.NoSuchElementException;

public class CoordinateSequenceIterator
        implements ReadOnlyIterator<Coordinate> {

    private CoordinateSequence coordinateSequence;
    private int cursor;

    public CoordinateSequenceIterator(CoordinateSequence coordinateSequence, int cursor) {
        this.coordinateSequence = coordinateSequence;
        this.cursor = cursor;
    }

    @Override
    public boolean hasNext() {
        return cursor < coordinateSequence.size();
    }

    @Override
    public Coordinate next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return coordinateSequence.getCoordinateAt(cursor++);
    }
}
