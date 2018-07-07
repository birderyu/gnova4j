package gnova.data.index.key;

import gnova.annotation.Checked;

public class MultiUnorderKey
        extends MultiKey implements UnorderKey  {

    public MultiUnorderKey(@Checked Object... values) {
        super(values);
    }

}
