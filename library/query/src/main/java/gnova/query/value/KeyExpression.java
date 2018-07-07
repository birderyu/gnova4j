package gnova.query.value;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;
import gnova.query.CompareOperator;
import gnova.query.ValueExpression;
import gnova.query.ValueType;

public final class KeyExpression
        extends ValueExpression {

    @NotNull
    private final String name;

    public KeyExpression(@Checked String name) {
        this.name = name;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.Key;
    }

    @Override
    public String getValue() {
        return name;
    }

    @Override
    public String checkBy(CompareOperator co) {
        // 键值可以和所有符号相匹配
        return null;
    }

    @Override
    public String asKey() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
