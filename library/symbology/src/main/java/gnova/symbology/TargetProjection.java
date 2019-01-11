package gnova.symbology;

import gnova.drawing.d2.Extent;
import gnova.drawing.d2.Matrix;

public class TargetProjection implements Projection {

    private final Extent source;
    private final boolean reverseSourceY;
    private final Projection target;
    private final SimpleProjection s2t;

    public TargetProjection(Extent source, Projection target) {
        this(source, target, false);
    }

    public TargetProjection(Extent source, Projection target, boolean reverseSourceY) {
        this.source = source;
        this.target = target;
        this.reverseSourceY = reverseSourceY;
        s2t = new SimpleProjection(source, target.getSource(),
                reverseSourceY, target.isReverseSourceY());
    }

    @Override
    public Extent getSource() {
        return source;
    }

    @Override
    public Extent getTarget() {
        return target.getTarget();
    }

    @Override
    public boolean isReverseSourceY() {
        return reverseSourceY;
    }

    @Override
    public boolean isReverseTargetY() {
        return target.isReverseTargetY();
    }

    @Override
    public double toSource(double distance) {
        return s2t.toSource(target.toSource(distance));
    }

    @Override
    public double toSourceX(double targetX) {
        return s2t.toSourceX(target.toSourceX(targetX));
    }

    @Override
    public double toSourceY(double targetY) {
        return s2t.toSourceY(target.toSourceY(targetY));
    }

    @Override
    public Matrix toSource(Matrix target) {
        return s2t.toSource(this.target.toSource(target));
    }

    @Override
    public double toTarget(double distance) {
        return target.toTarget(s2t.toTarget(distance));
    }

    @Override
    public double toTargetX(double sourceX) {
        return target.toTargetX(s2t.toTargetX(sourceX));
    }

    @Override
    public double toTargetY(double sourceY) {
        return target.toTargetY(s2t.toTargetY(sourceY));
    }

    @Override
    public Matrix toTarget(Matrix source) {
        return target.toTarget(s2t.toTarget(source));
    }

}
