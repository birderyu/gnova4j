package gnova.drawing.d2.figure;

import gnova.core.annotation.NotNull;

import java.util.Iterator;

public interface Polygon extends Figure, Iterable<Ring> {

    Ring getShell();

    Iterator<Ring> getHoles();

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    Ring getRingAt(int n);

    @Override
    @NotNull
    default FigureType getType() {
        return FigureType.Lines;
    }

}
