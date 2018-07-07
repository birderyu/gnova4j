package gnova.query;

import gnova.annotation.NotNull;
import gnova.function.Getter;

/**
 * 复合逻辑表达式
 */
public final class MultiExpression
        extends LogicalExpression {

    @NotNull
    private final LogicalExpression leftExpression;

    @NotNull
    private final LogicalOperator logicalOperator;

    @NotNull
    private final LogicalExpression rightExpression;

    public MultiExpression(@NotNull LogicalExpression le,
                           @NotNull LogicalOperator lo,
                           @NotNull LogicalExpression re) {
        this.leftExpression = le;
        this.logicalOperator = lo;
        this.rightExpression = re;
    }

    public LogicalExpression getLeftExpression() {
        return leftExpression;
    }

    public LogicalOperator getLogicalOperator() {
        return logicalOperator;
    }

    public LogicalExpression getRightExpression() {
        return rightExpression;
    }

    @Override
    public boolean fit(Getter getter)
            throws UnsupportedOperationException {

        if (isAlwaysTrue()) {
            return true;
        } else if (isAlwaysFalse()) {
            return false;
        }

        boolean l = leftExpression.fit(getter);
        if (logicalOperator.shortFor(l)) {
            // 短路，不需要再计算右侧的值
            return l;
        }
        boolean r = rightExpression.fit(getter);
        if (logicalOperator.isAnd()) {
            // 逻辑与操作
            return l && r;
        } else if (logicalOperator.isOr()) {
            // 逻辑或操作
            return l || r;
        } else if (logicalOperator.isXor()) {
            // 逻辑异或操作
            return (l && r) || (!l && !r);
        }
        return false;
    }

    @Override
    public boolean isAlwaysTrue() {
        boolean l = leftExpression.isAlwaysTrue();
        if (logicalOperator.shortFor(l)) {
            // 短路，不需要再计算右侧的值
            return l;
        }
        boolean r = rightExpression.isAlwaysTrue();
        if (logicalOperator.isAnd()) {
            // 逻辑与操作
            return l && r;
        } else if (logicalOperator.isOr()) {
            // 逻辑或操作
            return l || r;
        } else if (logicalOperator.isXor()) {
            // 逻辑异或操作
            return (l && r) ||
                    (leftExpression.isAlwaysFalse()
                            && rightExpression.isAlwaysFalse());
        }
        return false;
    }

    @Override
    public boolean isAlwaysFalse() {
        boolean l = leftExpression.isAlwaysFalse();
        // false and xxx is false : and isShort4 false
        // true or xxx is true : or isShort4 true
        // l is false, lo is and, and isShort4 true? false
        // l is true, lo is and, and isShort4 false? true, return true || any is true
        // l is false, lo is or, or isShort4 true? true, return false || any is false
        // l is true, lo is or, or isShort4 false? false
        if (logicalOperator.shortFor(!l)) {
            // 短路，不需要再计算右侧的值
            return l;
        }
        boolean r = rightExpression.isAlwaysFalse();
        if (logicalOperator.isAnd()) {
            // 逻辑与操作
            return l || r;
        } else if (logicalOperator.isOr()) {
            // 逻辑或操作
            return l && r;
        } else if (logicalOperator.isXor()) {
            // 逻辑异或操作
            return (l && r) ||
                    (leftExpression.isAlwaysTrue()
                            && rightExpression.isAlwaysTrue());
        }
        return false;
    }

    @Override
    public boolean isMulti() {
        return true;
    }

    @Override
    public MultiExpression asMulti() {
        return this;
    }

    @Override
    public int sizeOfPlaceholder() {
        return leftExpression.sizeOfPlaceholder()
                + rightExpression.sizeOfPlaceholder();
    }

    @Override
    public String toString() {
        return leftExpression.toString()
                + " "
                + logicalOperator.toString()
                + " "
                + toString(rightExpression);
    }

    private String toString(LogicalExpression le) {
        if (le.isSimple() || le.isNon()) {
            return le.toString();
        }
        return "(" + le.toString() + ")";
    }
}
