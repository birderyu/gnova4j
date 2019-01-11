package gnova.drawing.d2.figure;

import gnova.drawing.d2.Extent;
import gnova.drawing.d2.Point;

public class SimpleCircle extends SimpleFigure implements Circle {

    private final Point center;
    private final float radius;

    public SimpleCircle(FigureFactory factory, float centerX, float centerY, float radius) {
        super(factory);
        this.center = new Point(centerX, centerY);
        this.radius = radius;
    }

    public SimpleCircle(FigureFactory factory, Point center, float radius) {
        super(factory);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Point getCenter() {
        return center;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    protected Extent buildExtent() {
        return new Extent(center.getX() - radius, center.getX() + radius,
                center.getY() - radius, center.getY() + radius);
    }
}
