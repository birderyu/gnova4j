package gnova.query.value;

import gnova.core.annotation.NotNull;
import gnova.query.CompareOperator;
import gnova.query.ValueExpression;
import gnova.query.ValueType;

public final class BooleanExpression
        extends ValueExpression {

    public static final BooleanExpression TRUE = new BooleanExpression(true);

    public static final BooleanExpression FALSE = new BooleanExpression(false);

    private final boolean value;

    public BooleanExpression(boolean value) {
        this.value = value;
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
    public String checkBy(CompareOperator co) {
        // 布尔值只能和等于、不等于相匹配
        if (co == CompareOperator.EQ || co == CompareOperator.NEQ) {
            return null;
        }
        return "布尔值只能和等于、不等于相匹配";
    }

    @Override
    public boolean asBoolean(boolean defaultValue) {
        return value;
    }

    @Override
    protected boolean equalsBy(@NotNull Object left) {
        if (left instanceof BooleanExpression) {
            return ((BooleanExpression) left).value == value;
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
            return ((BooleanExpression) left).value != value;
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
}
