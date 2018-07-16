package gnova.drawing.d2;

import gnova.core.annotation.NotNull;

public interface Line extends Figure {

    Point getStart();

    Point getStop();

    @Override
    @NotNull
    default FigureType getType() {
        return FigureType.Line;
    }

}
