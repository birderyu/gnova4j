package gnova.drawing.d2.figure;

import gnova.core.annotation.NotNull;
import gnova.drawing.d2.Point;

public interface Rectangle extends Figure {

    Point getLeftTop();

    Point getRightBottom();

    @Override
    @NotNull
    default FigureType getType() {
        return FigureType.Rectangle;
    }

}
