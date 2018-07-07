package gnova.data.table.simple;

import gnova.core.EmptyIterator;
import gnova.core.SuperIterator;
import gnova.data.table.*;

import java.util.*;

public abstract class SimpleDataTable
        implements DataTable {

    private final String name;
    private List<DataColumn> columns;
    private List<DataRow> rows;
    private Map<String, Integer> columnNames;

    public SimpleDataTable() {
        name = "new data table";
    }

    public SimpleDataTable(ReadOnlyDataTable table) {
        name = table.getName();
        // TODO
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getColumnSize() {
        return columns == null ? 0 : columns.size();
    }

    @Override
    public int getRowSize() {
        return rows == null ? 0 : rows.size();
    }

    @Override
    public boolean isEmpty() {
        return rows == null ? true : rows.isEmpty();
    }

    @Override
    public DataColumn getColumn(String cName) {
        if (columnNames == null) {
            return null;
        }
        Integer cIndex = columnNames.get(cName);
        return cIndex == null ? null : columns.get(cIndex);
    }

    @Override
    public DataColumn getColumn(int cIndex)
            throws IndexOutOfBoundsException {
        if (columns == null) {
            throw new IndexOutOfBoundsException("column size is zero");
        }
        return columns.get(cIndex);
    }

    @Override
    public boolean containsColumn(String cName) {
        return columnNames == null ? false : columnNames.containsKey(cName);
    }

    @Override
    public int getColumnIndex(String cName) {
        if (columnNames == null) {
            return -1;
        }
        Integer cIndex = columnNames.get(cName);
        return cIndex == null ? -1 : cIndex;
    }

    @Override
    public String getColumnName(int cIndex)
            throws IndexOutOfBoundsException {
        if (columns == null) {
            throw new IndexOutOfBoundsException("column size is zero");
        }
        return columns.get(cIndex).getName();
    }

    @Override
    public ReadOnlyDataColumn appendColumn(String cName, ReadOnlyDataColumn column) {

        if (columnNames == null) {
            columns = new ArrayList<>();
            columnNames = new HashMap<>();
        } else if (columnNames.containsKey(column.getName())) {
            throw new IllegalArgumentException("当前表中已经包含了列：" + column.getName());
        }

        //DataColumn _column = new SimpleDataColumn(this, column);
        DataColumn _column = null; // TODO
        columns.add(_column);
        columnNames.put(_column.getName(), columns.size() - 1);
        if (rows != null) {
            for (DataRow row : rows) {
                row.appendValue(_column.getDefaultValue());
            }
        }
        // TODO，维护索引
        return _column;
    }

    @Override
    public ReadOnlyDataColumn removeColumn(int cIndex)
            throws IndexOutOfBoundsException {
        if (columnNames == null) {
            throw new IndexOutOfBoundsException("column size is zero");
        }
        DataColumn column = columns.remove(cIndex);
        if (rows != null) {
            for (DataRow row : rows) {
                row.remove(cIndex);
            }
        }
        // TODO，维护索引
        return column;
    }

    @Override
    public ReadOnlyDataColumn removeColumn(String cName) {
        if (columnNames == null) {
            return null;
        }
        Integer cIndex = columnNames.get(cName);
        if (cIndex == null) {
            return null;
        }
        return removeColumn(cIndex);
    }

    @Override
    public void clearColumn() {
        columns = null;
        columnNames = null;
        rows = null;
        // TODO，清空索引
    }

    @Override
    public DataRow appendRow() {
        DataRow row = new SimpleDataRow(this);
        if (rows != null) {
            rows = new ArrayList<>();
        }
        rows.add(row);
        return row;
    }

    @Override
    public ReadOnlyDataRow appendRow(ReadOnlyDataRow row) {
        DataRow _row = new SimpleDataRow(this, row);
        if (rows != null) {
            rows = new ArrayList<>();
        }
        rows.add(_row);
        return _row;
    }

    @Override
    public DataRow getRow(int rIndex)
            throws IndexOutOfBoundsException {
        if (rows == null) {
            throw new IndexOutOfBoundsException("row size is zero");
        }
        return rows.get(rIndex);
    }



    @Override
    public Iterator<ReadOnlyDataRow> iterator(int fromIndex, boolean fromInclusive,
                                              int toIndex, boolean toInclusive) {
        if (rows == null || rows.isEmpty()) {
            return new EmptyIterator<>();
        }
        return new SuperIterator<>(
                rows.subList(
                fromInclusive ? fromIndex : fromIndex + 1,
                toInclusive ? toIndex + 1 : toIndex).iterator());
    }



    @Override
    public ReadOnlyDataRow removeRow(int rIndex)
            throws IndexOutOfBoundsException {
        // TODO
        return null;
    }

    @Override
    public void clearRow() {
        rows = null;
        // TODO，清空索引
    }

    @Override
    public void clear() {
        clearColumn();
    }

    @Override
    public DataTable clone() {
        // TODO
        return null;
    }

    @Override
    public DataTable cloneEmpty() {
        // TODO
        return null;
    }

    @Override
    public Iterator<ReadOnlyDataRow> iterator() {
        return rows == null ?
                new EmptyIterator<>() :
                new SuperIterator<>(rows.iterator());
    }
}
