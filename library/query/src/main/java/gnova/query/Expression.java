package gnova.query;

import gnova.annotation.Immutable;
import gnova.annotation.NotNull;

/**
 * 表达式
 */
@Immutable
public abstract class Expression {

    /**
     * 获取表达式中占位符的数量
     *
     * @return
     */
    public abstract int sizeOfPlaceholder();

    /**
     * 是否是值表达式
     *
     * @return
     */
    public abstract boolean isValue();

    /**
     * 转换为值表达式
     *
     * @return
     */
    public ValueExpression asValue() {
        return isValue() ? (ValueExpression) this : null;
    }

    /**
     * 转换为逻辑表达式
     *
     * @return
     */
    public LogicalExpression asLogical() {
        return isValue() ? null : (LogicalExpression) this;
    }

    @Override
    @NotNull
    public abstract String toString();

}
