package gnova.orm;

/**
 * Object-Relational Type：对象映射类型
 *
 * @author birderyu
 * @version 1.0.0
 */
public enum ORT {

    /**
     * 非数组、容器的其他类型
     */
    Normal,

    /**
     * 数组类型
     */
    Array,

    /**
     * 列表类型
     */
    List,

    /**
     * 集合类型
     */
    Set,

    /**
     * 容器类型
     */
    Collection

}
