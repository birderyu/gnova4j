package gnova.json;

import gnova.annotation.NotNull;

/**
 * JSON对象的代理
 *
 * @param <JO> 实际JSON对象的类型
 * @see Jsonable
 * @see Iterable
 * @see JsonKeyValue
 * @author birderyu
 * @version 1.0.0
 */
public interface JsonObject<JO>
        extends Jsonable, Iterable<JsonKeyValue> {

    /**
     * 获取被代理的JSON对象本身
     *
     * @return JSON对象
     */
    JO getSubject();

    /**
     * 获取JSON对象中键值对的个数
     *
     * @return 键值对的个数
     */
    int size();

    /**
     * 判断JSON对象是否为空
     *
     * @return 若为空，则返回true，否则返回false
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 判断是否包含某个键
     *
     * @param key 键，不允许为null
     * @return 若包含该键，则返回true，否则返回false
     */
    boolean contains(@NotNull String key);

    /**
     * 通过键获取一个值
     *
     * @param key 键
     * @param <T> 值的类型
     * @return 值，若不存在该值，则返回null
     */
    <T> T get(@NotNull String key);

    /**
     * 添加一个键值对到JSON对象中
     *
     * @param key 键，不允许为null
     * @param value 值
     * @param <T> 值的类型
     * @return 该JSON对象本身，不会返回null
     */
    @NotNull
    <T> JsonObject<JO> append(@NotNull String key, T value);

    /**
     * 在JSON对象中通过键移除一个值
     *
     * @param key 键，不允许为null
     * @return 该JSON对象本身，不会返回null
     */
    @NotNull
    JsonObject<JO> remove(@NotNull String key);

    /**
     * 清空JSON对象中的所有键值对
     *
     * @return 该JSON对象本身，不会返回null
     */
    @NotNull
    JsonObject<JO> clear();

    /**
     * 克隆当前的对象
     *
     * @return 克隆之后的对象
     * @see Object#clone()
     */
    @Override
    @NotNull
    JsonObject<JO> clone();

    /**
     * 是否是JSON对象
     *
     * @return 若是JSON对象，则返回true，否则返回false
     * @see Jsonable#isObject()
     */
    @Override
    default boolean isObject() {
        return true;
    }
}
