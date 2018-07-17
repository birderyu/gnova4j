package gnova.drawing.d2.figure;

import gnova.core.ArrayIterator;
import gnova.drawing.d2.Extent;
import gnova.drawing.d2.Point;

import java.util.Iterator;

public class SimpleLines extends SimpleFigure implements Lines {

    private final Point[] points;

    public SimpleLines(FigureFactory factory, Point... points) {
        super(factory);
        this.points = points;
    }

    public SimpleLines(FigureFactory factory, float... points) {
        super(factory);
        this.points = new Point[points.length / 2];
        for (int i = 0; i < this.points.length; i++) {
            this.points[i] = new Point(points[2 * i], points[2 * i + 1]);
        }
    }

    @Override
    public int size() {
        return points.length;
    }

    @Override
    public Point getPointAt(int n) {
        return points[n];
    }

    @Override
    public Iterator<Point> iterator() {
        return new ArrayIterator<>(points);
    }

    @Override
    protected Extent buildExtent() {
        if (isEmpty()) {
            return Extent.NONE;
        }
        float minX = 0, minY = 0, maxX = 0, maxY = 0;
        for (int i = 0; i < points.length; i++) {
            if (i == 0) {
                minX = maxX = points[i].getX();
                minY = maxY = points[i].getY();
            } else {
                minX = Math.min(minX, points[i].getX());
                maxX = Math.max(maxX, points[i].getX());
                minY = Math.min(minY, points[i].getY());
                maxY = Math.max(maxY, points[i].getY());
            }
        }
        return new Extent(minX, minY, maxX, maxY);
    }
}
