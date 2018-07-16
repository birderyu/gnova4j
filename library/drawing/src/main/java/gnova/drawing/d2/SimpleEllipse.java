package gnova.drawing.d2;

public class SimpleEllipse extends SimpleFigure implements Ellipse {

    private Point center;
    private float radiusX;
    private float radiusY;

    public SimpleEllipse(FigureFactory factory, float centerX, float centerY, float radiusX, float radiusY) {
        super(factory);
        this.center = new Point(centerX, centerY);
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    public SimpleEllipse(FigureFactory factory, Point center, float radiusX, float radiusY) {
        super(factory);
        this.center = center;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    @Override
    public Point getCenter() {
        return center;
    }

    @Override
    public float getRadiusX() {
        return radiusX;
    }

    @Override
    public float getRadiusY() {
        return radiusY;
    }

    @Override
    protected Extent buildExtent() {
        return new Extent(center.getX() - radiusX, center.getX() + radiusX,
                center.getY() - radiusY, center.getY() + radiusY);
    }
}
