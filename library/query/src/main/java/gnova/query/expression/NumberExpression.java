package gnova.query.expression;

import gnova.core.annotation.NotNull;

public abstract class NumberExpression extends AbstractValueExpression {

    @Override
    public abstract Number getValue();

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public void checkBy(ValuePredicate predicate) throws IllegalArgumentException {
        // 数字值只能和等于、不等于、大于、大于等于、小于、小于等于相匹配
        if (predicate == ValuePredicate.EQ || predicate == ValuePredicate.NEQ ||
                predicate == ValuePredicate.GT || predicate == ValuePredicate.GTE ||
                predicate == ValuePredicate.LT || predicate == ValuePredicate.LTE) {
            return;
        }
        throw new IllegalArgumentException("数字值只能和等于、不等于、大于、大于等于、小于、小于等于相匹配");
    }

    @Override
    protected abstract boolean equalsBy(@NotNull Object left);

    @Override
    protected abstract boolean unequalsBy(@NotNull Object left);

    @Override
    protected abstract boolean lessBy(@NotNull Object left);

    @Override
    protected abstract boolean lessEqualsBy(@NotNull Object left);

    @Override
    protected abstract boolean greaterBy(@NotNull Object left);

    @Override
    protected abstract boolean greaterEqualsBy(@NotNull Object left);

}
