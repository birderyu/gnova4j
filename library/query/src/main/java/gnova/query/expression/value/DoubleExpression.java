package gnova.query.expression.value;

import gnova.core.annotation.NotNull;
import gnova.query.expression.ValueType;

public final class DoubleExpression
        extends NumberExpression {

    private final double value;

    public DoubleExpression(double value) {
        this.value = value;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.Double;
    }

    @Override
    public Double getValue() {
        return Double.valueOf(value);
    }

    @Override
    public double asDouble ( double defaultValue){
        return value;
    }

    @Override
    public Double asNumber() {
        return value;
    }

    @Override
    protected boolean equalsBy(@NotNull Object left) {
        if (left instanceof NumberExpression) {
            NumberExpression nl = (NumberExpression) left;
            return nl.isDouble() && nl.asDouble(value) == value;
        } else if (left instanceof Number) {
            return ((Number) left).doubleValue() == value;
        } else if (left instanceof String) {
            try {
                return Double.valueOf((String) left).doubleValue() == value;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    protected boolean unequalsBy(@NotNull Object left) {
        if (left instanceof NumberExpression) {
            NumberExpression nl = (NumberExpression) left;
            return nl.isDouble() && nl.asDouble(value) != value;
        } else if (left instanceof Number) {
            return ((Number) left).doubleValue() != value;
        } else if (left instanceof String) {
            try {
                return Double.valueOf((String) left).doubleValue() != value;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    protected boolean lessBy(@NotNull Object left) {
        if (left instanceof NumberExpression) {
            NumberExpression nl = (NumberExpression) left;
            return nl.isDouble() && nl.asDouble(value) < value;
        } else if (left instanceof Number) {
            return ((Number) left).doubleValue() < value;
        } else if (left instanceof String) {
            try {
                return Double.valueOf((String) left).doubleValue() < value;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    protected boolean lessEqualsBy(@NotNull Object left) {
        if (left instanceof NumberExpression) {
            NumberExpression nl = (NumberExpression) left;
            return nl.isDouble() && nl.asDouble(value) <= value;
        } else if (left instanceof Number) {
            return ((Number) left).doubleValue() <= value;
        } else if (left instanceof String) {
            try {
                return Double.valueOf((String) left).doubleValue() <= value;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    protected boolean greaterBy(@NotNull Object left) {
        if (left instanceof NumberExpression) {
            NumberExpression nl = (NumberExpression) left;
            return nl.isDouble() && nl.asDouble(value) > value;
        } else if (left instanceof Number) {
            return ((Number) left).doubleValue() > value;
        } else if (left instanceof String) {
            try {
                return Double.valueOf((String) left).doubleValue() > value;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    protected boolean greaterEqualsBy(@NotNull Object left) {
        if (left instanceof NumberExpression) {
            NumberExpression nl = (NumberExpression) left;
            return nl.isDouble() && nl.asDouble(value) >= value;
        } else if (left instanceof Number) {
            return ((Number) left).doubleValue() >= value;
        } else if (left instanceof String) {
            try {
                return Double.valueOf((String) left).doubleValue() >= value;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}