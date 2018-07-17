package gnova.drawing.d2;

import gnova.core.ArrayIterator;
import gnova.core.annotation.NotNull;

import java.util.Iterator;

public class Points implements Iterable<Point> {

    private Point[] points;
    private Extent extent;

    public Points(Point... points) {
        this.points = points;
    }

    @Override
    public Iterator<Point> iterator() {
        return new ArrayIterator<>(points);
    }

    @NotNull
    public Extent getExtent() {
        if (extent == null) {
            extent = buildExtent();
        }
        return extent;
    }

    @NotNull
    private Extent buildExtent() {
        Extent extent = null;
        for (int i = 0; i < points.length; i++) {
            if (i == 0) {
                extent = new Extent(points[i]);
            } else {
                extent = extent.expandToInclude(points[i]);
            }
        }
        return extent;
    }
}
