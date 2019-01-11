package gnova.drawing.d2.figure;

import gnova.core.annotation.NotNull;
import gnova.drawing.d2.Point;

public interface Line extends Figure {

    Point getStart();

    Point getStop();

    @Override
    @NotNull
    default FigureType getType() {
        return FigureType.Line;
    }

}
