package gnova.drawing.d2;

import gnova.core.annotation.NotNull;

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
