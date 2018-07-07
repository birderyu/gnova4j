package gnova.geometry.model.operator;

import gnova.geometry.model.Geometry;

/**
 * 仿射变换操作
 */
public interface AffineOperator {

    /**
     * 平移
     *
     * @param control
     */
    Geometry translate(Geometry control);

    void scale();

    void rotate();

    void symmetry();

    void shear();

}
