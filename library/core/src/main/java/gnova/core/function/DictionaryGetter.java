package gnova.core.function;

import gnova.core.annotation.NotNull;
import gnova.core.Dictionary;
import gnova.core.KeyValue;

/**
 * 使用Dictionary实现的Getter
 *
 * @see Getter
 * @author birderyu
 * @version 1.0.0
 */
public class DictionaryGetter
        implements Getter {

    /**
     * Dictionary对象，不会为null
     */
    @NotNull
    private final Dictionary dictionary;

    /**
     * 构建一个使用Dictionary实现的Getter
     *
     * @param dictionary Dictionary对象，不允许null
     */
    public DictionaryGetter(@NotNull Dictionary dictionary) {
        this.dictionary = dictionary;
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
        KeyValue kv = dictionary.get(key);
        return kv == null ? null : kv.getValue();
    }
}
