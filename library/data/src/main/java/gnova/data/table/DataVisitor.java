package gnova.data.table;

/**
 * 数据访问者
 */
@FunctionalInterface
public interface DataVisitor {

    /**
     * 访问一个数据值
     *
     * @param value 数据值
     * @param <T> 数据值的类型
     * @return 若需要继续访问，则返回true，若希望终止访问，则返回false
     */
    <T> boolean visit(T value);

}
