package gnova.query.expression;

public final class PlaceholderExpression
        extends AbstractValueExpression {

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
    public void checkBy(ValuePredicate predicate) throws IllegalArgumentException {
        // 占位符可以和所有符号相匹配
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
