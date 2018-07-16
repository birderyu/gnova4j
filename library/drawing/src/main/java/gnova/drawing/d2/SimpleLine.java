package gnova.drawing.d2;

public class SimpleLine extends SimpleFigure implements Line {

    private final Point start;

    private final Point stop;

    public SimpleLine(FigureFactory factory,
                      float startX, float startY,
                      float stopX, float stopY) {
        super(factory);
        start = new Point(startX, startY);
        stop = new Point(stopX, stopY);
    }

    public SimpleLine(FigureFactory factory,
                      Point start,
                      Point stop) {
        super(factory);
        this.start = start;
        this.stop = stop;
    }

    @Override
    public Point getStart() {
        return start;
    }

    @Override
    public Point getStop() {
        return stop;
    }

    @Override
    protected Extent buildExtent() {
        return new Extent(
                Math.min(start.getX(), stop.getX()),
                Math.min(start.getY(), stop.getY()),
                Math.max(start.getX(), stop.getX()),
                Math.max(start.getY(), stop.getY()));
    }
}
