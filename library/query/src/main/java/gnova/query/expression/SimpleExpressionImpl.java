package gnova.query.expression;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;

public final class SimpleExpressionImpl extends AbstractPredicateExpression implements SimpleExpression {

    /**
     * 左值表达式
     */
    @NotNull
    private final ValueExpression left;

    /**
     * 谓词
     */
    @NotNull
    private final ValuePredicate predicate;

    /**
     * 右值表达式
     */
    @NotNull
    private final ValueExpression right;

    /**
     * 构造一个简单逻辑表达式
     *
     * @param left 左值表达式，可以为key，或一个明确的值，不允许包含占位符
     * @param predicate 比较操作符，必须和左右值相匹配
     * @param right 右值表达式，可以为一个明确的值，或包含占位符，不可以为key
     */
    public SimpleExpressionImpl(@Checked ValueExpression left,
                                @Checked ValuePredicate predicate,
                                @Checked ValueExpression right) {
        this.left = left;
        this.predicate = predicate;
        this.right = right;
    }

    @Override
    public boolean isSimple() {
        return true;
    }

    @Override
    public ValueExpression getLeft() {
        return left;
    }

    @Override
    public ValuePredicate getPredicate() {
        return predicate;
    }

    @Override
    public ValueExpression getRight() {
        return right;
    }

    @Override
    public String toString() {
        return getLeft().toString() + " " + getPredicate().toString() + " " + getRight().toString();
    }

}
