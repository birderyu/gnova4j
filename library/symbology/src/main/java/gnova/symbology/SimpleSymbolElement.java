package gnova.symbology;

import gnova.drawing.d2.Fill;
import gnova.drawing.d2.Outline;
import gnova.drawing.d2.figure.Figure;
import gnova.drawing.d2.Matrix;

public interface SimpleSymbolElement extends SymbolElement {

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
