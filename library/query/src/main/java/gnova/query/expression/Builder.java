package gnova.query.expression;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.Geometry;
import gnova.geometry.model.GeometryType;
import gnova.query.expression.value.*;

import java.math.BigDecimal;

/**
 * 表达式构造器
 */
public class Builder {

    /**
     * 构建一个空值表达式
     *
     * @return 空值表达式，不会返回null
     */
    @NotNull
    public static NullExpression buildNull() {
        return NullExpression.NULL;
    }

    /**
     * 构建一个值为true的布尔值表达式
     *
     * @return 布尔值表达式，不会返回null
     */
    @NotNull
    public static BooleanExpression buildTrue() {
        return BooleanExpression.TRUE;
    }

    /**
     * 构建一个值为false的布尔值表达式
     *
     * @return 布尔值表达式，不会返回null
     */
    @NotNull
    public static BooleanExpression buildFalse() {
        return BooleanExpression.FALSE;
    }

    /**
     * 构建一个布尔值表达式
     *
     * @return 布尔值表达式，不会返回null
     */
    @NotNull
    public static BooleanExpression buildBoolean(boolean val) {
        return val ? buildTrue() : buildFalse();
    }

    /**
     * 构建一个数字值表达式
     *
     * @param val 数字值，不允许为null
     * @return 数字值表达式，不会返回null
     */
    @NotNull
    public static NumberExpression buildNumber(@NotNull Number val) {
        if (val instanceof Integer) {
            return buildInt32((Integer) val);
        } else if (val instanceof Long) {
            return buildInt64((Long) val);
        } else if (val instanceof Float) {
            return buildDouble((Float) val);
        } else if (val instanceof Double) {
            return buildDouble((Double) val);
        } else if (val instanceof BigDecimal) {
            BigDecimal value = (BigDecimal) val;
            if (value.scale() > 0) {
                // 转化为double
                return buildDouble(value.doubleValue());
            } else if (value.compareTo(BigDecimal.valueOf(Integer.MAX_VALUE)) > 0) {
                // 转化为long
                return buildInt64(value.longValue());
            } else {
                // 转化为int
                return buildInt32(value.intValue());
            }
        } else {
            return buildDouble(val.doubleValue());
        }
    }

    /**
     * 构建一个32位整型数字值表达式
     *
     * @param val 整型数字
     * @return 32位整型数字值表达式，不会返回null
     */
    @NotNull
    public static Int32Expression buildInt32(int val) {
        return new Int32Expression(val);
    }

    /**
     * 构建一个64位整型数字值表达式
     *
     * @param val 长整型数字
     * @return 64位整型数字值表达式，不会返回null
     */
    @NotNull
    public static Int64Expression buildInt64(long val) {
        return new Int64Expression(val);
    }

    /**
     * 构建一个双精度浮点型数字值表达式
     *
     * @param val 双精度浮点型数字
     * @return 双精度浮点型数字值表达式，不会返回null
     */
    @NotNull
    public static DoubleExpression buildDouble(double val) {
        return new DoubleExpression(val);
    }

    /**
     * 构建一个字符串值表达式
     *
     * @param val 字符串，不允许为null
     * @return 字符串值表达式，不会返回null
     */
    @NotNull
    public static StringExpression buildString(@NotNull String val) {
        return new StringExpression(val);
    }

    /**
     * 构建一个空的几何区域值表达式
     *
     * @return 空的几何区域值表达式，不会返回null
     */
    @NotNull
    public static GeometryExpression buildEmptyGeometry() {
        return GeometryExpression.EMPTY;
    }

    /**
     * 构建一个几何区域值表达式
     *
     * @param val 几何对象，不允许为null
     * @return 几何区域值表达式，不会返回null
     */
    @NotNull
    public static GeometryExpression buildGeometry(@NotNull Geometry val) {
        if (val == null || val.getType() == GeometryType.None) {
            return buildEmptyGeometry();
        }
        return new GeometryExpression(val);
    }

    public static ListExpression buildEmptyList() {
        return ListExpression.EMPTY;
    }

    public static ListExpression buildList(ValueExpression[] values) {
        if (values == null || values.length <= 0) {
            return buildEmptyList();
        }
        return new ListExpression(values);
    }

    public static PlaceholderExpression buildPlaceholder() {
        return PlaceholderExpression.PLACEHOLDER;
    }

    public static KeyExpression buildKey(String name) {
        return new KeyExpression(name);
    }

    /**
     *
     * @param val
     * @return
     * @throws IllegalArgumentException
     */
    public static ValueExpression buildValue(Object val)
            throws IllegalArgumentException {
        ValueExpression value = ValueExpression.tryBuildValue(val);
        if (value == null) {
            throw new IllegalArgumentException(val.toString());
        }
        return value;
    }

    public static SimpleExpression buildSimple(@NotNull ValueExpression key,
                                               @NotNull CompareOperator co,
                                               @NotNull ValueExpression value)
            throws IllegalArgumentException {
        // TODO，check
        return new SimpleExpression(key, co, value);
    }

    public static MultiExpression buildMulti(@NotNull LogicalExpression left,
                                             @NotNull LogicalOperator lo,
                                             @NotNull LogicalExpression right) {
        return new MultiExpression(left, lo, right);
    }


    public static InvariableExpression buildInvariable(LogicalExpression le, boolean alwaysTrue) {
        return new InvariableProxyExpression(le, alwaysTrue);
    }

    public static InvariableExpression buildInvariable(String le, boolean alwaysTrue) {
        return new InvariableStringExpression(le, alwaysTrue);
    }

    public static NonExpression buildNon(LogicalExpression le) {
        return new NonExpression(le);
    }
}
