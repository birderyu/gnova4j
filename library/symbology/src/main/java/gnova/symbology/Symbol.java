package gnova.symbology;

/**
 * 符号
 */
public interface Symbol {

    String getId();

    /**
     * 获取符号的类型
     *
     * @return
     */
    SymbolType getType();

}
