package gnova.drawing.d2.figure;

import gnova.core.annotation.NotNull;
import gnova.drawing.d2.Point;

public interface Lines extends Figure, Iterable<Point> {

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    Point getPointAt(int n);

    @Override
    @NotNull
    default FigureType getType() {
        return FigureType.Lines;
    }

}
