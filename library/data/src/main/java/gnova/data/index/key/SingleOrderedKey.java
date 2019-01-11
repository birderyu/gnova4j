package gnova.data.index.key;

import gnova.core.annotation.NotNull;

public class SingleOrderedKey
        extends SingleKey implements OrderedKey {

    /**
     * 构造器
     *
     * @param value
     * @throws IllegalArgumentException 若值是无法排序的，则抛出此异常
     */
    public SingleOrderedKey(@NotNull Object value)
            throws IllegalArgumentException {
        super(value);
        if (!(value instanceof Comparable)) {
            throw new IllegalArgumentException("key必须实现接口Comparable");
        }
    }

    @Override
    public int compareTo(OrderedKey o) {
        return ((Comparable) value).compareTo(((SingleOrderedKey) o).value);
    }
}
