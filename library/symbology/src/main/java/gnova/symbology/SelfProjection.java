package gnova.symbology;

import gnova.drawing.d2.Extent;
import gnova.drawing.d2.Matrix;

public class SelfProjection implements Projection {

    private final Extent extent;
    private final boolean reverseY;

    public SelfProjection(Extent extent) {
        this(extent, false);
    }

    public SelfProjection(Extent extent, boolean reverseY) {
        this.extent = extent;
        this.reverseY = reverseY;
    }

    @Override
    public Extent getSource() {
        return extent;
    }

    @Override
    public Extent getTarget() {
        return extent;
    }

    @Override
    public boolean isReverseSourceY() {
        return reverseY;
    }

    @Override
    public boolean isReverseTargetY() {
        return reverseY;
    }

    @Override
    public double toSource(double distance) {
        return distance;
    }

    @Override
    public double toSourceX(double targetX) {
        return targetX;
    }

    @Override
    public double toSourceY(double targetY) {
        return targetY;
    }

    @Override
    public Matrix toSource(Matrix target) {
        return target;
    }

    @Override
    public double toTarget(double distance) {
        return distance;
    }

    @Override
    public double toTargetX(double sourceX) {
        return sourceX;
    }

    @Override
    public double toTargetY(double sourceY) {
        return sourceY;
    }

    @Override
    public Matrix toTarget(Matrix source) {
        return source;
    }
}
