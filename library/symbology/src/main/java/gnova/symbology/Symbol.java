package gnova.symbology;

/**
 * 符号
 */
public interface Symbol {

    /**
     * 获取符号的ID
     *
     * @return 符号的ID
     */
    String getId();

    /**
     * 获取符号的类型
     *
     * @return
     */
    SymbolType getType();

}
