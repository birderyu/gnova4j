package gnova.query.expression.util;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;
import gnova.query.expression.*;
import gnova.query.expression.util.ExpressionFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class ExpressionInstancer {

    /**
     *
     * @param expression 必须包含占位符
     * @param params 必须与占位符的数量一一对应
     * @throws IllegalArgumentException
     */
    public static Expression instantiate(@Checked Expression expression,
                                         @Checked Object[] params)
            throws IllegalArgumentException {

        // 用一个队列去存储所有的参数
        Queue<Object> queue = new LinkedList<>();
        for (Object param : params) {
            queue.offer(param);
        }

        if (expression.isValue()) {
            return instantiateValue(expression.asValue(), queue);
        } else {
            return instantiatePredicate(expression.asPredicate(), queue);
        }
    }

    private static ValueExpression instantiateValue(@NotNull ValueExpression value,
                                                    @NotNull Queue<Object> params)
            throws IllegalArgumentException {

        if (value.isPlaceholder()) {
            Object val = params.poll();
            if (val == null) {
                throw new IllegalArgumentException("值与占位符在数量上不匹配："
                        + value + "，" + params);
            }
            return ExpressionFactory.buildValue(val);
        } else if (value.isList()) {
            if (value.placeholderSize() == 0) {
                return value;
            }
            ListExpression list = (ListExpression) value;
            Collection<ValueExpression> values = new ArrayList<>();
            for (ValueExpression item : list) {
                ValueExpression _item = instantiateValue(item, params);
                values.add(_item);
            }
            return new ListExpression(values);
        }
        return value;

    }

    private static PredicateExpression instantiatePredicate(@NotNull PredicateExpression le,
                                                            @NotNull Queue<Object> params)
            throws IllegalArgumentException {
        if (le.isInvariable()) {
            return le;
        } else if (le.isSimple()) {
            ValueExpression right = instantiateValue(le.asSimple().getRight(), params);
            // 左值原本就不允许包含占位符，所以不需要实例化
            return ExpressionFactory.buildSimple(le.asSimple().getLeft(),
                    le.asSimple().getPredicate(), right);
        } else if (le.isNegative()) {
            return ExpressionFactory.buildNegative(instantiatePredicate(
                    le.asNegative().getPositive(), params));
        } else if (le.isMultiple()) {
            return ExpressionFactory.buildMultiple(
                    instantiatePredicate(le.asMultiple().getLeft(), params),
                    le.asMultiple().getPredicate(),
                    instantiatePredicate(le.asMultiple().getRight(), params));
        }
        return le;
    }

}
