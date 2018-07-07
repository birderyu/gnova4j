package gnova.query;

import gnova.annotation.Checked;
import gnova.annotation.NotNull;
import gnova.query.value.ListExpression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class Instancer {

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
            return instantiateLogical(expression.asLogical(), queue);
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
            return Builder.buildValue(val);
        } else if (value.isList()) {
            if (value.sizeOfPlaceholder() == 0) {
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

    private static LogicalExpression instantiateLogical(@NotNull LogicalExpression le,
                                                        @NotNull Queue<Object> params)
            throws IllegalArgumentException {
        if (le.isInvariable()) {
            return le;
        } else if (le.isSimple()) {
            ValueExpression right = instantiateValue(le.asSimple().getRightValue(), params);
            String errorMsg = right.checkBy(le.asSimple().getCompareOperator());
            if (errorMsg != null) {
                throw new IllegalArgumentException(errorMsg);
            }
            // 左值原本就不允许包含占位符，所以不需要实例化
            return new SimpleExpression(le.asSimple().getLeftValue(),
                    le.asSimple().getCompareOperator(), right);
        } else if (le.isNon()) {
            return new NonExpression(instantiateLogical(
                    le.asNon().getLogicalExpression(), params));
        } else if (le.isMulti()) {
            return new MultiExpression(
                    instantiateLogical(le.asMulti().getLeftExpression(), params),
                    le.asMulti().getLogicalOperator(),
                    instantiateLogical(le.asMulti().getRightExpression(), params));
        }
        return le;
    }

}
