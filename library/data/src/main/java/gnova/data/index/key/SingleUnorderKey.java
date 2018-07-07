package gnova.data.index.key;

import gnova.annotation.NotNull;

public class SingleUnorderKey
        extends SingleKey implements UnorderKey {

    public SingleUnorderKey(@NotNull Object value) {
        super(value);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
