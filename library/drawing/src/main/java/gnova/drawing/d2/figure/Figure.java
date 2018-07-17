package gnova.drawing.d2.figure;

import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;
import gnova.drawing.d2.Extent;

/**
 * 图元
 */
@Immutable
public interface Figure {

    @NotNull
    FigureFactory getFactory();

    @NotNull
    Extent getExtent();

    @NotNull
    FigureType getType();
}
