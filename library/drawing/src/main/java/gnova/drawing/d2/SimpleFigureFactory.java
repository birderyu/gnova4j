package gnova.drawing.d2;

public class SimpleFigureFactory implements FigureFactory {

    @Override
    public SimpleLine buildLine(float startX, float startY, float stopX, float stopY) {
        return new SimpleLine(this, startX, startY, stopX, stopY);
    }

    @Override
    public SimpleLine buildLine(Point start, Point stop) {
        return new SimpleLine(this, start, stop);
    }

    @Override
    public SimpleLines buildLines(Point... points) {
        return new SimpleLines(this, points);
    }

    @Override
    public SimpleLines buildLines(float... points) {
        return new SimpleLines(this, points);
    }

    @Override
    public SimpleSquare buildSquare(float centerX, float centerY, float edge) {
        return new SimpleSquare(this, centerX, centerY, edge);
    }

    @Override
    public SimpleSquare buildSquare(Point center, float edge) {
        return new SimpleSquare(this, center, edge);
    }

    @Override
    public SimpleRoundSquare buildRoundSquare(float centerX, float centerY, float edge, float radiusX, float radiusY) {
        return new SimpleRoundSquare(this, centerX, centerY, edge, radiusX, radiusY);
    }

    @Override
    public SimpleRoundSquare buildRoundSquare(Point center, float edge, float radiusX, float radiusY) {
        return new SimpleRoundSquare(this, center, edge, radiusX, radiusY);
    }

    @Override
    public SimpleRoundSquare buildRoundSquare(Square square, float radiusX, float radiusY) {
        return new SimpleRoundSquare(this, square.getCenter(), square.getEdge(), radiusX, radiusY);
    }

    @Override
    public SimpleRectangle buildRectangle(float left, float top, float right, float bottom) {
        return new SimpleRectangle(this, left, top, right, bottom);
    }

    @Override
    public SimpleRectangle buildRectangle(Point leftTop, Point rightBottom) {
        return new SimpleRectangle(this, leftTop, rightBottom);
    }

    @Override
    public SimpleRoundRectangle buildRoundRectangle(float left, float top, float right, float bottom, float radiusX, float radiusY) {
        return new SimpleRoundRectangle(this, left, top, right, bottom, radiusX, radiusY);
    }

    @Override
    public SimpleRoundRectangle buildRoundRectangle(Point leftTop, Point rightBottom, float radiusX, float radiusY) {
        return new SimpleRoundRectangle(this, leftTop, rightBottom, radiusX, radiusY);
    }

    @Override
    public SimpleRoundRectangle buildRoundRectangle(Rectangle rectangle, float radiusX, float radiusY) {
        return new SimpleRoundRectangle(this, rectangle.getLeftTop(), rectangle.getRightBottom(), radiusX, radiusY);
    }

    @Override
    public SimpleCircle buildCircle(float centerX, float centerY, float radius) {
        return new SimpleCircle(this, centerX, centerY, radius);
    }

    @Override
    public SimpleCircle buildCircle(Point center, float radius) {
        return new SimpleCircle(this, center, radius);
    }
}
