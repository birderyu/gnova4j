package gnova.data.wrapper;

import gnova.data.Columns;

import java.util.*;

public class ListColumns<E>
        implements Columns<E> {

    private final List<E> list;
    private final Map<String, Integer> names;
    private final Map<Integer, String> indices;

    public ListColumns() {
        this.list = new ArrayList<>();
        this.names = new HashMap<>();
        this.indices = new HashMap<>();
    }

    @Override
    public int getColumnSize() {
        return list.size();
    }

    @Override
    public boolean isColumnEmpty() {
        return list.isEmpty();
    }

    @Override
    public E getColumn(String cName) {
        Integer cIndex = names.get(cName);
        if (cIndex == null) {
            return null;
        }
        return list.get(cIndex);
    }

    @Override
    public E getColumn(int cIndex) throws IndexOutOfBoundsException {
        return list.get(cIndex);
    }

    @Override
    public boolean containsColumn(String cName) {
        return names.containsKey(cName);
    }

    @Override
    public int getColumnIndex(String cName) {
        Integer cIndex = names.get(cName);
        if (cIndex == null) {
            return -1;
        }
        return cIndex;
    }

    @Override
    public String getColumnName(int cIndex) throws IndexOutOfBoundsException {
        return indices.get(cIndex);
    }

    @Override
    public Iterator<E> columnIterator() {
        return list.iterator();
    }

    @Override
    public Iterator<E> columnIterator(String[] columns, boolean inclusion)
            throws IllegalArgumentException {

        if (columns == null || columns.length == 0) {
            return columnIterator();
        }

        // 构造结果中实际包含的列
        Set<String> projections = new HashSet<>();
        if (inclusion) {
            for (String column : columns) {
                projections.add(column);
            }
        } else {
            projections = names.keySet();
            for (String column : columns) {
                projections.remove(column);
            }
        }

        // 返回结果
        List<E> nl = new ArrayList<>();
        for (String column : projections) {
            Integer i = names.get(column);
            if (i == null) {
                throw new IllegalArgumentException("列名错误：不包含名为"
                        + column
                        + "的列");
            }
            nl.add(list.get(i));
        }
        return nl.iterator();
    }

    @Override
    public E appendColumn(String cName, E column) throws IllegalArgumentException {
        if (names.containsKey(cName)) {
            throw new IllegalArgumentException();
        }
        if (!list.add(column)) {
            throw new IllegalArgumentException();
        }
        names.put(cName, list.size() - 1);
        indices.put(list.size() - 1, cName);
        return column;
    }

    @Override
    public E removeColumn(int cIndex) throws IndexOutOfBoundsException {
        String name = indices.remove(cIndex);
        if (name == null) {
            throw new IndexOutOfBoundsException();
        }
        // TODO，维护indices
        names.remove(name);
        for (Map.Entry<String, Integer> entry : names.entrySet()) {
            int v = entry.getValue();
            if (v > cIndex) {
                entry.setValue(v + 1);
            }
        }
        return list.remove(cIndex);
    }

    @Override
    public E removeColumn(String cName) {
        Integer cIndex = names.get(cName);
        if (cIndex == null) {
            return null;
        }
        return list.remove((int) cIndex);
    }

    @Override
    public void clearColumn() {
        list.clear();
        names.clear();
        indices.clear();
    }
}
