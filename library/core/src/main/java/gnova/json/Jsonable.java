package gnova.json;

import gnova.annotation.NotNull;

/**
 * JSON实体
 *
 * @author birderyu
 * @version 1.0.0
 */
public interface Jsonable extends Cloneable {

    /**
     * 是否是JSON对象
     *
     * @return 若是JSON对象，则返回true，否则返回false
     */
    default boolean isObject() {
        return false;
    }

    /**
     * 是否是JSON数组
     *
     * @return 若是JSON数组，则返回true，否则返回false
     */
    default boolean isArray() {
        return false;
    }

    /**
     * 转换为JSON对象
     *
     * @return JSON对象，默认为null
     */
    default JsonObject asObject() {
        if (isObject()) {
            return (JsonObject) this;
        }
        return null;
    }

    /**
     * 转换为JSON数组
     *
     * @return JSON数组，默认为null
     */
    default JsonArray asArray() {
        if (isArray()) {
            return (JsonArray) this;
        }
        return null;
    }

    /**
     * 转换为JSON字符串
     *
     * @return 字符串
     * @see Object#toString()
     */
    @Override
    @NotNull
    String toString();

    /**
     * 克隆当前的对象
     *
     * @return 克隆之后的对象
     * @see Object#clone()
     */
    @NotNull
    Jsonable clone();
}
