package gnova.drawing.d2.figure;

import gnova.core.annotation.NotNull;

public interface RoundSquare extends Square {

    float getRadiusX();

    float getRadiusY();

    @Override
    @NotNull
    default FigureType getType() {
        return FigureType.RoundSquare;
    }

}
