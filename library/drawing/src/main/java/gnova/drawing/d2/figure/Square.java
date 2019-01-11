package gnova.drawing.d2.figure;

import gnova.core.annotation.NotNull;
import gnova.drawing.d2.Point;

public interface Square extends Figure {

    Point getCenter();

    /**
     * 边长
     *
     * @return
     */
    float getEdge();

    @Override
    @NotNull
    default FigureType getType() {
        return FigureType.Square;
    }

    @NotNull
    default Circle toCircle() {
        return getFactory().buildCircle(getCenter(), getEdge() / 2f);
    }

}
