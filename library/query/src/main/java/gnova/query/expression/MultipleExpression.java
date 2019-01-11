package gnova.query.expression;

import gnova.core.annotation.NotNull;
import gnova.core.function.Getter;

/**
 * 复合谓词表达式
 */
public interface MultipleExpression extends PredicateExpression {

    @NotNull
    PredicateExpression getLeft();

    @NotNull
    LogicalPredicate getPredicate();

    @NotNull
    PredicateExpression getRight();

    @Override
    default boolean match(Getter getter) throws IllegalArgumentException {

        if (isAlwaysTrue()) {
            return true;
        } else if (isAlwaysFalse()) {
            return false;
        }

        boolean left = getLeft().match(getter);
        if (getPredicate().shortFor(left)) {
            // 短路，不需要再计算右侧的值
            return left;
        }
        boolean right = getRight().match(getter);
        if (getPredicate().isAnd()) {
            // 逻辑与操作
            return left && right;
        } else if (getPredicate().isOr()) {
            // 逻辑或操作
            return left || right;
        } else if (getPredicate().isXor()) {
            // 逻辑异或操作
            return (left && right) || (!left && !right);
        }
        return false;
    }

    @Override
    default boolean isAlwaysTrue() {
        boolean left = getLeft().isAlwaysTrue();
        if (getPredicate().shortFor(left)) {
            // 短路，不需要再计算右侧的值
            return left;
        }
        boolean right = getRight().isAlwaysTrue();
        if (getPredicate().isAnd()) {
            // 逻辑与操作
            return left && right;
        } else if (getPredicate().isOr()) {
            // 逻辑或操作
            return left || right;
        } else if (getPredicate().isXor()) {
            // 逻辑异或操作
            return (left && right) ||
                    (getLeft().isAlwaysFalse() && getRight().isAlwaysFalse());
        }
        return false;
    }

    @Override
    default boolean isAlwaysFalse() {
        boolean left = getLeft().isAlwaysFalse();
        // false and xxx is false : and isShort4 false
        // true or xxx is true : or isShort4 true
        // l is false, lo is and, and isShort4 true? false
        // l is true, lo is and, and isShort4 false? true, return true || any is true
        // l is false, lo is or, or isShort4 true? true, return false || any is false
        // l is true, lo is or, or isShort4 false? false
        if (getPredicate().shortFor(!left)) {
            // 短路，不需要再计算右侧的值
            return left;
        }
        boolean right = getRight().isAlwaysFalse();
        if (getPredicate().isAnd()) {
            // 逻辑与操作
            return left || right;
        } else if (getPredicate().isOr()) {
            // 逻辑或操作
            return left && right;
        } else if (getPredicate().isXor()) {
            // 逻辑异或操作
            return (left && right) ||
                    (getLeft().isAlwaysTrue() && getRight().isAlwaysTrue());
        }
        return false;
    }

    @Override
    default boolean isMultiple() {
        return true;
    }

    @Override
    default MultipleExpression asMultiple() {
        return this;
    }

    @Override
    default int placeholderSize() {
        return getLeft().placeholderSize()
                + getRight().placeholderSize();
    }


}
