package gnova.query.expression;

import gnova.core.annotation.NotNull;

public interface BytesExpression extends ValueExpression {

    boolean isEmpty();

    @Override
    default ValueType getValueType() {
        return ValueType.Bytes;
    }

    @Override
    default void checkBy(ValuePredicate predicate) throws IllegalArgumentException {
        // 字节数组值只能和等于、不等于相匹配
        if (predicate == ValuePredicate.EQ || predicate == ValuePredicate.NEQ) {
            return;
        }
        throw new IllegalArgumentException("字节串值只能和等于、不等于相匹配");
    }

    @Override
    @NotNull
    byte[] getValue();

}
