package gnova.data.index.key;

import gnova.core.annotation.NotNull;

public class SingleUnorderedKey
        extends SingleKey implements UnorderedKey {

    public SingleUnorderedKey(@NotNull Object value) {
        super(value);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
