package gnova.query.value;

import gnova.query.CompareOperator;
import gnova.query.ValueExpression;
import gnova.query.ValueType;

public final class NullExpression
        extends ValueExpression {

    public static final NullExpression NULL = new NullExpression();

    @Override
    public ValueType getValueType() {
        return ValueType.Null;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String checkBy(CompareOperator co) {
        // 空值只能和等于、不等于相匹配
        // 且除了null = null为true，其余都为false
        if (co == CompareOperator.EQ || co == CompareOperator.NEQ) {
            return null;
        }
        return "空值只能和等于、不等于相匹配";
    }

    @Override
    public String toString() {
        return "null";
    }
}
