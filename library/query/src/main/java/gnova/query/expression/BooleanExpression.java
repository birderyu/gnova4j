package gnova.query.expression;

import gnova.core.annotation.NotNull;

public interface BooleanExpression extends ValueExpression {

    @NotNull
    InvariableExpression toInvariable();

    @Override
    default ValueType getValueType() {
        return ValueType.Boolean;
    }

    @Override
    Boolean getValue();

    @Override
    default void checkBy(ValuePredicate predicate) throws IllegalArgumentException {
        // 布尔值只能和等于、不等于相匹配
        if (predicate == ValuePredicate.EQ || predicate == ValuePredicate.NEQ) {
            return;
        }
        throw new IllegalArgumentException("布尔值只能和等于、不等于相匹配");
    }
}
