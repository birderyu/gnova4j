package gnova.drawing.d2.figure;

import gnova.core.annotation.NotNull;

public interface Ring extends Lines {

    @Override
    @NotNull
    default FigureType getType() {
        return FigureType.Ring;
    }

}
