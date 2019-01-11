package gnova.symbology;

import gnova.drawing.d2.Extent;
import gnova.drawing.d2.Matrix;

public class MultiProjection implements Projection {

    private final Projection source;
    private final Projection target;

    public MultiProjection(Projection source, Projection target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public Extent getSource() {
        return source.getSource();
    }

    @Override
    public Extent getTarget() {
        return target.getTarget();
    }

    @Override
    public boolean isReverseSourceY() {
        return source.isReverseSourceY();
    }

    @Override
    public boolean isReverseTargetY() {
        return target.isReverseTargetY();
    }

    @Override
    public double toSource(double distance) {
        return source.toSource(target.toSource(distance));
    }

    @Override
    public double toSourceX(double targetX) {
        return source.toSourceX(target.toSourceX(targetX));
    }

    @Override
    public double toSourceY(double targetY) {
        return source.toSourceY(target.toSourceY(targetY));
    }

    @Override
    public Matrix toSource(Matrix target) {
        return source.toSource(this.target.toSource(target));
    }

    @Override
    public double toTarget(double distance) {
        return target.toTarget(source.toTarget(distance));
    }

    @Override
    public double toTargetX(double sourceX) {
        return target.toTargetX(source.toTargetX(sourceX));
    }

    @Override
    public double toTargetY(double sourceY) {
        return target.toTargetY(source.toTargetY(sourceY));
    }

    @Override
    public Matrix toTarget(Matrix source) {
        return target.toTarget(this.source.toTarget(source));
    }
}
