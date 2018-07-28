package gnova.query.expression.value;

import gnova.query.expression.CompareOperator;
import gnova.query.expression.ValueExpression;
import gnova.query.expression.ValueType;

public final class PlaceholderExpression
        extends ValueExpression {

    public static final PlaceholderExpression PLACEHOLDER = new PlaceholderExpression();

    @Override
    public ValueType getValueType() {
        return ValueType.Placeholder;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String checkBy(CompareOperator co) {
        // 占位符可以和所有符号相匹配
        return null;
    }

    @Override
    public int placeholderSize() {
        return 1;
    }

    @Override
    public String toString() {
        return "?";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return obj instanceof PlaceholderExpression;
    }

    @Override
    protected int hashing() {
        return 54321;
    }
}
