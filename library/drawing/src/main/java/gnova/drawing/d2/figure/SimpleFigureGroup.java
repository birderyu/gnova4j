package gnova.drawing.d2.figure;

import gnova.drawing.d2.Extent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class SimpleFigureGroup extends SimpleFigure implements FigureGroup {

    private final Collection<Figure> figures;

    public SimpleFigureGroup(FigureFactory factory, Figure... figures) {
        super(factory);
        this.figures = Arrays.asList(figures);
    }

    public SimpleFigureGroup(FigureFactory factory, Collection<? extends Figure> figures) {
        super(factory);
        this.figures = new ArrayList<>();
        this.figures.addAll(figures);
    }

    @Override
    public int size() {
        return figures.size();
    }

    @Override
    public boolean isEmpty() {
        return figures.isEmpty();
    }

    @Override
    public Iterator<Figure> iterator() {
        return figures.iterator();
    }

    @Override
    protected Extent buildExtent() {
        int index = 0;
        Extent extent = null;
        for (Figure figure : figures) {
            if (index++ == 0) {
                extent = figure.getExtent();
            } else {
                extent = extent.expandToInclude(figure.getExtent());
            }
        }
        return extent;
    }


}
