package gnova.data.table;

/**
 * 数据行访问者
 */
@FunctionalInterface
public interface RowVisitor {

    /**
     * 访问一个行对象
     *
     * @param row 只读的行对象
     * @return 若需要继续访问，则返回true，若希望终止访问，则返回false
     */
    boolean visit(ReadOnlyDataRow row);

}
