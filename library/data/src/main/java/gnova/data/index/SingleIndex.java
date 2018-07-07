package gnova.data.index;

import gnova.annotation.NotNull;
import gnova.data.Index;

/**
 * 单字段索引
 *
 * @param <E>
 */
public interface SingleIndex<E>
        extends Index<E> {

    /**
     * 获取索引字段名称
     *
     * @return 字段名称，该方法不会返回null
     */
    @NotNull
    String getFieldName();

}
