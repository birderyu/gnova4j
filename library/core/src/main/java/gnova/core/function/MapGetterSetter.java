package gnova.core.function;

import gnova.core.annotation.NotNull;

import java.util.Map;

/**
 * 使用Map实现的Getter和Setter
 *
 * @see Getter
 * @see Setter
 * @author birderyu
 * @version 1.0.0
 */
public class MapGetterSetter
        implements Getter, Setter {

    /**
     * Map对象，不会为null
     */
    @NotNull
    private final Map<String, Object> map;

    /**
     * 构建一个使用Map实现的Getter和Setter
     *
     * @param map Map对象，不允许null
     */
    public MapGetterSetter(@NotNull Map<String, Object> map) {
        this.map = map;
    }

    /**
     * 获取一个值
     *
     * @param key 键，不允许为null
     * @param <T> 值的类型
     * @return 字段值，若不存在该字段，则返回null
     * @see Getter#getValue(String)
     */
    @Override
    public <T> T getValue(String key) {
        return (T) map.get(key);
    }

    /**
     * 设置一个值
     *
     * <p>当键存在时，才会设置这个值，若键不存在，该方法不会增加值
     *
     * @param key 键，不允许为null
     * @param value 值
     * @param <T> 值的类型
     * @see Setter#setValue(String, Object)
     */
    @Override
    public <T> void setValue(String key, T value) {
        map.replace(key, value);
    }
}
