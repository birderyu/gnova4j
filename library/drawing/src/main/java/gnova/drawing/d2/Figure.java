package gnova.drawing.d2;

import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;

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
