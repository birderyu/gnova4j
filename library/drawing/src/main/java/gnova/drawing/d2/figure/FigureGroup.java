package gnova.drawing.d2.figure;

import gnova.core.annotation.NotNull;

public interface FigureGroup
        extends Figure, Iterable<Figure> {

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    @Override
    @NotNull
    default FigureType getType() {
        return FigureType.Group;
    }

}
