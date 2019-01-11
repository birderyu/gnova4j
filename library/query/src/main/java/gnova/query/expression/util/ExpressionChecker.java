package gnova.query.expression.util;

import gnova.core.ArrayIterator;
import gnova.core.annotation.NotNull;
import gnova.geometry.model.Geometry;
import gnova.geometry.model.GeometryType;
import gnova.query.expression.ValueExpression;
import gnova.query.expression.ValuePredicate;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionChecker {

    private static Pattern keyPattern = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*$");

    public static void checkGeometry(@NotNull Geometry geometry)
            throws IllegalArgumentException {
        GeometryType gt = geometry.getType();
        if (gt != GeometryType.None
                && gt != GeometryType.Polygon
                && gt != GeometryType.MultiPolygon) {
            throw new IllegalArgumentException("几何区域值必须为多边形类型和多多边形类型：" + geometry);
        }
    }

    public static void checkList(@NotNull ValueExpression[] values)
            throws IllegalArgumentException {
        checkList(new ArrayIterator<>(values));
    }

    public static void checkList(@NotNull Iterable<? extends ValueExpression> values)
            throws IllegalArgumentException {
        checkList(values.iterator());
    }

    public static void checkKey(@NotNull String keyName) throws IllegalArgumentException {
        if (keyName.isEmpty()) {
            throw new IllegalArgumentException("键值不能为空");
        }
        Matcher matcher = keyPattern.matcher(keyName);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("键值只能以字母和下划线打头，只能包含字母、数字或下划线：" + keyName);
        }
    }

    public static void checkList(@NotNull Iterator<? extends ValueExpression> values)
            throws IllegalArgumentException {
        while (values.hasNext()) {
            ValueExpression value = values.next();
            if (value == null) {
                throw new IllegalArgumentException("列表值中不允许包含null，若需要表达空值，请使用空值表达式对象（NullExpression）");
            } else if (value.isKey()) {
                throw new IllegalArgumentException("列表值中不允许包含键值：" + value);
            }
        }
    }

    public static void checkValuePredicate(@NotNull ValueExpression value,
                                           @NotNull ValuePredicate predicate)
            throws IllegalArgumentException {
        value.checkBy(predicate);
    }

    /**
     * 验证一个值表达式是否可以被作为左值
     *
     * @param value
     * @throws IllegalArgumentException
     */
    public static void checkLeftValue(@NotNull ValueExpression value)
            throws IllegalArgumentException {
        if (value.placeholderSize() > 0) {
            throw new IllegalArgumentException("左值中不允许包含占位符：" + value);
        }
    }

    /**
     * 验证一个值表达式是否可以被作为右值
     *
     * @param value
     * @throws IllegalArgumentException
     */
    public static void checkRightValue(@NotNull ValueExpression value)
            throws IllegalArgumentException {
        if (value.isKey()) {
            throw new IllegalArgumentException("右值不允许为键值：" + value);
        }
    }

    public static void checkSimple(@NotNull ValueExpression left,
                                   @NotNull ValuePredicate predicate,
                                   @NotNull ValueExpression right)
            throws IllegalArgumentException {

        checkLeftValue(left);
        checkRightValue(right);
        if (left.isKey() || right.placeholderSize() > 0) {
            // 左值为键值，或者右值中包含了占位符，此时无法比较合法性
            return;
        }
        left.checkBy(predicate);
        right.checkBy(predicate);
    }

}
