package gnova.symbology;

import gnova.drawing.d2.Fill;

public interface PolygonalSymbol extends Symbol {

    Fill getFill();

    void setFill(Fill fill);

    /**
     * 获取符号的类型
     *
     * @return
     */
    default SymbolType getType() {
        return SymbolType.Lineal;
    }

}
