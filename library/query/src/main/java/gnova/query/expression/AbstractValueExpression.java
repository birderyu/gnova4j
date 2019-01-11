package gnova.query.expression;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.Geometry;

import java.util.Date;
import java.util.List;

public abstract class AbstractValueExpression implements ValueExpression {

    private int hashCode = 0;

    @Override
    public boolean comparedBy(Object left, ValuePredicate predicate)
            throws IllegalArgumentException {
        if (isKey()) {
            // right is key
            throw new IllegalArgumentException(left + " compare to a key");
        } else if (isPlaceholder()) {
            // right is placeholder
            throw new IllegalArgumentException(left + " compare to a placeholder");
        } else if (placeholderSize() != 0) {
            // right has placeholder
            throw new IllegalArgumentException(left + " compare to a placeholder");
        } else if (left == null || left instanceof NullExpression) {
            // left is null
            // null = null is always true
            // null op any is always false
            return isNull() && predicate == ValuePredicate.EQ;
        } else {
            // left is not null
            if (isNull() && predicate == ValuePredicate.NEQ) {
                // any != null is always true
                return true;
            } else {
                switch (predicate) {
                    case EQ: return equalsBy(left);
                    case NEQ: return unequalsBy(left);
                    case LT: return lessBy(left);
                    case LTE: return lessEqualsBy(left);
                    case GT: return greaterBy(left);
                    case GTE: return greaterEqualsBy(left);
                    case LIKE: return likeBy(left);
                    case IN: return inBy(left);
                    case NIN: return uninBy(left);
                    case INTERSECT: return intersectBy(left);
                    case WITHIN: return withinBy(left);
                }
            }
        }
        return false;
    }

    @Override
    public boolean isNull() {
        return getValueType() == ValueType.Null;
    }

    @Override
    public boolean isBoolean() {
        return getValueType() == ValueType.Boolean;
    }

    @Override
    public boolean isNumber() {
        return getValueType() == ValueType.Int32 ||
                getValueType() == ValueType.Int64 ||
                getValueType() == ValueType.Double;
    }

    @Override
    public boolean isInt32() {
        return getValueType() == ValueType.Int32;
    }

    @Override
    public boolean isInt64() {
        return getValueType() == ValueType.Int64;
    }

    @Override
    public boolean isDouble() {
        return getValueType() == ValueType.Double;
    }

    @Override
    public boolean isString() {
        return getValueType() == ValueType.String;
    }

    @Override
    public boolean isBytes() {
        return getValueType() == ValueType.Bytes;
    }

    @Override
    public boolean isDate() {
        return getValueType() == ValueType.Date;
    }

    @Override
    public boolean isTimestamp() {
        return getValueType() == ValueType.Timestamp;
    }

    @Override
    public boolean isGeometry() {
        return getValueType() == ValueType.Geometry;
    }

    @Override
    public boolean isPlaceholder() {
        return getValueType() == ValueType.Placeholder;
    }

    @Override
    public boolean isList() {
        return getValueType() == ValueType.List;
    }

    @Override
    public boolean isKey() {
        return getValueType() == ValueType.Key;
    }

    @Override
    public boolean asBoolean(boolean defaultValue) {
        return defaultValue;
    }

    @Override
    public int asInt32(int defaultValue) {
        return defaultValue;
    }

    @Override
    public long asInt64(long defaultValue) {
        return defaultValue;
    }

    @Override
    public double asDouble(double defaultValue) {
        return defaultValue;
    }

    @Override
    public Number asNumber() {
        return null;
    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public byte[] asBytes() {
        return null;
    }

    @Override
    public Date asDate()  {
        return null;
    }

    @Override
    public long asTimestamp() {
        return System.currentTimeMillis();
    }

    @Override
    public Geometry asGeometry() {
        return null;
    }

    @Override
    public List asList() { return null; }

    @Override
    public String asKey() {
        return null;
    }

    @Override
    public int placeholderSize() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof ValueExpression)) {
            return false;
        }
        ValueExpression ve = (ValueExpression) obj;
        if (getValueType() != ve.getValueType()) {
            return false;
        }
        return getValue().equals(ve.getValue());
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = hashing();
        }
        return hashCode;
    }

    protected boolean equalsBy(@NotNull Object left) {
        return false;
    }

    protected boolean unequalsBy(@NotNull Object left) {
        return false;
    }

    protected boolean lessBy(@NotNull Object left) {
        return false;
    }

    protected boolean lessEqualsBy(@NotNull Object left) {
        return false;
    }

    protected boolean greaterBy(@NotNull Object left) {
        return false;
    }

    protected boolean greaterEqualsBy(@NotNull Object left) {
        return false;
    }

    protected boolean likeBy(@NotNull Object left) {
        return false;
    }

    protected boolean inBy(@NotNull Object left)
            throws IllegalArgumentException {
        return false;
    }

    protected boolean uninBy(@NotNull Object left)
            throws IllegalArgumentException {
        return false;
    }

    protected boolean intersectBy(@NotNull Object left) {
        return false;
    }

    protected boolean withinBy(@NotNull Object left) {
        return false;
    }

    protected int hashing() {
        return getValue().hashCode();
    }

}