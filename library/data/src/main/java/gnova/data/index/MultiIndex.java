package gnova.data.index;

import gnova.annotation.NotNull;
import gnova.data.Index;

/**
 * 多字段索引
 *
 * @param <E>
 */
public interface MultiIndex<E>
        extends Index<E> {

    /**
     * 获取多字段索引的所有字段名称
     *
     * @return
     */
    @NotNull
    String[] getFieldNames();

    /**
     * 获取字段名称对应在索引中的位置
     *
     * @param fieldName
     * @return 若字段名称在索引中不存在，则返回-1
     */
    int indexOf(@NotNull String fieldName);

}
