package gnova.drawing.d2.figure;

import gnova.drawing.d2.Extent;
import gnova.drawing.d2.Point;

public class SimpleSquare extends SimpleFigure implements Square {

    private final Point center;
    private final float edge;

    public SimpleSquare(FigureFactory factory, float centerX, float centerY, float edge) {
        super(factory);
        this.center = new Point(centerX, centerY);
        this.edge = edge;
    }

    public SimpleSquare(FigureFactory factory, Point center, float edge) {
        super(factory);
        this.center = center;
        this.edge = edge;
    }

    @Override
    public Point getCenter() {
        return center;
    }

    @Override
    public float getEdge() {
        return edge;
    }

    @Override
    protected Extent buildExtent() {
        return new Extent(center.getX() - edge / 2f,
                center.getX() + edge / 2f,
                center.getY() - edge / 2f,
                center.getY() + edge / 2f);
    }
}
