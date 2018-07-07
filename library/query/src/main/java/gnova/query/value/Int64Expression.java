package gnova.query.value;

import gnova.annotation.NotNull;
import gnova.query.ValueType;

public final class Int64Expression
        extends NumberExpression {

    private final long value;

    public Int64Expression(long value) {
        this.value = value;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.Int64;
    }

    @Override
    public Long getValue() {
        return Long.valueOf(value);
    }

    @Override
    public long asInt64(long defaultValue) {
        return value;
    }

    @Override
    public Long asNumber() {
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
            if (nl.isInt64()) {
                return nl.asInt64(value) == value;
            } else if (nl.isDouble()) {
                return nl.asDouble(value) == value;
            }
        } else if (left instanceof Number) {
            return ((Number) left).longValue() == value;
        } else if (left instanceof String) {
            try {
                return Long.valueOf((String) left).longValue() == value;
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
            if (nl.isInt64()) {
                return nl.asInt64(value) != value;
            } else if (nl.isDouble()) {
                return nl.asDouble(value) != value;
            }
        } else if (left instanceof Number) {
            return ((Number) left).longValue() != value;
        } else if (left instanceof String) {
            try {
                return Long.valueOf((String) left).longValue() != value;
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
            if (nl.isInt64()) {
                return nl.asInt64(value) < value;
            } else if (nl.isDouble()) {
                return nl.asDouble(value) < value;
            }
        } else if (left instanceof Number) {
            return ((Number) left).longValue() < value;
        } else if (left instanceof String) {
            try {
                return Long.valueOf((String) left).longValue() < value;
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
            if (nl.isInt64()) {
                return nl.asInt64(value) <= value;
            } else if (nl.isDouble()) {
                return nl.asDouble(value) <= value;
            }
        } else if (left instanceof Number) {
            return ((Number) left).longValue() <= value;
        } else if (left instanceof String) {
            try {
                return Long.valueOf((String) left).longValue() <= value;
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
            if (nl.isInt64()) {
                return nl.asInt64(value) > value;
            } else if (nl.isDouble()) {
                return nl.asDouble(value) > value;
            }
        } else if (left instanceof Number) {
            return ((Number) left).longValue() > value;
        } else if (left instanceof String) {
            try {
                return Long.valueOf((String) left).longValue() > value;
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
            if (nl.isInt64()) {
                return nl.asInt64(value) >= value;
            } else if (nl.isDouble()) {
                return nl.asDouble(value) >= value;
            }
        } else if (left instanceof Number) {
            return ((Number) left).longValue() >= value;
        } else if (left instanceof String) {
            try {
                return Long.valueOf((String) left).longValue() >= value;
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
