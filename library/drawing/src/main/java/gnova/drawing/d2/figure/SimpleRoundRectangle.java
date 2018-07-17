package gnova.drawing.d2.figure;

import gnova.drawing.d2.Point;

public class SimpleRoundRectangle extends SimpleRectangle implements RoundRectangle {

    private final float radiusX;
    private final float radiusY;

    public SimpleRoundRectangle(FigureFactory factory,
                                float left, float top, float right, float bottom,
                                float radiusX, float radiusY) {
        super(factory, left, top, right, bottom);
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    public SimpleRoundRectangle(FigureFactory factory,
                                Point leftTop, Point rightBottom,
                                float radiusX, float radiusY) {
        super(factory, leftTop, rightBottom);
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
