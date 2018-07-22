package gnova.geometry.model.operator;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.Geometry;

/**
 * 空间仿射变换操作
 */
public interface AffineOperator {

    @NotNull
    Geometry translate(double offsetX, double offsetY);

    @NotNull
    Geometry scale(double baseX, double baseY, double scaleX, double scaleY);

    @NotNull
    Geometry scale(double scaleX, double scaleY);

    /**
     *
     * @param baseX
     * @param baseY
     * @param angle 角度值
     * @return
     */
    @NotNull
    Geometry rotate(double baseX, double baseY, double angle);

    /**
     *
     * @param angle 角度值
     * @return
     */
    @NotNull
    Geometry rotate(double angle);

    @NotNull
    Geometry shear(double shearX, double shearY);

    @NotNull
    Geometry reflection(double baseX, double baseY, double x, double y);

    @NotNull
    Geometry reflection(double x, double y);

}
