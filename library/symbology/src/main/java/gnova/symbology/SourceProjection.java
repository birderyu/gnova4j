package gnova.symbology;

import gnova.drawing.d2.Extent;
import gnova.drawing.d2.Matrix;

public class SourceProjection implements Projection {

    private final Projection source;
    private final Extent target;
    private final boolean reverseTargetY;
    private final SimpleProjection s2t;

    public SourceProjection(Projection source, Extent target) {
        this(source, target, false);
    }

    public SourceProjection(Projection source, Extent target, boolean reverseTargetY) {
        this.source = source;
        this.target = target;
        this.reverseTargetY = reverseTargetY;
        s2t = new SimpleProjection(source.getTarget(), target,
                source.isReverseTargetY(), reverseTargetY);
    }

    @Override
    public Extent getSource() {
        return source.getSource();
    }

    @Override
    public Extent getTarget() {
        return target;
    }

    @Override
    public boolean isReverseSourceY() {
        return source.isReverseSourceY();
    }

    @Override
    public boolean isReverseTargetY() {
        return reverseTargetY;
    }

    @Override
    public double toSource(double distance) {
        return source.toSource(s2t.toSource(distance));
    }

    @Override
    public double toSourceX(double targetX) {
        return source.toSourceX(s2t.toSourceX(targetX));
    }

    @Override
    public double toSourceY(double targetY) {
        return source.toSourceY(s2t.toSourceY(targetY));
    }

    @Override
    public Matrix toSource(Matrix target) {
        return source.toSource(s2t.toSource(target));
    }

    @Override
    public double toTarget(double distance) {
        return s2t.toTarget(source.toTarget(distance));
    }

    @Override
    public double toTargetX(double sourceX) {
        return s2t.toTargetX(source.toTargetX(sourceX));
    }

    @Override
    public double toTargetY(double sourceY) {
        return s2t.toTargetY(source.toTargetY(sourceY));
    }

    @Override
    public Matrix toTarget(Matrix source) {
        return s2t.toTarget(this.source.toTarget(source));
    }
}
