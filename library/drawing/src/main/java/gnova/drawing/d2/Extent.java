package gnova.drawing.d2;

import gnova.core.annotation.Immutable;

/**
 * 平面的区域范围
 */
@Immutable
public class Extent {

    public static final Extent NONE = new Extent(Point.NONE, Point.NONE);

    private final Point minimum;

    private final Point maximum;

    public Extent(float x, float y) {
        minimum = maximum = new Point(x, y);
    }

    public Extent(Point point) {
        minimum = maximum = point;
    }

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

    public float getMinX() {
        return minimum.getX();
    }

    public float getMinY() {
        return minimum.getY();
    }

    public float getMaxX() {
        return maximum.getX();
    }

    public float getMaxY() {
        return maximum.getY();
    }

    public float getWidth() {
        return getMaxX() - getMinX();
    }

    public float getHeight() {
        return getMaxY() - getMinY();
    }

    public float getDiagonal() {
        return getDiagonal(getMinX(), getMinY(), getMaxX(), getMaxY());
    }

    public float getBottom() {
        return getMinY();
    }

    public float getRight() {
        return getMaxX();
    }

    public Point getCenter() {
        return new Point((getMinX() + getMaxX()) / 2f, (getMinY() + getMaxY()) / 2f);
    }

    public Extent expandToInclude(Point point) {
        return new Extent(
                Math.min(minimum.getX(), point.getX()),
                Math.min(minimum.getY(), point.getY()),
                Math.max(maximum.getX(), point.getX()),
                Math.max(maximum.getY(), point.getY()));
    }

    public Extent expandToInclude(Extent extent) {
        return new Extent(
                Math.min(minimum.getX(), extent.minimum.getX()),
                Math.min(minimum.getY(), extent.minimum.getY()),
                Math.max(maximum.getX(), extent.maximum.getX()),
                Math.max(maximum.getY(), extent.maximum.getY()));
    }

    public static float getDiagonal(float minX, float minY, float maxX, float maxY) {
        float w = maxX - minX;
        float h = maxY - minY;
        return (float) Math.sqrt(w * w + h * h);
    }

}
