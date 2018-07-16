package gnova.symbology;

import gnova.drawing.Color;

/**
 * 简单轮廓
 */
public interface SimpleOutline {

    Color getColor();

    float getWidth();

    DashStyle getDashStyle();

}
