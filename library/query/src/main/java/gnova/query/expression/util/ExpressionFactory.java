package gnova.query.expression.util;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.Geometry;
import gnova.geometry.model.GeometryType;
import gnova.query.expression.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 表达式构造器
 */
public class ExpressionFactory {

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
        if (val instanceof Integer
                || val instanceof Byte
                || val instanceof Short
                || val instanceof AtomicInteger) {
            return buildInt32(val.intValue());
        } else if (val instanceof Long
                || val instanceof AtomicLong) {
            return buildInt64(val.longValue());
        } else if (val instanceof Float
                || val instanceof Double) {
            return buildDouble(val.doubleValue());
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
        } else if (val instanceof BigInteger) {
            BigInteger value = (BigInteger) val;
            if (value.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
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
     * 构建一个空的字节串值表达式
     *
     * @return 空的字节串值表达式，不会返回null
     */
    @NotNull
    public static BytesExpression buildEmptyBytes() {
        return BytesExpression.EMPTY;
    }

    /**
     * 构建一个字节串值表达式
     *
     * @param val 字节数组，不允许为null
     * @return 字符串值表达式，不会返回null
     */
    @NotNull
    public static BytesExpression buildBytes(@NotNull byte[] val) {
        if (val == null || val.length == 0) {
            return buildEmptyBytes();
        }
        return new BytesExpression(val);
    }

    public static DateExpression buildNowDate() {
        return new DateExpression(new Date());
    }

    public static DateExpression buildDate(@NotNull Date date) {
        return new DateExpression(date);
    }

    public static DateExpression buildDate(@NotNull String date)
            throws UnsupportedOperationException, IllegalArgumentException {
        return new DateExpression(date);
    }

    public static DateExpression buildDate(@NotNull String date, String format)
            throws UnsupportedOperationException, IllegalArgumentException {
        return new DateExpression(date, format);
    }

    public static TimestampExpression buildNowTimestamp() {
        return new TimestampExpression(System.currentTimeMillis());
    }

    public static TimestampExpression buildTimestamp(long timeMillis) {
        return new TimestampExpression(timeMillis);
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
     * @param val 几何对象，不允许为null，仅支持{@link Geometry#NONE 空几何对象}、{@link gnova.geometry.model.Polygon 多边形类型}、{@link gnova.geometry.model.MultiPolygon 多多边形类型}
     * @return 几何区域值表达式，不会返回null
     * @throws IllegalArgumentException 若几何对象不合法，则抛出此异常
     */
    @NotNull
    public static GeometryExpression buildGeometry(@NotNull Geometry val)
            throws IllegalArgumentException {
        if (val == null || val.getType() == GeometryType.None) {
            return buildEmptyGeometry();
        }
        ExpressionChecker.checkGeometry(val);
        return new GeometryExpression(val);
    }

    /**
     * 构建一个空的列表值表达式
     *
     * @return 空的列表值表达式，不会返回null
     */
    @NotNull
    public static ListExpression buildEmptyList() {
        return ListExpression.EMPTY;
    }

    /**
     * 构建一个列表值表达式
     *
     * @param values 值表达式的数组，不允许为null，可以为空数组
     * @return 列表值表达式，不会返回null
     */
    @NotNull
    public static ListExpression buildList(@NotNull ValueExpression... values) {
        if (values == null || values.length <= 0) {
            return buildEmptyList();
        }
        ExpressionChecker.checkList(values);
        return new ListExpression(values);
    }

    /**
     * 构建一个列表值表达式
     *
     * @param values 值表达式的集合，不允许为null，可以为空集合
     * @return 列表值表达式，不会返回null
     */
    @NotNull
    public static ListExpression buildList(@NotNull Collection<? extends ValueExpression> values) {
        if (values == null || values.isEmpty()) {
            return buildEmptyList();
        }
        ExpressionChecker.checkList(values);
        return new ListExpression(values);
    }

    /**
     * 构建一个占位符表达式
     *
     * @return 占位符表达式，不会返回null
     */
    @NotNull
    public static PlaceholderExpression buildPlaceholder() {
        return PlaceholderExpression.PLACEHOLDER;
    }

    /**
     * 构建一个键值表达式
     *
     * @param keyName 键的名称，不允许为null
     * @return 键值表达式，不会返回null
     */
    @NotNull
    public static KeyExpression buildKey(@NotNull String keyName) {
        ExpressionChecker.checkKey(keyName);
        return new KeyExpression(keyName);
    }

    /**
     * 根据一个对象构建一个值表达式
     *
     * @param object 对象，不允许为null
     * @return 值表达式，不会返回null
     * @throws IllegalArgumentException 若对象无法被构建成值表达式，则抛出此异常
     */
    @NotNull
    public static ValueExpression buildValue(Object object)
            throws IllegalArgumentException {
        if (object == null) {
            return ExpressionFactory.buildNull();
        } else if (object instanceof ValueExpression) {
            return (ValueExpression) object;
        } else if (object instanceof Boolean) {
            return ExpressionFactory.buildBoolean(((Boolean) object).booleanValue());
        } else if (object instanceof Number) {
            return ExpressionFactory.buildNumber((Number) object);
        } else if (object instanceof String) {
            return ExpressionFactory.buildString((String) object);
        } else if (object instanceof Geometry) {
            return ExpressionFactory.buildGeometry((Geometry) object);
        } else if (object.getClass().isArray()) {
            Class<?> clazz = object.getClass().getComponentType();
            Collection<ValueExpression> list = new ArrayList<>();
            if (clazz == byte.class) {
                byte[] bytes = (byte[]) object;
                for (byte b : bytes) {
                    list.add(buildInt32(b));
                }
            } else if (clazz == char.class) {
                // TODO
            }
            // TODO
        } else if (object instanceof Iterable) {
            // TODO
        }
        throw new IllegalArgumentException("不支持的值类型：" + object);
    }

    /**
     * 构建一个简单谓词表达式
     *
     * @param left
     * @param predicate
     * @param right
     * @return
     * @throws IllegalArgumentException
     */
    public static SimpleExpression buildSimple(@NotNull ValueExpression left,
                                               @NotNull ValuePredicate predicate,
                                               @NotNull ValueExpression right)
            throws IllegalArgumentException {
        ExpressionChecker.checkSimple(left, predicate, right);
        return new SimpleExpressionImpl(left, predicate, right);
    }

    /**
     * 构建一个复合谓词表达式
     *
     * @param left
     * @param predicate
     * @param right
     * @return
     */
    public static MultipleExpression buildMultiple(@NotNull PredicateExpression left,
                                                   @NotNull LogicalPredicate predicate,
                                                   @NotNull PredicateExpression right) {
        return new MultipleExpressionImpl(left, predicate, right);
    }

    /**
     * 构建一个否定谓词表达式
     *
     * @param positive
     * @return
     */
    public static NegativeExpression buildNegative(@NotNull PredicateExpression positive) {
        return new NegativeExpressionImpl(positive);
    }

}