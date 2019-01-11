package gnova.query.expression;

import gnova.core.annotation.NotNull;
import gnova.core.function.Getter;

/**
 * 否定谓词表达式
 */
public interface NegativeExpression extends PredicateExpression {

    @NotNull
    PredicateExpression getPositive();

    @Override
    default boolean match(@NotNull Getter getter)
            throws IllegalArgumentException {
        return !getPositive().match(getter);
    }

    @Override
    default boolean isAlwaysTrue() {
        return getPositive().isAlwaysFalse();
    }

    @Override
    default boolean isAlwaysFalse() {
        return getPositive().isAlwaysTrue();
    }

    @Override
    default int placeholderSize() {
        return getPositive().placeholderSize();
    }

    @Override
    default boolean isNegative() { return true; }

    @Override
    default NegativeExpression asNegative() {
        return this;
    }

}
