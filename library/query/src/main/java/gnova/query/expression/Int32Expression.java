package gnova.query.expression;

import gnova.core.annotation.NotNull;

public final class Int32Expression
        extends NumberExpression {

    private final int value;

    public Int32Expression(int value) {
        this.value = value;
    }

    public int intValue() {
        return value;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.Int32;
    }

    @Override
    public Integer getValue() {
        return Integer.valueOf(value);
    }

    @Override
    public int asInt32(int defaultValue) {
        return value;
    }

    @Override
    public Integer asNumber() {
        return value;
    }

    @Override
    public long asInt64(long defaultValue) {
        return value;
    }

    @Override
    public double asDouble(double defaultValue) {
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
            return ((Number) left).intValue() == value;
        } else if (left instanceof String) {
            try {
                return Integer.valueOf((String) left).intValue() == value;
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
            return ((Number) left).intValue() != value;
        } else if (left instanceof String) {
            try {
                return Integer.valueOf((String) left).intValue() != value;
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
            return ((Number) left).intValue() < value;
        } else if (left instanceof String) {
            try {
                return Integer.valueOf((String) left).intValue() < value;
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
            return ((Number) left).intValue() <= value;
        } else if (left instanceof String) {
            try {
                return Integer.valueOf((String) left).intValue() <= value;
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
            return ((Number) left).intValue() > value;
        } else if (left instanceof String) {
            try {
                return Integer.valueOf((String) left).intValue() > value;
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
            return ((Number) left).intValue() >= value;
        } else if (left instanceof String) {
            try {
                return Integer.valueOf((String) left).intValue() >= value;
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
        return Integer.hashCode(value);
    }
}
