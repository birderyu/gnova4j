package gnova.geometry.model;

import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * 浮点计算的精度值
 */
@Immutable
public class Precision implements Comparable<Precision>, Serializable  {

    private final PrecisionMode mode;
    private double scale;

    public Precision() {
        this(PrecisionMode.DoubleFloat);
    }

    public Precision(PrecisionMode mode) {
        this.mode = mode;
        if (mode == PrecisionMode.CustomScale) {
            setScale(1.0);
        }
    }

    public Precision(double scale) {
        mode = PrecisionMode.CustomScale;
        setScale(scale);
    }

    public PrecisionMode getMode() {
        return mode;
    }

    public double getScale() {
        return scale;
    }

    public boolean isFloating() {
        return mode == PrecisionMode.DoubleFloat
                || mode == PrecisionMode.SingleFloat;
    }

    public Coordinate toInternal(Coordinate external) {
        double x = makePrecise(external.getX());
        double y = makePrecise(external.getY());
        double z = external.hasZ() ? makePrecise(external.getZ()) : Coordinate.NULL_ORDINATE_VALUE;
        double m = external.hasM() ? makePrecise(external.getM()) : Coordinate.NULL_ORDINATE_VALUE;
        return new Coordinate(x, y, z, m);
    }

    public double makePrecise(double val) {
        if (Double.isNaN(val)) {
            return val;
        } else if (mode == PrecisionMode.SingleFloat) {
            return val;
        } else if (mode == PrecisionMode.SingleFloat) {
            float floatSingleVal = (float) val;
            return (double) floatSingleVal;
        } else {
            return Math.round(val * scale) / scale;
        }
    }

    public int getMaximumSignificantDigits() {
        int maxSigDigits = 16;
        switch (mode) {
            case SingleFloat:
                maxSigDigits = 6;
                break;
            case DoubleFloat:
                maxSigDigits = 16;
                break;
            case CustomScale:
                maxSigDigits = 1 + (int) Math.ceil(Math.log(scale) / Math.log(10));
                break;
        }
        return maxSigDigits;
    }

    @Override
    public String toString() {
        switch (mode) {
            case SingleFloat:
                return "Single Float";
            case DoubleFloat:
                return "Double Float";
            case CustomScale:
                return "Custom Scale: " + getScale();
        }
        return "Unknown";
    }

    @Override
    public int hashCode() {
        switch (mode) {
            case SingleFloat:
                return PrecisionMode.SingleFloat.hashCode();
            case DoubleFloat:
                return PrecisionMode.DoubleFloat.hashCode();
            case CustomScale:
                return Objects.hash(PrecisionMode.CustomScale, scale);
        }
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Precision)) {
            return false;
        }
        Precision p = (Precision) obj;
        switch (mode) {
            case SingleFloat:
            case DoubleFloat:
                return p.mode == mode;
            case CustomScale:
                return p.mode == mode
                        && Double.compare(p.scale, scale) == 0;
        }
        return false;
    }

    @Override
    public int compareTo(@NotNull Precision p) {
        return Integer.compare(getMaximumSignificantDigits(), p.getMaximumSignificantDigits());
    }

    private void setScale(double scale) {
        this.scale = Math.abs(scale);
    }

}
