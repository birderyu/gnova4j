package gnova.symbology;

import gnova.core.annotation.NotNull;
import gnova.drawing.d2.Extent;
import gnova.drawing.d2.Matrix;
import gnova.drawing.d2.Point;

/**
 * 点符号
 */
public interface PuntalSymbol extends Symbol, Iterable<SymbolElement> {

    /**
     * 设置符号的原点
     *
     * @return
     */
    @NotNull
    Point getOrigin();

    /**
     * 获取符号的原点
     *
     * @param origin
     */
    void setOrigin(@NotNull Point origin);

    /**
     * 符号的包围盒
     *
     * @return
     */
    @NotNull
    Extent getExtent();

    @NotNull
    Matrix getMatrix();

    void setMatrix(@NotNull Matrix matrix);

    SymbolElement getElementAt(int n);

    void addElement(@NotNull SymbolElement element);

    void setElementAt(@NotNull SymbolElement element, int n);

    /**
     * 获取符号的类型
     *
     * @return
     */
    default SymbolType getType() {
        return SymbolType.Puntal;
    }

}
