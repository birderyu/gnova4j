package gnova.query.expression;

import gnova.core.annotation.NotNull;

public final class DoubleExpression
        extends NumberExpression {

    private final double value;

    public DoubleExpression(double value) {
        this.value = value;
    }

    public double doubleValue() {
        return value;
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
            NumberExpression ne = (NumberExpression) left;
            if (ne.isInt32()) {
                return ((Int32Expression) ne).intValue() == value;
            } else if (ne.isInt64()) {
                return ((Int64Expression) ne).longValue() == value;
            } else if (ne.isDouble()) {
                return Double.compare(((DoubleExpression) ne).doubleValue(), value) == 0;
            } else {
                return false;
            }
        } else if (left instanceof Number) {
            return Double.compare(((Number) left).doubleValue(), value) == 0;
        } else if (left instanceof String) {
            try {
                return Double.compare(Double.valueOf((String) left).doubleValue(), value) == 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    protected boolean unequalsBy(@NotNull Object left) {
        if (left instanceof NumberExpression) {
            NumberExpression ne = (NumberExpression) left;
            if (ne.isInt32()) {
                return ((Int32Expression) ne).intValue() != value;
            } else if (ne.isInt64()) {
                return ((Int64Expression) ne).longValue() != value;
            } else if (ne.isDouble()) {
                return Double.compare(((DoubleExpression) ne).doubleValue(), value) != 0;
            } else {
                return false;
            }
        } else if (left instanceof Number) {
            return Double.compare(((Number) left).doubleValue(), value) != 0;
        } else if (left instanceof String) {
            try {
                return Double.compare(Double.valueOf((String) left).doubleValue(), value) != 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    protected boolean lessBy(@NotNull Object left) {
        if (left instanceof NumberExpression) {
            NumberExpression ne = (NumberExpression) left;
            if (ne.isInt32()) {
                return ((Int32Expression) ne).intValue() < value;
            } else if (ne.isInt64()) {
                return ((Int64Expression) ne).longValue() < value;
            } else if (ne.isDouble()) {
                return ((DoubleExpression) ne).doubleValue() < value;
            } else {
                return false;
            }
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
            NumberExpression ne = (NumberExpression) left;
            if (ne.isInt32()) {
                return ((Int32Expression) ne).intValue() <= value;
            } else if (ne.isInt64()) {
                return ((Int64Expression) ne).longValue() <= value;
            } else if (ne.isDouble()) {
                return ((DoubleExpression) ne).doubleValue() <= value;
            } else {
                return false;
            }
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
            NumberExpression ne = (NumberExpression) left;
            if (ne.isInt32()) {
                return ((Int32Expression) ne).intValue() > value;
            } else if (ne.isInt64()) {
                return ((Int64Expression) ne).longValue() > value;
            } else if (ne.isDouble()) {
                return ((DoubleExpression) ne).doubleValue() > value;
            } else {
                return false;
            }
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
            NumberExpression ne = (NumberExpression) left;
            if (ne.isInt32()) {
                return ((Int32Expression) ne).intValue() >= value;
            } else if (ne.isInt64()) {
                return ((Int64Expression) ne).longValue() >= value;
            } else if (ne.isDouble()) {
                return ((DoubleExpression) ne).doubleValue() >= value;
            } else {
                return false;
            }
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof NumberExpression)) {
            return false;
        }
        NumberExpression ne = (NumberExpression) obj;
        if (ne.isInt32()) {
            return value == ((Int32Expression) ne).intValue();
        } else if (ne.isInt64()) {
            return value == ((Int64Expression) ne).longValue();
        } else if (ne.isDouble()) {
            return Double.compare(value, ((DoubleExpression) ne).doubleValue()) == 0;
        } else {
            return false;
        }
    }

    @Override
    protected int hashing() {
        return Double.hashCode(value);
    }
}