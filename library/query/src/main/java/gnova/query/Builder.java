package gnova.query;

import gnova.annotation.NotNull;
import gnova.geometry.model.Geometry;
import gnova.query.value.*;

import java.math.BigDecimal;

public class Builder {

    /**
     * 构建一个空值表达式
     *
     * @return
     */
    public static NullExpression buildNull() {
        return NullExpression.NULL;
    }

    public static BooleanExpression buildBoolean(boolean val) {
        return val ? BooleanExpression.TRUE : BooleanExpression.FALSE;
    }

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

    public static Int32Expression buildInt32(int val) {
        return new Int32Expression(val);
    }

    public static Int64Expression buildInt64(long val) {
        return new Int64Expression(val);
    }

    public static DoubleExpression buildDouble(double val) {
        return new DoubleExpression(val);
    }

    public static StringExpression buildString(String val) {
        return new StringExpression(val);
    }

    public static GeometryExpression buildGeometry(Geometry val) {
        return new GeometryExpression(val);
    }

    public static ListExpression buildList(ValueExpression[] values) {
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
