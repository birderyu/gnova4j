package gnova.query.expression;

import gnova.core.annotation.NotNull;

import java.util.Date;

public interface DateExpression extends ValueExpression {

    @Override
    default ValueType getValueType() {
        return ValueType.Date;
    }

    @Override
    @NotNull
    Date getValue();

    @Override
    default void checkBy(ValuePredicate predicate) throws IllegalArgumentException {
        // 日期值只能和等于、不等于、大于、大于等于、小于、小于等于相匹配
        if (predicate == ValuePredicate.EQ || predicate == ValuePredicate.NEQ ||
                predicate == ValuePredicate.GT || predicate == ValuePredicate.GTE ||
                predicate == ValuePredicate.LT || predicate == ValuePredicate.LTE) {
            return;
        }
        throw new IllegalArgumentException("日期值只能和等于、不等于、大于、大于等于、小于、小于等于相匹配");
    }

}
