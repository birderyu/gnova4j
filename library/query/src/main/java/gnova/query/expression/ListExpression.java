package gnova.query.expression;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;
import gnova.core.ArrayIterator;
import gnova.query.expression.util.ExpressionFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class ListExpression
        extends AbstractValueExpression implements Iterable<ValueExpression> {

    /**
     * 空列表
     */
    public static final ListExpression EMPTY = new ListExpression(new ValueExpression[0]);

    @NotNull
    private ValueExpression[] values;

    /**
     *
     * @param values 不允许出现key
     */
    public ListExpression(@Checked ValueExpression[] values) {
        this.values = values;
    }

    /**
     *
     * @param values 不允许出现key
     */
    public ListExpression(@Checked Collection<? extends ValueExpression> values) {
        this.values = values.toArray(new ValueExpression[values.size()]);
    }

    public int size() {
        return values.length;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.List;
    }

    @Override
    @NotNull
    public ValueExpression[] getValue() {
        return values;
    }

    @Override
    public void checkBy(ValuePredicate predicate) throws IllegalArgumentException {
        // 字符串值只能和等于、不等于、列表包含、列表不包含相匹配
        if (predicate == ValuePredicate.EQ || predicate == ValuePredicate.NEQ ||
                predicate == ValuePredicate.IN || predicate == ValuePredicate.NIN) {
            return;
        }
        throw new IllegalArgumentException("字符串值只能和等于、不等于、列表包含、列表不包含相匹配");
    }

    @Override
    public int placeholderSize() {
        int size = 0;
        for (ValueExpression value : values) {
            size += value.placeholderSize();
        }
        return size;
    }

    @Override
    public List asList() {
        List list = new ArrayList();
        for (ValueExpression value : values) {
            if (value.isList()) {
                list.add(value.asList());
            } else {
                list.add(value.getValue());
            }
        }
        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i].toString());
            if (i != values.length - 1) {
                sb.append(", ");
            }
        }
        return sb.append(")").toString();
    }

    @Override
    protected boolean inBy(@NotNull Object left) throws IllegalArgumentException {
        if (left instanceof Object[]) {
            Object[] lefts = (Object[]) left;
            for (Object _left : lefts) {
                if (!inBy(_left)) {
                    return false;
                }
            }
            return true;
        } else if (left instanceof Iterable) {
            Iterable lefts = (Iterable) left;
            for (Object _left : lefts) {
                if (!inBy(_left)) {
                    return false;
                }
            }
            return true;
        } else if (left instanceof Iterator) {
            Iterator lefts = (Iterator) left;
            while (lefts.hasNext()) {
                if (!inBy(lefts.next())) {
                    return false;
                }
            }
            return true;
        } else if (left instanceof ValueExpression) {
            return inBy((ValueExpression) left);
        } else {
            return inBy(ExpressionFactory.buildValue(left));
        }
    }

    protected boolean inBy(@NotNull ValueExpression left) {
        for (ValueExpression right : values) {
            if (right.comparedBy(left, ValuePredicate.EQ)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean uninBy(@NotNull Object left) throws IllegalArgumentException {
        if (left instanceof Object[]) {
            Object[] lefts = (Object[]) left;
            for (Object _left : lefts) {
                if (!uninBy(_left)) {
                    return false;
                }
            }
            return true;
        } else if (left instanceof Iterable) {
            Iterable lefts = (Iterable) left;
            for (Object _left : lefts) {
                if (!uninBy(_left)) {
                    return false;
                }
            }
            return true;
        } else if (left instanceof Iterator) {
            Iterator lefts = (Iterator) left;
            while (lefts.hasNext()) {
                if (!uninBy(lefts.next())) {
                    return false;
                }
            }
            return true;
        } else if (left instanceof ValueExpression) {
            return uninBy((ValueExpression) left);
        } else {
            return uninBy(ExpressionFactory.buildValue(left));
        }
    }

    protected boolean uninBy(@NotNull ValueExpression left) {
        for (ValueExpression right : values) {
            if (right.comparedBy(left, ValuePredicate.EQ)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<ValueExpression> iterator() {
        return new ArrayIterator<>(values);
    }
}
