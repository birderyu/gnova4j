package gnova.data;

import gnova.core.annotation.NotNull;

public abstract class AbstractRecord
        extends AbstractReadOnlyRecord implements Record {

    @NotNull
    public abstract AbstractRecord clone();

}
