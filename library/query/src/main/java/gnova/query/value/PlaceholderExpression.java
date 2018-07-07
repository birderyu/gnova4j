package gnova.query.value;

import gnova.query.CompareOperator;
import gnova.query.ValueExpression;
import gnova.query.ValueType;

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
    public int sizeOfPlaceholder() {
        return 1;
    }

    @Override
    public String toString() {
        return "?";
    }
}
