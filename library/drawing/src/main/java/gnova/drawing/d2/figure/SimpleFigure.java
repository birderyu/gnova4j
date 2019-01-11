package gnova.drawing.d2.figure;

import gnova.core.annotation.NotNull;
import gnova.drawing.d2.Extent;

public abstract class SimpleFigure implements Figure {

    private final FigureFactory factory;
    private Extent extent;

    public SimpleFigure(FigureFactory factory) {
        this.factory = factory;
    }

    @Override
    public FigureFactory getFactory() {
        return factory;
    }

    @Override
    public Extent getExtent() {
        if (extent == null) {
            extent = buildExtent();
        }
        return extent;
    }

    @NotNull
    protected abstract Extent buildExtent();

}
