package gnova.data;

import gnova.annotation.NotNull;

public abstract class AbstractReadOnlyRecord
        implements ReadOnlyRecord {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Keyer)) {
            return false;
        }
        return ((Keyer) o).getPrimaryKey().equals(getPrimaryKey());
    }

    @Override
    public int hashCode() {
        return getPrimaryKey().hashCode();
    }

    @NotNull
    public abstract AbstractReadOnlyRecord clone();

}
