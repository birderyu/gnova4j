package gnova.query.expression;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.Geometry;

import java.util.Date;
import java.util.List;

/**
 * 值表达式
 *
 * <p>值表达式表示一个具体的值，如：
 * <pre>{@code
 *      null
 *      true
 *      'birderyu'
 *      18
 *      (null, true, 'birderyu', 18)
 * }</pre>
 *
 * <p>值表达式包括以下几种类型：
 * <br>{@link NullExpression 空值表达式}，表示一个默认值或缺省值；
 *
 * @see NullExpression
 * @see BooleanExpression
 * @see NumberExpression
 * @see StringExpression
 * @see GeometryExpression
 * @see ListExpression
 * @see KeyExpression
 * @see PlaceholderExpression
 */
public interface ValueExpression extends Expression {

    /**
     * 检查当前值是否能够和值谓词做匹配
     *
     * @param predicate 值谓词，不允许为null
     * @throws IllegalArgumentException 若匹配失败，则抛出此异常
     */
    void checkBy(@NotNull ValuePredicate predicate) throws IllegalArgumentException;

    /**
     * 将值本身作为右值，与左值进行被比较操作
     *
     * <p>相当于判断 left predicate right 的值
     *
     * @param left 左值
     * @param predicate 值谓词
     * @return 若 left predicate right 成立，则返回true，否则返回false
     * @throws IllegalArgumentException
     *          若本身为主键，或包含了占位符，则抛出此异常
     *          若left为主键，或包含了占位符，则抛出此异常
     */
    boolean comparedBy(Object left, ValuePredicate predicate) throws IllegalArgumentException;

    /**
     * 获取值表达式的类型
     *
     * @return 值表达式的类型，不会返回null
     */
    @NotNull
    ValueType getValueType();

    boolean isNull();

    boolean isBoolean();

    boolean isNumber();

    boolean isInt32();

    boolean isInt64();

    boolean isDouble();

    boolean isString();

    boolean isBytes();

    boolean isDate();

    boolean isTimestamp();

    boolean isGeometry();

    boolean isPlaceholder();

    boolean isList();

    boolean isKey();

    Object getValue();

    boolean asBoolean(boolean defaultValue);

    int asInt32(int defaultValue);

    long asInt64(long defaultValue);

    double asDouble(double defaultValue);

    Number asNumber();

    String asString();

    byte[] asBytes();

    Date asDate();

    long asTimestamp();

    Geometry asGeometry();

    List asList();

    String asKey();

    @Override
    default boolean isValue() {
        return true;
    }

    @Override
    default ValueExpression asValue() {
        return this;
    }

    @Override
    default PredicateExpression asPredicate() {
        return null;
    }

}
