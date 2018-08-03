package gnova.query.expression;

import gnova.core.annotation.Checked;
import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;
import gnova.core.function.Getter;

/**
 * 简单逻辑表达式
 */
@Immutable
public final class SimpleExpression
        extends LogicalExpression {

    /**
     * 左值表达式
     */
    @NotNull
    private final ValueExpression leftValue;

    /**
     * 比较操作符
     */
    @NotNull
    private final CompareOperator compareOperator;

    /**
     * 右值表达式
     */
    @NotNull
    private final ValueExpression rightValue;

    /**
     * 构造一个简单逻辑表达式
     *
     * @param lv 左值表达式，可以为key，或一个明确的值，不允许包含占位符
     * @param co 比较操作符，必须和左右值相匹配
     * @param rv 右值表达式，可以为一个明确的值，或包含占位符，不可以为key
     */
    public SimpleExpression(@Checked ValueExpression lv,
                            @Checked CompareOperator co,
                            @Checked ValueExpression rv) {
        this.leftValue = lv;
        this.compareOperator = co;
        this.rightValue = rv;
    }

    public ValueExpression getLeftValue() {
        return leftValue;
    }

    public CompareOperator getCompareOperator() {
        return compareOperator;
    }

    public ValueExpression getRightValue() {
        return rightValue;
    }

    @Override
    public int placeholderSize() {
        return rightValue.placeholderSize();
    }

    @Override
    public String toString() {
        return leftValue.toString()
                + " "
                + compareOperator.toString()
                + " "
                + rightValue.toString() ;
    }

    /**
     *
     * @param getter 值获取器
     * @return
     * @throws IllegalArgumentException 当右值中包含了占位符时，抛出此异常
     */
    @Override
    public boolean fit(Getter getter) throws IllegalArgumentException {
        if (leftValue.isKey()) {
            Object left = getter.getValue(leftValue.asKey());
            return rightValue.comparedBy(left, compareOperator);
        } else if (isAlwaysTrue()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isAlwaysTrue() {
        if (!leftValue.isKey() && rightValue.placeholderSize() == 0) {
            // 仅当左值不为key且右值不含占位符时，可以直接进行比较
            return rightValue.comparedBy(leftValue, compareOperator);
        }
        return false;
    }

    @Override
    public boolean isAlwaysFalse() {
        if (!leftValue.isKey() && rightValue.placeholderSize() != 0) {
            // 仅当左值不为key且右值不含占位符时，可以直接进行比较
            return !rightValue.comparedBy(leftValue, compareOperator);
        }
        return false;
    }

    @Override
    public boolean isSimple() {
        return true;
    }

    @Override
    public SimpleExpression asSimple() {
        return this;
    }

}
