package gnova.data.index.key;

import gnova.core.annotation.Checked;

public class MultiUnorderedKey
        extends MultiKey implements UnorderedKey {

    public MultiUnorderedKey(@Checked Object... values) {
        super(values);
    }

}
