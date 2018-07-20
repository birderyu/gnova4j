package gnova.data.wrapper;

import gnova.query.expression.Expressions;
import gnova.query.expression.LogicalExpression;
import gnova.data.Rows;
import gnova.core.function.Getter;
import gnova.core.function.ObjectBuilder;
import gnova.core.PredicateIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListRows<E extends Getter>
        implements Rows<E> {

    private final List<E> list;
    private final ObjectBuilder<E> builder;

    public ListRows(ObjectBuilder<E> builder) {
        this.list = new ArrayList<>();
        this.builder = builder;
    }

    @Override
    public int getRowSize() {
        return list.size();
    }

    @Override
    public boolean isRowEmpty() {
        return list.isEmpty();
    }

    @Override
    public E getRow(int rIndex) throws IndexOutOfBoundsException {
        return list.get(rIndex);
    }

    @Override
    public Iterator<E> rowIterator() {
        return list.iterator();
    }

    @Override
    public Iterator<E> rowIterator(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex).iterator();
    }

    @Override
    public Iterator<E> rowIterator(int fromIndex, boolean fromInclusive, int toIndex, boolean toInclusive) {
        return list.subList(
                fromInclusive ? fromIndex : fromIndex + 1,
                toInclusive ? toIndex + 1 : toIndex
        ).iterator();
    }

    @Override
    public Iterator<E> rowIterator(String selection, Object[] params) {
        LogicalExpression le = Expressions.toLogical(selection, params);
        return new PredicateIterator<>(rowIterator(), o -> le.fit(o));
    }

    @Override
    public E appendRow() {
        E e = builder.build();
        if (!list.add(e)) {
            throw new IllegalArgumentException();
        }
        return e;
    }

    @Override
    public E appendRow(E row) throws IllegalArgumentException {
        if (!list.add(row)) {
            throw new IllegalArgumentException();
        }
        return row;
    }

    @Override
    public E removeRow(int rIndex) throws IndexOutOfBoundsException {
        return list.remove(rIndex);
    }

    @Override
    public void clearRow() {
        list.clear();
    }

}
