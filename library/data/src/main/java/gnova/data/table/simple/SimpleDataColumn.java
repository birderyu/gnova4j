package gnova.data.table.simple;

import gnova.data.table.ColumnType;
import gnova.data.table.DataColumn;
import gnova.data.table.DataTable;
import gnova.data.table.ReadOnlyDataColumn;

public abstract class SimpleDataColumn
        implements DataColumn {

    private final DataTable table;
    private final String name;
    private final ColumnType type;
    private String caption;
    private int precision;
    private int scale;
    private boolean nullable;
    private boolean caseSensitive;
    private boolean readOnly;
    private boolean primaryKey;
    private Object defaultValue;

    public SimpleDataColumn(DataTable table, String name, ColumnType type) {
        this.table = table;
        this.name = name;
        this.type = type;
    }

    public SimpleDataColumn(DataTable table, ReadOnlyDataColumn column) {
        this.table = table;
        this.name = column.getName();
        this.type = column.getType();
        //this.caption = column.getCaption();
        //this.precision = column.getPrecision();
        //this.scale = column.getScale();
        //this.nullable = column.isNullable();
        //this.caseSensitive = column.isCaseSensitive();
        //this.readOnly = column.isReadOnly();
        //this.primaryKey = column.isPrimaryKey();
        //this.defaultValue = column.getDefaultValue();
    }

    @Override
    public DataTable getTable() {
        return table;
    }

    @Override
    public String getName() {
        return name;
    }

    //@Override
    public String getCaption() {
        return caption;
    }

    @Override
    public ColumnType getType() {
        return null;
    }

    //@Override
    public void setCaption(String caption) {
        this.caption = caption;
    }

    //@Override
    public int getPrecision() {
        return precision;
    }

    //@Override
    public void setPrecision(int precision) {
        this.precision = precision;
    }

    //@Override
    public int getScale() {
        return scale;
    }

    //@Override
    public void setScale(int scale) {
        this.scale = scale;
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    @Override
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    //@Override
    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    //@Override
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    //@Override
    public boolean isReadOnly() {
        return readOnly;
    }

    //@Override
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Override
    public boolean isPrimaryKey() {
        return primaryKey;
    }

    @Override
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }
}
