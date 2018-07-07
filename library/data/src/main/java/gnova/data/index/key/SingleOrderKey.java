package gnova.data.index.key;

import gnova.core.annotation.NotNull;

public class SingleOrderKey
        extends SingleKey implements OrderKey {

    /**
     * 构造器
     *
     * @param value
     * @throws IllegalArgumentException 若值是无法排序的，则抛出此异常
     */
    public SingleOrderKey(@NotNull Object value) {
        super(value);
        if (!(value instanceof Comparable)) {
            throw new IllegalArgumentException("key必须实现接口Comparable");
        }
    }

    @Override
    public int compareTo(OrderKey o) {
        return ((Comparable) value).compareTo(((SingleOrderKey) o).value);
    }
}
