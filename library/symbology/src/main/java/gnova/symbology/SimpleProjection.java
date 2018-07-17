package gnova.symbology;

import gnova.drawing.d2.Extent;
import gnova.drawing.d2.Matrix;

public class SimpleProjection implements Projection {

    private final Extent source;
    private final boolean reverseSourceY;
    private final Extent target;
    private final boolean reverseTargetY;

    private volatile boolean scaleSetted = false;
    private double scale = 0.0;

    public SimpleProjection(Extent source, Extent target) {
        this(source, target, false, false);
    }

    public SimpleProjection(Extent source, Extent target,
                            boolean reverseSourceY, boolean reverseTargetY) {
        this.source = source;
        this.target = target;
        this.reverseSourceY = reverseSourceY;
        this.reverseTargetY = reverseTargetY;
    }

    @Override
    public Extent getSource() {
        return source;
    }

    @Override
    public Extent getTarget() {
        return target;
    }

    @Override
    public boolean isReverseSourceY() {
        return reverseSourceY;
    }

    @Override
    public boolean isReverseTargetY() {
        return reverseTargetY;
    }

    @Override
    public double toSource(double distance) {
        if (!scaleSetted)
        {
            scale = target.getDiagonal() / source.getDiagonal();
            scaleSetted = true;
        }
        return distance / scale;
    }

    @Override
    public double toSourceX(double targetX) {
        return source.getMinX() + (targetX - target.getMinX()) * (source.getWidth() / target.getWidth());
    }

    @Override
    public double toSourceY(double targetY) {
        if (reverseSourceY == reverseSourceY) {
            return source.getMinY() + (targetY - target.getMinY()) * (source.getHeight() / target.getHeight());
        } else if (reverseSourceY) {
            return source.getMaxY() - (targetY - target.getMinY()) * (source.getHeight() / target.getHeight());
        } else {
            return source.getMinY() + (target.getMaxY() - targetY) * (source.getHeight() / target.getHeight());
        }
    }

    @Override
    public Matrix toSource(Matrix target) {
        // TODO
        return null;
    }

    @Override
    public double toTarget(double distance) {
        if (!scaleSetted)
        {
            scale = target.getDiagonal() / source.getDiagonal();
            scaleSetted = true;
        }
        return distance * scale;
    }

    @Override
    public double toTargetX(double sourceX) {
        return target.getMinX() + (sourceX - source.getMinX()) * (target.getWidth() / source.getWidth());
    }

    @Override
    public double toTargetY(double sourceY) {
        if (reverseSourceY == reverseSourceY) {
            return target.getMinY() + (sourceY - source.getMinY()) * (target.getHeight() / source.getHeight());
        } else if (reverseSourceY) {
            return target.getMaxY() - (sourceY - source.getMinY()) * (target.getHeight() / source.getHeight());
        } else {
            return target.getMinY() + (source.getMaxY() - sourceY) * (target.getHeight() / source.getHeight());
        }
    }

    @Override
    public Matrix toTarget(Matrix source) {
        // TODO
        return null;
    }
}
