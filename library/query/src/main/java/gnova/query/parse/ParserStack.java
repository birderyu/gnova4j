package gnova.query.parse;

import gnova.core.annotation.NotNull;
import gnova.query.*;

public class ParserStack {

    private final String whereClause;

    private LogicalExpression ll = null; // 左逻辑表达式
    private LogicalOperator lo = null; // 逻辑操作符
    private ValueExpression lv = null; // 左值表达式
    private CompareOperator co = null; // 比较操作符
    private int sizeOfNon = 0;

    private ValueExpression lastValue = null;
    private int lastValueCursor = -1;

    public ParserStack(String whereClause) {
        this.whereClause = whereClause;
    }

    public void pushNon() {
        sizeOfNon++;
    }

    public void pushValue(@NotNull ValueExpression ve, int cursor)
            throws ParseException {
        // 当插入一个值时，先将这个值缓存起来
        if (lastValue != null) {
            pushValue0(lastValue, lastValueCursor);
        }
        lastValue = ve;
        lastValueCursor = cursor;
    }

    public void push(@NotNull CompareOperator co, int cursor)
            throws ParseException {

        // 将上一部没有插入的值插入
        if (lastValue != null) {
            pushValue0(lastValue, lastValueCursor);
        }
        lastValue = null;
        lastValueCursor = -1;

        push0(co, cursor);
    }

    public void push(@NotNull LogicalExpression le, int cursor)
            throws ParseException {

        // 将上一部没有插入的值插入
        if (lastValue != null) {
            pushValue0(lastValue, lastValueCursor);
        }
        lastValue = null;
        lastValueCursor = -1;

        push0(le, cursor);
    }

    public void push(@NotNull LogicalOperator lo, int cursor)
            throws ParseException {

        // 将上一部没有插入的值插入
        if (lastValue != null) {
            pushValue0(lastValue, lastValueCursor);
        }
        lastValue = null;
        lastValueCursor = -1;

        push0(lo, cursor);
    }

    public ValueExpression pop() {
        if (lastValue != null) {
            ValueExpression ve = lastValue;
            lastValue = null;
            lastValueCursor = -1;
            return ve;
        }
        return null;
    }

    public Expression get()
            throws ParseException {

        // 将上一部没有插入的值插入
        if (lastValue != null) {
            pushValue0(lastValue, lastValueCursor);
        }
        lastValue = null;
        lastValueCursor = -1;

        if (ll == null && lv == null) {
            return null;
        }
        if (ll != null && lv != null) {
            throw new ParseException("简单逻辑表达式不完整，必须包含一个比较操作符",
                    whereClause, -1);
        }
        if (sizeOfNon > 0) {
            throw new ParseException("否定逻辑表达式不完整，否定操作符之后无逻辑表达式",
                    whereClause, -1);
        }
        return ll != null ? ll : lv;
    }

    private void pushValue0(@NotNull ValueExpression ve, int cursor)
            throws ParseException {

        if (lv == null) {
            if (ve.sizeOfPlaceholder() != 0) {
                throw new ParseException("左值表达式不允许包含占位符",
                        whereClause, cursor);
            }
            lv = ve;
        } else {
            if (co == null) {
                throw new ParseException("简单逻辑表达式不完整，必须包含一个比较操作符",
                        whereClause, cursor);
            }
            if (ve.isKey()) {
                throw new ParseException("右值表达式不允许是键值",
                        whereClause, cursor);
            }
            String msg = ve.checkBy(co);
            if (msg != null) {
                throw new ParseException("表达式解析失败，" + msg,
                        whereClause, cursor);
            }
            LogicalExpression se = new SimpleExpression(lv, co, ve);
            while (sizeOfNon > 0) {
                se = new NonExpression(se);
                sizeOfNon--;
            }
            push0(se, cursor);
            lv = null;
            co = null;
        }
    }

    public void push0(@NotNull CompareOperator co, int cursor)
            throws ParseException {

        lastValue = null;
        lastValueCursor = -1;

        if (this.co != null) {
            throw new ParseException("表达式解析失败，比较操作符不允许叠加",
                    whereClause, cursor);
        }
        if (lv == null) {
            throw new ParseException("表达式解析失败，比较操作符的左侧没有值表达式",
                    whereClause, cursor);
        }
        String msg = lv.checkBy(co);
        if (msg != null) {
            throw new ParseException("表达式解析失败，" + msg,
                    whereClause, cursor);
        }
        this.co = co;
    }

    public void push0(@NotNull LogicalExpression le, int cursor)
            throws ParseException {

        lastValue = null;
        lastValueCursor = -1;

        if (ll == null) {
            while (sizeOfNon > 0) {
                le = new NonExpression(le);
                sizeOfNon--;
            }
            ll = le;
        } else {
            if (lo == null) {
                throw new ParseException("复合逻辑表达式不完整，必须包含一个逻辑操作符",
                        whereClause, cursor);
            }
            while (sizeOfNon > 0) {
                le = new NonExpression(le);
                sizeOfNon--;
            }
            LogicalExpression me = new MultiExpression(ll, lo, le);
            lo = null;
            ll = me;
        }
    }

    public void push0(@NotNull LogicalOperator lo, int cursor)
            throws ParseException {

        lastValue = null;
        lastValueCursor = -1;

        if (this.lo != null) {
            throw new ParseException("表达式解析失败，逻辑操作符不允许叠加",
                    whereClause, cursor);
        }
        if (ll == null) {
            throw new ParseException("表达式解析失败，逻辑操作符的左侧没有逻辑表达式",
                    whereClause, cursor);
        }
        this.lo = lo;
    }
}
