package gnova.query.expression;

import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;

/**
 * 表达式
 */
@Immutable
public interface Expression {

    /**
     * 获取表达式中占位符的数量
     *
     * @return
     */
    int placeholderSize();

    /**
     * 是否是值表达式
     *
     * @return
     */
    boolean isValue();

    /**
     * 转换为值表达式
     *
     * @return
     */
    default ValueExpression asValue() {
        return isValue() ? (ValueExpression) this : null;
    }

    /**
     * 转换为逻辑表达式
     *
     * @return
     */
    default LogicalExpression asLogical() {
        return isValue() ? null : (LogicalExpression) this;
    }

    @Override
    @NotNull
    String toString();

}
