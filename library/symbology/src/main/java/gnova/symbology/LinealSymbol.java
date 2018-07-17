package gnova.symbology;

import gnova.drawing.d2.Outline;

public interface LinealSymbol extends Symbol {

    Outline getOutline();

    void setOutline(Outline outline);

    /**
     * 获取符号的类型
     *
     * @return
     */
    default SymbolType getType() {
        return SymbolType.Lineal;
    }
}
