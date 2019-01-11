package gnova.drawing.d2.figure;

import gnova.core.annotation.NotNull;

public interface Arc extends Ellipse {

    float getStartAngle();

    float getSweepAngle();

    @Override
    @NotNull
    default FigureType getType() {
        return FigureType.Arc;
    }

}
