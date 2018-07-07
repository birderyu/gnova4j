package gnova.data.index.key;

import gnova.annotation.Checked;

public class MultiOrderKey
        extends MultiKey implements OrderKey {

    public MultiOrderKey(@Checked Object... values) {
        super(values);
    }

    @Override
    public int compareTo(OrderKey o) {
        for (Object value : values) {
            int r = ((Comparable) value).compareTo(((SingleOrderKey) o).value);
            if (r != 0) return r;
        }
        return 0;
    }
}
