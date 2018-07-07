package gnova.data.index.key;

import gnova.core.annotation.Checked;

public class MultiUnorderKey
        extends MultiKey implements UnorderKey  {

    public MultiUnorderKey(@Checked Object... values) {
        super(values);
    }

}
