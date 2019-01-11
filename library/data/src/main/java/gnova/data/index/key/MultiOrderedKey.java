package gnova.data.index.key;

import gnova.core.annotation.Checked;

public class MultiOrderedKey
        extends MultiKey implements OrderedKey {

    public MultiOrderedKey(@Checked Object... values) {
        super(values);
    }

    @Override
    public int compareTo(OrderedKey o) {
        for (Object value : values) {
            int r = ((Comparable) value).compareTo(((SingleOrderedKey) o).value);
            if (r != 0) return r;
        }
        return 0;
    }
}
