package gnova.geometry.model.operator;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.Geometry;

/**
 * 空间简化算法
 */
public interface SimplifierOperator {

    @NotNull
    Geometry simplify(@NotNull SimplifierFunction function, double distanceTolerance);

}
