package gnova.query.expression;

import gnova.core.annotation.NotNull;

public final class BooleanExpressionImpl
        extends AbstractValueExpression implements BooleanExpression {

    public static final BooleanExpression TRUE = new BooleanExpressionImpl(true);

    public static final BooleanExpression FALSE = new BooleanExpressionImpl(false);

    private final boolean value;

    public BooleanExpressionImpl(boolean value) {
        this.value = value;
    }

    @Override
    public InvariableExpression toInvariable() {
        return value ? InvariableExpression.ALWAYS_TRUE : InvariableExpression.ALWAYS_FALSE;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.Boolean;
    }

    @Override
    public Boolean getValue() {
        return Boolean.valueOf(value);
    }

    @Override
    public boolean asBoolean(boolean defaultValue) {
        return value;
    }

    @Override
    protected boolean equalsBy(@NotNull Object left) {
        if (left instanceof BooleanExpression) {
            return ((BooleanExpression) left).asBoolean(value) == value;
        } else if (left instanceof Boolean) {
            return ((Boolean) left) == value;
        } else if (left instanceof String) {
            String svalue = (String) left;
            if ("true".equalsIgnoreCase(svalue)) {
                return true == value;
            } else if ("false".equalsIgnoreCase(svalue)) {
                return false == value;
            }
        }
        return false;
    }

    @Override
    protected boolean unequalsBy(@NotNull Object left) {
        if (left instanceof BooleanExpression) {
            return ((BooleanExpression) left).asBoolean(value) != value;
        } else if (left instanceof String) {
            String sl = (String) left;
            if ("true".equalsIgnoreCase(sl)) {
                return true != value;
            } else if ("false".equalsIgnoreCase(sl)) {
                return false != value;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof BooleanExpression)) {
            return false;
        }
        BooleanExpression be = (BooleanExpression) obj;
        return value == be.asBoolean(value);
    }

    @Override
    protected int hashing() {
        return Boolean.hashCode(value);
    }

}
