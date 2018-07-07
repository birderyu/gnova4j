package gnova.query.value;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;
import gnova.query.CompareOperator;
import gnova.query.ValueExpression;
import gnova.query.ValueType;
import gnova.core.ArrayIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class ListExpression
        extends ValueExpression implements Iterable<ValueExpression> {

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
    public String checkBy(CompareOperator co) {
        // 字符串值只能和等于、不等于、列表包含、列表不包含相匹配
        if (co == CompareOperator.EQ || co == CompareOperator.NEQ ||
                co == CompareOperator.IN || co == CompareOperator.NIN) {
            return null;
        }
        return "字符串值只能和等于、不等于、列表包含、列表不包含相匹配";
    }

    @Override
    public int sizeOfPlaceholder() {
        int size = 0;
        for (ValueExpression value : values) {
            size += value.sizeOfPlaceholder();
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
    protected boolean inBy(@NotNull Object left) {
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
            ValueExpression value = tryBuildValue(left);
            if (value != null) {
                return inBy(value);
            }
        }
        return false;
    }

    protected boolean inBy(@NotNull ValueExpression left) {
        for (ValueExpression right : values) {
            if (right.comparedBy(left, CompareOperator.EQ)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean uninBy(@NotNull Object left) {
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
            ValueExpression value = tryBuildValue(left);
            if (value != null) {
                return uninBy(value);
            }
        }
        return false;
    }

    protected boolean uninBy(@NotNull ValueExpression left) {
        for (ValueExpression right : values) {
            if (right.comparedBy(left, CompareOperator.EQ)) {
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
