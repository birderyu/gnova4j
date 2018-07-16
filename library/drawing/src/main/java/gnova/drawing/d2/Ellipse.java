package gnova.drawing.d2;

import gnova.core.annotation.NotNull;

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
