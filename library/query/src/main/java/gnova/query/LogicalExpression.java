package gnova.query;

import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;
import gnova.core.function.Getter;

/**
 * 逻辑表达式
 */
@Immutable
public abstract class LogicalExpression
        implements Expression {

    /**
     * 与一个值进行匹配
     *
     * @param getter 值获取器
     * @return 若与该值匹配成功，则返回true，否则返回false
     * @throws IllegalArgumentException 当右值中包含了占位符时，抛出此异常
     */
    public abstract boolean fit(@NotNull Getter getter)
            throws IllegalArgumentException;

    /**
     * 是否恒为真
     *
     * @return
     */
    public abstract boolean isAlwaysTrue();

    /**
     * 是否恒为假
     *
     * @return
     */
    public abstract boolean isAlwaysFalse();

    public boolean isInvariable() {
        return false;
    }

    public boolean isSimple() {
        return false;
    }

    public boolean isMulti() {
        return false;
    }

    public boolean isNon() { return false; }

    public InvariableExpression asInvariable() {
        return isInvariable() ? (InvariableExpression) this : null;
    }

    public SimpleExpression asSimple() {
        return isSimple() ? (SimpleExpression) this : null;
    }

    public MultiExpression asMulti() {
        return isMulti() ? (MultiExpression) this : null;
    }

    public NonExpression asNon() {
        return isNon() ? (NonExpression) this : null;
    }

    @Override
    public boolean isValue() {
        return false;
    }

    @Override
    public LogicalExpression asLogical() {
        return this;
    }

}
