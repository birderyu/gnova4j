package gnova.query.expression.value;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;
import gnova.query.expression.CompareOperator;
import gnova.query.expression.ValueExpression;
import gnova.query.expression.ValueType;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof KeyExpression)) {
            return false;
        }
        KeyExpression key = (KeyExpression) obj;
        return name.equals(key.name);
    }

    @Override
    protected int hashing() {
        return name.hashCode();
    }
}
