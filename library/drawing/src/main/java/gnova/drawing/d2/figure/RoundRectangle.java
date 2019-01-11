package gnova.drawing.d2.figure;

import gnova.core.annotation.NotNull;

/**
 * 圆角矩形
 */
public interface RoundRectangle extends Rectangle {

    float getRadiusX();

    float getRadiusY();

    @Override
    @NotNull
    default FigureType getType() {
        return FigureType.RoundRectangle;
    }

}
