package gnova.drawing.d2;

import gnova.core.annotation.NotNull;

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
