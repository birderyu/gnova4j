package gnova.drawing.d2;

import gnova.drawing.Color;
import gnova.drawing.d2.DashStyle;
import gnova.drawing.d2.Outline;

/**
 * 简单轮廓
 */
public interface SimpleOutline extends Outline {

    /**
     * 轮廓的颜色
     *
     * @return
     */
    Color getColor();

    /**
     * 轮廓的宽度
     *
     * @return
     */
    float getWidth();

    /**
     * 轮廓的线型
     *
     * @return
     */
    DashStyle getDashStyle();

    /**
     * 自定义线型，仅当DashStyle为Custom时有效
     *
     * @return
     */
    float[] getCustomDash();

    void getStartCap();

    void getEndCap();

}
