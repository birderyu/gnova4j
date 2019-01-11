package gnova.query.expression;

import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;
import gnova.core.function.Getter;

/**
 * 简单谓词表达式
 */
@Immutable
public interface SimpleExpression extends PredicateExpression {

    @NotNull
    ValueExpression getLeft();

    @NotNull
    ValuePredicate getPredicate();

    @NotNull
    ValueExpression getRight();

    @Override
    default int placeholderSize() {
        return getRight().placeholderSize();
    }

    /**
     *
     * @param getter 值获取器
     * @return
     * @throws IllegalArgumentException 当右值中包含了占位符时，抛出此异常
     */
    @Override
    default boolean match(Getter getter) throws IllegalArgumentException {

        if (isAlwaysTrue()) {
            return true;
        } else if (isAlwaysFalse()) {
            return false;
        } else if (getLeft().isKey()) {
            Object left = getter.getValue(getLeft().asKey());
            return getRight().comparedBy(left, getPredicate());
        } else {
            return false;
        }
    }

    @Override
    default boolean isAlwaysTrue() {
        if (!getLeft().isKey() && getRight().placeholderSize() == 0) {
            // 仅当左值不为key且右值不含占位符时，可以直接进行比较
            Object left = getLeft().getValue();
            return getRight().comparedBy(left, getPredicate());
        }
        return false;
    }

    @Override
    default boolean isAlwaysFalse() {
        if (!getLeft().isKey() && getRight().placeholderSize() != 0) {
            // 仅当左值不为key且右值不含占位符时，可以直接进行比较
            Object left = getLeft().getValue();
            return !getRight().comparedBy(left, getPredicate());
        }
        return false;
    }

    @Override
    default boolean isSimple() {
        return true;
    }

    @Override
    default SimpleExpression asSimple() {
        return this;
    }

}
