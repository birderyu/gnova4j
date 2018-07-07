package gnova.query;

import gnova.annotation.NotNull;
import gnova.query.parse.ParseException;
import gnova.query.parse.Parser;

/**
 * 表达式的帮助类
 */
public class Expressions {

    /**
     * 一个空的恒真逻辑表达式
     */
    private static final InvariableExpression EMTRY_ALWAYS_TRUE = new InvariableExpression(true) {

        @Override
        public int sizeOfPlaceholder() {
            return 0;
        }

        @Override
        public String toString() {
            return "";
        }
    };

    /**
     * 一个空的恒假逻辑表达式
     */
    private static final InvariableExpression EMTRY_ALWAYS_FALSE = new InvariableExpression(true) {

        @Override
        public int sizeOfPlaceholder() {
            return 0;
        }

        @Override
        public String toString() {
            return "";
        }
    };

    /**
     * 将一个字符串和若干参数转化为一个逻辑表达式
     *
     * @param selection
     * @param params
     * @return
     * @throws IllegalArgumentException
     */
    @NotNull
    public static LogicalExpression toLogical(String selection, Object[] params)
            throws IllegalArgumentException {

        if (selection == null || (selection = selection.trim()).isEmpty()) {
            // 空的字符串，解析成为一个恒真逻辑表达式
            return EMTRY_ALWAYS_TRUE;
        }

        // 1. 解析表达式
        Expression expression;
        try {
            expression = Parser.parse(selection);
        } catch (ParseException e) {
            // 表达式解析失败
            throw new IllegalArgumentException(e);
        }
        if (expression == null || expression.isValue()) {
            // 表达式解析失败
            throw new IllegalArgumentException("字符串并非一个逻辑表达式：" + selection);
        }


        // 2.实例化表达式
        int s = expression.sizeOfPlaceholder();
        if (s > 0) {
            // 占位符的数量不为0，说明需要实例化
            if (params == null) {
                // 直接返回一个包含了占位符的表达式
                return expression.asLogical();
            } else if (params.length < s) {
                throw new IllegalArgumentException("占位符的个数（"
                        + s
                        + "）与参数的个数（"
                        + params.length
                        + "）无法匹配");
            }
            expression = Instancer.instantiate(expression, params);
        }

        // 3.简化表达式
        return Simplifier.simplify(expression).asLogical();

    }

}
