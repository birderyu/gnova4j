package gnova.drawing.d2;

import gnova.core.annotation.NotNull;

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
