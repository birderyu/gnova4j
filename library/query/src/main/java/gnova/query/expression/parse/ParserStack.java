package gnova.query.expression.parse;

import gnova.core.annotation.NotNull;
import gnova.query.expression.*;
import gnova.query.expression.util.ExpressionChecker;
import gnova.query.expression.util.ExpressionFactory;

public class ParserStack {

    private final String whereClause;

    private PredicateExpression leftPredicateExpression = null; // 左谓词表达式
    private LogicalPredicate logicalPredicate = null; // 逻辑谓词
    private ValueExpression lve = null; // 左值表达式
    private ValuePredicate co = null; // 值谓词
    private int sizeOfNegative = 0;

    private ValueExpression lastValue = null;
    private int lastValueCursor = -1;

    public ParserStack(String whereClause) {
        this.whereClause = whereClause;
    }

    /**
     * 添加一个否定谓词
     */
    public void pushNegative() {
        sizeOfNegative++;
    }

    /**
     * 添加一个表达式
     *
     * @param expression
     * @param cursor
     * @throws ParseException
     */
    public void push(@NotNull Expression expression, int cursor) throws ParseException {

        // 将上一次没有插入的值插入
        if (lastValue != null) {
            push0(lastValue, lastValueCursor);
            lastValue = null;
            lastValueCursor = -1;
        }

        if (expression.isValue()) {
            // 当插入一个值时，先将这个值缓存起来
            lastValue = expression.asValue();
            lastValueCursor = cursor;
        } else {
            push0(expression.asPredicate(), cursor);
        }

    }

    public void push(@NotNull ValuePredicate valuePredicate, int cursor)
            throws ParseException {

        // 将上一次没有插入的值插入
        if (lastValue != null) {
            push0(lastValue, lastValueCursor);
            lastValue = null;
            lastValueCursor = -1;
        }

        push0(valuePredicate, cursor);
    }

    public void push(@NotNull LogicalPredicate logicalPredicate, int cursor)
            throws ParseException {

        // 将上一次没有插入的值插入
        if (lastValue != null) {
            push0(lastValue, lastValueCursor);
            lastValue = null;
            lastValueCursor = -1;
        }

        push0(logicalPredicate, cursor);
    }

    public ValueExpression pop() {
        if (lastValue != null) {
            ValueExpression value = lastValue;
            lastValue = null;
            lastValueCursor = -1;
            return value;
        }
        return null;
    }

    public Expression get()
            throws ParseException {

        // 将上一次没有插入的值插入
        if (lastValue != null) {
            push0(lastValue, lastValueCursor);
            lastValue = null;
            lastValueCursor = -1;
        }

        if (leftPredicateExpression == null && lve == null) {
            return null;
        }
        if (leftPredicateExpression != null && lve != null) {
            throw new ParseException("简单逻辑表达式不完整，必须包含一个比较操作符",
                    whereClause, -1);
        }
        if (sizeOfNegative > 0) {
            throw new ParseException("否定逻辑表达式不完整，否定操作符之后无逻辑表达式",
                    whereClause, -1);
        }
        return leftPredicateExpression != null ? leftPredicateExpression : lve;
    }

    private void push0(@NotNull ValueExpression ve, int cursor)
            throws ParseException {

        if (lve == null) {
            try {
                ExpressionChecker.checkLeftValue(ve);
            } catch (IllegalArgumentException iae) {
                throw new ParseException("表达式解析失败，" + iae.getMessage(), whereClause, cursor);
            }
            lve = ve;
        } else {
            if (co == null) {
                throw new ParseException("简单逻辑表达式不完整，必须包含一个比较操作符",
                        whereClause, cursor);
            }

            PredicateExpression se = null;
            try {
                se = ExpressionFactory.buildSimple(lve, co, ve);
            } catch (IllegalArgumentException iae) {
                throw new ParseException("表达式解析失败，" + iae.getMessage(), whereClause, cursor);
            }
            while (sizeOfNegative > 0) {
                se = ExpressionFactory.buildNegative(se);
                sizeOfNegative--;
            }
            push0(se, cursor);
            lve = null;
            co = null;
        }
    }

    public void push0(@NotNull ValuePredicate co, int cursor)
            throws ParseException {

        lastValue = null;
        lastValueCursor = -1;

        if (this.co != null) {
            throw new ParseException("表达式解析失败，比较操作符不允许叠加",
                    whereClause, cursor);
        }
        if (lve == null) {
            throw new ParseException("表达式解析失败，比较操作符的左侧没有值表达式",
                    whereClause, cursor);
        }
        try {
            lve.checkBy(co);
        } catch (IllegalArgumentException iae) {
            throw new ParseException("表达式解析失败，" + iae.getMessage(),
                    whereClause, cursor);
        }
        this.co = co;
    }

    private void push0(@NotNull PredicateExpression predicateExpression, int cursor)
            throws ParseException {

        lastValue = null;
        lastValueCursor = -1;

        if (leftPredicateExpression == null) {
            while (sizeOfNegative > 0) {
                predicateExpression = ExpressionFactory.buildNegative(predicateExpression);
                sizeOfNegative--;
            }
            leftPredicateExpression = predicateExpression;
        } else {
            if (logicalPredicate == null) {
                throw new ParseException("复合逻辑表达式不完整，必须包含一个逻辑操作符",
                        whereClause, cursor);
            }
            while (sizeOfNegative > 0) {
                predicateExpression = ExpressionFactory.buildNegative(predicateExpression);
                sizeOfNegative--;
            }
            PredicateExpression me = ExpressionFactory.buildMultiple(leftPredicateExpression, logicalPredicate, predicateExpression);
            logicalPredicate = null;
            leftPredicateExpression = me;
        }
    }

    public void push0(@NotNull LogicalPredicate lo, int cursor)
            throws ParseException {

        lastValue = null;
        lastValueCursor = -1;

        if (this.logicalPredicate != null) {
            throw new ParseException("表达式解析失败，逻辑操作符不允许叠加",
                    whereClause, cursor);
        }
        if (leftPredicateExpression == null) {
            throw new ParseException("表达式解析失败，逻辑操作符的左侧没有逻辑表达式",
                    whereClause, cursor);
        }
        this.logicalPredicate = lo;
    }
}
