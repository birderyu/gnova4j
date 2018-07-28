package gnova.query.expression.value;

import gnova.core.annotation.NotNull;
import gnova.query.expression.ValueType;

public final class Int32Expression
        extends NumberExpression {

    private final int value;

    public Int32Expression(int value) {
        this.value = value;
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
            NumberExpression nl = (NumberExpression) left;
            if (nl.isInt32()) {
                return nl.asInt32(value) == value;
            } else if (nl.isInt64()) {
                return nl.asInt64(value) == value;
            } else if (nl.isDouble()) {
                return nl.asDouble(value) == value;
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
            NumberExpression nl = (NumberExpression) left;
            if (nl.isInt32()) {
                return nl.asInt32(value) != value;
            } else if (nl.isInt64()) {
                return nl.asInt64(value) != value;
            } else if (nl.isDouble()) {
                return nl.asDouble(value) != value;
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
            NumberExpression nl = (NumberExpression) left;
            if (nl.isInt32()) {
                return nl.asInt32(value) < value;
            } else if (nl.isInt64()) {
                return nl.asInt64(value) < value;
            } else if (nl.isDouble()) {
                return nl.asDouble(value) < value;
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
            NumberExpression nl = (NumberExpression) left;
            if (nl.isInt32()) {
                return nl.asInt32(value) <= value;
            } else if (nl.isInt64()) {
                return nl.asInt64(value) <= value;
            } else if (nl.isDouble()) {
                return nl.asDouble(value) <= value;
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
            NumberExpression nl = (NumberExpression) left;
            if (nl.isInt32()) {
                return nl.asInt32(value) > value;
            } else if (nl.isInt64()) {
                return nl.asInt64(value) > value;
            } else if (nl.isDouble()) {
                return nl.asDouble(value) > value;
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
            NumberExpression nl = (NumberExpression) left;
            if (nl.isInt32()) {
                return nl.asInt32(value) >= value;
            } else if (nl.isInt64()) {
                return nl.asInt64(value) >= value;
            } else if (nl.isDouble()) {
                return nl.asDouble(value) >= value;
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
        } else if (!(obj instanceof Int32Expression)) {
            return false;
        }
        Int32Expression i32e = (Int32Expression) obj;
        return value == i32e.value;
    }

    @Override
    protected int hashing() {
        return Integer.hashCode(value);
    }
}
