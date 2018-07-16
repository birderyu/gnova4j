package gnova.drawing.d2;

public class Extent {

    public static final Extent NONE = new Extent(Point.NONE, Point.NONE);

    private Point minimum;

    private Point maximum;

    public Extent(float minX, float minY, float maxX, float maxY) {
        minimum = new Point(minX, minY);
        maximum = new Point(maxX, maxY);
    }

    public Extent(Point min, Point max) {
        minimum = min;
        maximum = max;
    }

    public Point getMinimum() {
        return minimum;
    }

    public Point getMaximum() {
        return maximum;
    }

    public Extent expandToInclude(Extent extent) {
        return new Extent(
                Math.min(minimum.getX(), extent.minimum.getX()),
                Math.min(minimum.getY(), extent.minimum.getY()),
                Math.max(maximum.getX(), extent.maximum.getX()),
                Math.max(maximum.getY(), extent.maximum.getY()));
    }

}
