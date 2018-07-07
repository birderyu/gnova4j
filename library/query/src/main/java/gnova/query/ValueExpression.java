package gnova.query;

import gnova.annotation.NotNull;
import gnova.geometry.model.Geometry;
import gnova.query.value.*;

import java.util.List;

/**
 * 值表达式
 */
public abstract class ValueExpression
        extends Expression {

    public static ValueExpression tryBuildValue(Object val) {
        if (val == null) {
            return Builder.buildNull();
        } else if (val instanceof ValueExpression) {
            return (ValueExpression) val;
        } else if (val instanceof Boolean) {
            return Builder.buildBoolean(((Boolean) val).booleanValue());
        } else if (val instanceof Number) {
            return Builder.buildNumber((Number) val);
        } else if (val instanceof String) {
            return Builder.buildString((String) val);
        } else if (val instanceof Geometry) {
            return Builder.buildGeometry((Geometry) val);
        }
        // TODO
        return null;
    }

    @Override
    public boolean isValue() {
        return true;
    }

    @Override
    public ValueExpression asValue() {
        return this;
    }

    public abstract ValueType getValueType();

    public abstract Object getValue();

    /**
     * 检查当前值是否能够和比较操作符co做匹配
     *
     * @param co
     * @return 若可以匹配，则返回null，否则返回错误信息
     */
    public abstract String checkBy(CompareOperator co);

    /**
     * 获取占位符的个数
     *
     * Key认为是一个占位符
     *
     * @return
     */
    @Override
    public int sizeOfPlaceholder() {
        return 0;
    }

    /**
     * 将值本身作为右值，与左值进行被比较操作
     *
     * 相当于判断 left co right 的值
     *
     * @param left 左值
     * @param co 比较操作符
     * @return 若 left co right 成立，则返回true，否则返回false
     * @throws UnsupportedOperationException
     *          若本身为主键，或包含了占位符，则抛出此异常
     *          若left为主键，或包含了占位符，则抛出此异常
     */
    public boolean comparedBy(Object left, CompareOperator co)
            throws UnsupportedOperationException {
        if (isKey()) {
            // right is key
            throw new UnsupportedOperationException(left + " compare to a key");
        } else if (isPlaceholder()) {
            // right is placeholder
            throw new UnsupportedOperationException(left + " compare to a placeholder");
        } else if (sizeOfPlaceholder() != 0) {
            // right has placeholder
            throw new UnsupportedOperationException(left + " compare to a placeholder");
        } else if (left == null || left instanceof NullExpression) {
            // left is null
            // null = null is always true
            // null op any is always false
            return isNull() && co == CompareOperator.EQ;
        } else {
            // left is not null
            if (isNull() && co == CompareOperator.NEQ) {
                // any != null is always true
                return true;
            } else {
                switch (co) {
                    case EQ: return equalsBy(left);
                    case NEQ: return unequalsBy(left);
                    case LT: return lessBy(left);
                    case LTE: return lessEqualsBy(left);
                    case GT: return greaterBy(left);
                    case GTE: return greaterEqualsBy(left);
                    case LIKE: return likeBy(left);
                    case IN: return inBy(left);
                    case NIN: return uninBy(left);
                    case INTERSECT: return intersectBy(left);
                    case WITHIN: return withinBy(left);
                }
            }
        }
        return false;
    }

    public boolean isNull() {
        return getValueType() == ValueType.Null;
    }

    public boolean isBoolean() {
        return getValueType() == ValueType.Boolean;
    }

    public boolean isNumber() {
        return getValueType() == ValueType.Int32 ||
                getValueType() == ValueType.Int64 ||
                getValueType() == ValueType.Double;
    }

    public boolean isInt32() {
        return getValueType() == ValueType.Int32;
    }

    public boolean isInt64() {
        return getValueType() == ValueType.Int64;
    }

    public boolean isDouble() {
        return getValueType() == ValueType.Double;
    }

    public boolean isString() {
        return getValueType() == ValueType.String;
    }

    public boolean isGeometry() {
        return getValueType() == ValueType.Geometry;
    }

    public boolean isPlaceholder() {
        return getValueType() == ValueType.Placeholder;
    }

    public boolean isList() {
        return getValueType() == ValueType.List;
    }

    public boolean isKey() {
        return getValueType() == ValueType.Key;
    }

    public boolean asBoolean(boolean defaultValue) {
        return defaultValue;
    }

    public int asInt32(int defaultValue) {
        return defaultValue;
    }

    public long asInt64(long defaultValue) {
        return defaultValue;
    }

    public double asDouble(double defaultValue) {
        return defaultValue;
    }

    public Number asNumber() {
        return null;
    }

    public String asString() {
        return null;
    }

    public Geometry asGeometry() {
        return null;
    }

    public List asList() { return null; }

    public String asKey() {
        return null;
    }

    protected boolean equalsBy(@NotNull Object left) {
        return false;
    }

    protected boolean unequalsBy(@NotNull Object left) {
        return false;
    }

    protected boolean lessBy(@NotNull Object left) {
        return false;
    }

    protected boolean lessEqualsBy(@NotNull Object left) {
        return false;
    }

    protected boolean greaterBy(@NotNull Object left) {
        return false;
    }

    protected boolean greaterEqualsBy(@NotNull Object left) {
        return false;
    }

    protected boolean likeBy(@NotNull Object left) {
        return false;
    }

    protected boolean inBy(@NotNull Object left) {
        return false;
    }

    protected boolean uninBy(@NotNull Object left) {
        return false;
    }

    protected boolean intersectBy(@NotNull Object left) {
        return false;
    }

    protected boolean withinBy(@NotNull Object left) {
        return false;
    }

}
