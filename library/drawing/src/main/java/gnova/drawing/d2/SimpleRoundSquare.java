package gnova.drawing.d2;

public class SimpleRoundSquare extends SimpleSquare implements RoundSquare {

    private final float radiusX;
    private final float radiusY;

    public SimpleRoundSquare(FigureFactory factory,
                             float centerX, float centerY, float edge,
                             float radiusX, float radiusY) {
        super(factory, centerX, centerY, edge);
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    public SimpleRoundSquare(FigureFactory factory,
                             Point center, float edge,
                             float radiusX, float radiusY) {
        super(factory, center, edge);
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    @Override
    public float getRadiusX() {
        return radiusX;
    }

    @Override
    public float getRadiusY() {
        return radiusY;
    }
}
