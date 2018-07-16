package gnova.drawing;

import gnova.core.annotation.Immutable;

import java.io.Serializable;

@Immutable
public interface Color
        extends Cloneable, Serializable {

    /**
     * 获取一个RGB颜色
     * @param rgb #FFFFFF - rrggbb or #FFFFFFFF - aarrggbb
     * @return
     */
    static Color rgb(String rgb) {
        return new RgbColor(rgb);
    }

    /**
     * 获取一个RGB颜色
     * @param r red，[0, 255]
     * @param g green，[0, 255]
     * @param b blue，[0, 255]
     * @return
     */
    static Color rgb(int r, int g, int b) {
        return new RgbColor(r, g, b);
    }

    /**
     * 获取一个RGB颜色
     * @param r red，[0, 255]
     * @param g green，[0, 255]
     * @param b blue，[0, 255]
     * @param a alpha，[0, 255]
     * @return
     */
    static Color rgb(int r, int g, int b, int a) {
        return new RgbColor(r, g, b, a);
    }

    int toRgbValue(boolean withAlpha);

    String toRgbString(boolean withAlpha);

    boolean isRgb();
}
