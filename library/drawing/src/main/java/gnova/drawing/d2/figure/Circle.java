package gnova.drawing.d2.figure;

import gnova.core.annotation.NotNull;
import gnova.drawing.d2.Point;

public interface Circle extends Figure {

    Point getCenter();

    float getRadius();

    @Override
    @NotNull
    default FigureType getType() {
        return FigureType.Circle;
    }

    @NotNull
    default Square toSquare() {
        return getFactory().buildSquare(getCenter(), getRadius() * 2);
    }

}
