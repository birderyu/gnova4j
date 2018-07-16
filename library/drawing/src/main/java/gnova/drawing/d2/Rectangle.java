package gnova.drawing.d2;

import gnova.core.annotation.NotNull;

public interface Rectangle extends Figure {

    Point getLeftTop();

    Point getRightBottom();

    @Override
    @NotNull
    default FigureType getType() {
        return FigureType.Rectangle;
    }

}
