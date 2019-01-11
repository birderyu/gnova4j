package gnova.query.expression;

import gnova.core.annotation.NotNull;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * 时间戳
 */
public final class TimestampExpression extends AbstractValueExpression {

    /**
     * 毫秒时间戳
     */
    private final long value;

    public TimestampExpression() {
        this.value = System.currentTimeMillis();
    }

    public TimestampExpression(long value) {
        this.value = value;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.Timestamp;
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
    public long asTimestamp() {
        return value;
    }

    @Override
    public void checkBy(ValuePredicate predicate) throws IllegalArgumentException {
        // 时间戳值只能和等于、不等于、大于、大于等于、小于、小于等于相匹配
        if (predicate == ValuePredicate.EQ || predicate == ValuePredicate.NEQ ||
                predicate == ValuePredicate.GT || predicate == ValuePredicate.GTE ||
                predicate == ValuePredicate.LT || predicate == ValuePredicate.LTE) {
            return;
        }
        throw new IllegalArgumentException("时间戳只能和等于、不等于、大于、大于等于、小于、小于等于相匹配");
    }

    @Override
    public String toString() {
        return "#" + String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof TimestampExpression)) {
            return false;
        }
        TimestampExpression te = (TimestampExpression) obj;
        return value == te.value;
    }

    @Override
    protected int hashing() {
        return Long.hashCode(value);
    }

    @Override
    protected boolean equalsBy(@NotNull Object left) {
        if (left instanceof Number) {
            return ((Number) left).longValue() == value;
        } else if (left instanceof TimestampExpression) {
            return ((TimestampExpression) left).value == value;
        } else if (left instanceof Date) {
            return ((Date) left).getTime() == value;
        } else if (left instanceof DateTime) {
            return ((DateTime) left).getMillis() == value;
        } else if (left instanceof DateExpression) {
            return ((DateExpression) left).asInt64(0L) == value;
        }
        return false;
    }

    @Override
    protected boolean unequalsBy(@NotNull Object left) {
        if (left instanceof Number) {
            return ((Number) left).longValue() != value;
        } else if (left instanceof TimestampExpression) {
            return ((TimestampExpression) left).value != value;
        } else if (left instanceof Date) {
            return ((Date) left).getTime() != value;
        } else if (left instanceof DateTime) {
            return ((DateTime) left).getMillis() != value;
        } else if (left instanceof DateExpression) {
            return ((DateExpression) left).asInt64(0L) != value;
        }
        return false;
    }

    @Override
    protected boolean lessBy(@NotNull Object left) {
        if (left instanceof Number) {
            return ((Number) left).longValue() < value;
        } else if (left instanceof TimestampExpression) {
            return ((TimestampExpression) left).value < value;
        } else if (left instanceof Date) {
            return ((Date) left).getTime() < value;
        } else if (left instanceof DateTime) {
            return ((DateTime) left).getMillis() < value;
        } else if (left instanceof DateExpression) {
            return ((DateExpression) left).asInt64(0L) < value;
        }
        return false;
    }

    @Override
    protected boolean lessEqualsBy(@NotNull Object left) {
        if (left instanceof Number) {
            return ((Number) left).longValue() <= value;
        } else if (left instanceof TimestampExpression) {
            return ((TimestampExpression) left).value <= value;
        } else if (left instanceof Date) {
            return ((Date) left).getTime() <= value;
        } else if (left instanceof DateTime) {
            return ((DateTime) left).getMillis() <= value;
        } else if (left instanceof DateExpression) {
            return ((DateExpression) left).asInt64(0L) <= value;
        }
        return false;
    }

    @Override
    protected boolean greaterBy(@NotNull Object left) {
        if (left instanceof Number) {
            return ((Number) left).longValue() > value;
        } else if (left instanceof TimestampExpression) {
            return ((TimestampExpression) left).value > value;
        } else if (left instanceof Date) {
            return ((Date) left).getTime() > value;
        } else if (left instanceof DateTime) {
            return ((DateTime) left).getMillis() > value;
        } else if (left instanceof DateExpression) {
            return ((DateExpression) left).asInt64(0L) > value;
        }
        return false;
    }

    @Override
    protected boolean greaterEqualsBy(@NotNull Object left) {
        if (left instanceof Number) {
            return ((Number) left).longValue() >= value;
        } else if (left instanceof TimestampExpression) {
            return ((TimestampExpression) left).value >= value;
        } else if (left instanceof Date) {
            return ((Date) left).getTime() >= value;
        } else if (left instanceof DateTime) {
            return ((DateTime) left).getMillis() >= value;
        } else if (left instanceof DateExpression) {
            return ((DateExpression) left).asInt64(0L) >= value;
        }
        return false;
    }
}
