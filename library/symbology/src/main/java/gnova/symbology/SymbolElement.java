package gnova.symbology;

import gnova.drawing.d2.Figure;

/**
 * 符号单元
 */
public interface SymbolElement {

    /**
     * 图元
     *
     * @return
     */
    Figure getFigure();

    /**
     * 变换矩阵
     *
     * @return
     */
    Matrix getMatrix();

    /**
     * 轮廓
     *
     * @return
     */
    Outline getOutline();

    /**
     * 填充
     *
     * @return
     */
    Fill getFill();

}
