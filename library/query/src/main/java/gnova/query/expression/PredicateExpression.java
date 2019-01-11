package gnova.query.expression;

import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;
import gnova.core.function.Getter;

import java.util.Map;
import java.util.function.Predicate;

/**
 * 谓词表达式
 */
@Immutable
public interface PredicateExpression extends Expression {

    /**
     * 匹配值获取器
     *
     * @param getter 值获取器，不允许为null
     * @return 若与该值匹配成功，则返回true，否则返回false
     * @throws IllegalArgumentException 若表达式中包含了占位符时，抛出此异常
     * @see Getter
     */
    boolean match(@NotNull Getter getter) throws IllegalArgumentException;

    default boolean match(@NotNull Map<String, Object> map) throws IllegalArgumentException {
        return match(new Getter() {
            @Override
            public <T> T getValue(String key) {
                return (T) map.get(key);
            }
        });
    }

    /**
     * 转换为谓词函数接口
     *
     * @return
     */
    @NotNull
    default Predicate<? extends Getter> toFunction() {
        return getter -> PredicateExpression.this.match(getter);
    }

    /**
     * 谓词表达式是否恒为真
     *
     * @return
     */
    boolean isAlwaysTrue();

    /**
     * 谓词表达式是否恒为假
     *
     * @return
     */
    boolean isAlwaysFalse();

    boolean isInvariable();

    boolean isSimple();

    boolean isMultiple();

    boolean isNegative();

    InvariableExpression asInvariable();

    SimpleExpression asSimple();

    MultipleExpression asMultiple();

    NegativeExpression asNegative();

    @Override
    default boolean isValue() {
        return false;
    }

    @Override
    default PredicateExpression asPredicate() {
        return this;
    }

    @Override
    default ValueExpression asValue() {
        return null;
    }

}
