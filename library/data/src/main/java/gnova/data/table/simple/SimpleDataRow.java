package gnova.data.table.simple;

import gnova.data.table.DataRow;
import gnova.data.table.DataTable;
import gnova.data.table.ReadOnlyDataRow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleDataRow
        implements DataRow {

    private final DataTable table;
    private List<Object> values;

    public SimpleDataRow(DataTable table) {
        this(table, null);
    }

    public SimpleDataRow(DataTable table, ReadOnlyDataRow row)
            throws IllegalArgumentException {
        this.table = table;
        initialize(row);
    }

    @Override
    public DataTable getTable() {
        return table;
    }

    @Override
    public <T> T getValue(int index) throws IndexOutOfBoundsException {
        return (T) values.get(index);
    }

    @Override
    public <T> T getValue(String name) {
        return (T) values.get(getColumnIndex(name));
    }

    @Override
    public <T> void setValue(int index, T value)
            throws IndexOutOfBoundsException, IllegalArgumentException {
        // TODO，判断值是否合法
        // TODO，维护索引
        values.set(index, value);
    }

    @Override
    public <T> void setValue(String name, T value)
            throws IllegalArgumentException {
        values.set(getColumnIndex(name), value);
    }

    @Override
    public <T> void appendValue(T value) {
        // TODO，维护索引
        values.add(value);
    }

    @Override
    public <T> T remove(int index) throws IndexOutOfBoundsException {
        // TODO，维护索引
        return (T) values.remove(index);
    }

    @Override
    public DataRow clone() {
        return new SimpleDataRow(table, this);
    }

    private void initialize(ReadOnlyDataRow row)
            throws IllegalArgumentException {

        int size = size();
        values = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            if (row != null && i < row.size()) {
                // TODO，判断值是否合法
                values.add(row.getValue(i));
            } else {
                values.add(getColumn(i).getDefaultValue());
            }
        }

    }

    @Override
    public Iterator iterator() {
        return values.iterator();
    }
}
