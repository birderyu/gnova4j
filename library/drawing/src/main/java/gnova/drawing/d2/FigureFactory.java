package gnova.drawing.d2;

import gnova.core.annotation.NotNull;

public interface FigureFactory {

    @NotNull
    Line buildLine(float startX, float startY, float stopX, float stopY);

    @NotNull
    Line buildLine(Point start, Point stop);

    @NotNull
    Lines buildLines(Point... points);

    @NotNull
    Lines buildLines(float... points);

    @NotNull
    Square buildSquare(float centerX, float centerY, float edge);

    @NotNull
    Square buildSquare(Point center, float edge);

    @NotNull
    RoundSquare buildRoundSquare(float centerX, float centerY, float edge, float radiusX, float radiusY);

    @NotNull
    RoundSquare buildRoundSquare(Point center, float edge, float radiusX, float radiusY);

    @NotNull
    RoundSquare buildRoundSquare(Square square, float radiusX, float radiusY);

    @NotNull
    Rectangle buildRectangle(float left, float top, float right, float bottom);

    @NotNull
    Rectangle buildRectangle(Point leftTop, Point rightBottom);

    @NotNull
    RoundRectangle buildRoundRectangle(float left, float top, float right, float bottom, float radiusX, float radiusY);

    @NotNull
    RoundRectangle buildRoundRectangle(Point leftTop, Point rightBottom, float radiusX, float radiusY);

    @NotNull
    RoundRectangle buildRoundRectangle(Rectangle rectangle, float radiusX, float radiusY);

    @NotNull
    Circle buildCircle(float centerX, float centerY, float radius);

    @NotNull
    Circle buildCircle(Point center, float radius);

}
