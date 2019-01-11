package gnova.data.index.key;

/**
 * 无序索引的键
 */
public interface UnorderedKey
        extends Key {

    /**
     * 获取索引键的散列码
     *
     * @return
     */
    @Override
    int hashCode();

}
