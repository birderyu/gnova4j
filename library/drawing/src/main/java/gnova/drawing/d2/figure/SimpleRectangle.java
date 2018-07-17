package gnova.drawing.d2.figure;

import gnova.drawing.d2.Extent;
import gnova.drawing.d2.Point;

public class SimpleRectangle extends SimpleFigure implements Rectangle {

    private final Point leftTop;
    private final Point rightBottom;

    public SimpleRectangle(FigureFactory factory, float left, float top, float right, float bottom) {
        super(factory);
        leftTop = new Point(left, top);
        rightBottom = new Point(right, bottom);
    }

    public SimpleRectangle(FigureFactory factory, Point leftTop, Point rightBottom) {
        super(factory);
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
    }

    @Override
    public Point getLeftTop() {
        return leftTop;
    }

    @Override
    public Point getRightBottom() {
        return rightBottom;
    }

    @Override
    protected Extent buildExtent() {
        return new Extent(leftTop, rightBottom);
    }
}
