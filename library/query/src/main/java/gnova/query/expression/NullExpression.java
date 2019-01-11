package gnova.query.expression;

public final class NullExpression
        extends AbstractValueExpression {

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
    public void checkBy(ValuePredicate predicate) throws IllegalArgumentException {
        // 空值只能和等于、不等于相匹配
        // 且除了null = null为true，其余都为false
        if (predicate == ValuePredicate.EQ || predicate == ValuePredicate.NEQ) {
            return;
        }
        throw new IllegalArgumentException("空值只能和等于、不等于相匹配");
    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return obj instanceof NullExpression;
    }

    @Override
    protected int hashing() {
        return 12345;
    }
}
