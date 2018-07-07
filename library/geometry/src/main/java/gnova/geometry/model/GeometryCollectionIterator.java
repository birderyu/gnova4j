package gnova.geometry.model;

import gnova.core.ReadOnlyIterator;

import java.util.NoSuchElementException;

public class GeometryCollectionIterator<G extends Geometry>
        implements ReadOnlyIterator<G> {

    private GeometryCollection<G> geometries;
    private int cursor;

    public GeometryCollectionIterator(GeometryCollection<G> geometries, int cursor) {
        this.geometries = geometries;
        this.cursor = cursor;
    }

    @Override
    public boolean hasNext() {
        return cursor < geometries.size();
    }

    @Override
    public G next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return geometries.getGeometryAt(cursor++);
    }
}
