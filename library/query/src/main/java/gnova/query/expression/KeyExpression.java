package gnova.query.expression;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;

public final class KeyExpression
        extends AbstractValueExpression {

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
    @NotNull
    public String getValue() {
        return name;
    }

    @Override
    public void checkBy(ValuePredicate predicate) throws IllegalArgumentException {
        // 键值可以和所有符号相匹配
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
