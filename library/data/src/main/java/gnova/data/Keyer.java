package gnova.data;

import gnova.core.annotation.NotNull;

/**
 * 主键获取
 */
@FunctionalInterface
public interface Keyer {

    /**
     * 获取主键
     *
     * @return 主键
     */
    @NotNull
    Object getPrimaryKey();

}
