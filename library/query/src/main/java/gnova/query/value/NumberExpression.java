package gnova.query.value;

import gnova.core.annotation.NotNull;
import gnova.query.CompareOperator;
import gnova.query.ValueExpression;

public abstract class NumberExpression
        extends ValueExpression {

    @Override
    public abstract Number getValue();

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public String checkBy(CompareOperator co) {
        // 数字值只能和等于、不等于、大于、大于等于、小于、小于等于相匹配
        if (co == CompareOperator.EQ || co == CompareOperator.NEQ ||
                co == CompareOperator.GT || co == CompareOperator.GTE ||
                co == CompareOperator.LT || co == CompareOperator.LTE) {
            return null;
        }
        return "数字值只能和等于、不等于、大于、大于等于、小于、小于等于相匹配";
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
