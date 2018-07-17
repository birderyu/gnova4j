package gnova.drawing.d2.figure;

import gnova.core.annotation.NotNull;
import gnova.drawing.d2.Point;

public interface Ellipse extends Figure {

    Point getCenter();

    float getRadiusX();

    float getRadiusY();

    @Override
    @NotNull
    default FigureType getType() {
        return FigureType.Ellipse;
    }

    default boolean isCircle() {
        return Float.compare(getRadiusX(), getRadiusY()) == 0;
    }

}
