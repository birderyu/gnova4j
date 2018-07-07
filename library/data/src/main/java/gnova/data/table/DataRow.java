package gnova.data.table;

import gnova.annotation.NotNull;
import gnova.function.Setter;

public interface DataRow
        extends ReadOnlyDataRow, Setter {

    @Override
    @NotNull
    DataTable getTable();

    <T> void setValue(int index, T value)
            throws IndexOutOfBoundsException, IllegalArgumentException;

    @Override
    <T> void setValue(@NotNull String cName, T value)
            throws IllegalArgumentException;

    <T> void appendValue(T value);

    <T> T remove(int index) throws IndexOutOfBoundsException;

    @Override
    DataRow clone();

    @Override
    default DataRow toWriteable() {
        return this;
    }
}
