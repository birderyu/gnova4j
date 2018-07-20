package gnova.geometry.model.operator;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.*;

/**
 * 空间拓扑操作
 *
 * @author birderyu
 * @date 2017/6/21
 */
public interface TopologicalOperator {

    /**
     * 获取边界
     *
     * @return
     */
    @NotNull
    Geometry getBoundary();

    @NotNull
    Point getCentroid();

    @NotNull
    Point getInterior();

    /**
     * 获取最小凸包
     *
     * @return
     */
    @NotNull
    Geometry convexHull();

    /**
     * 空间裁剪
     *
     * @param bbox
     * @return
     */
    @NotNull
    Geometry clip(@NotNull BoundingBox bbox);

    @NotNull
    Geometry split(@NotNull Geometry bladeIn);

    @NotNull
    Geometry cut(@NotNull Geometry bladeIn);

    @NotNull
    Polygon buffer(double distance);

    @NotNull
    Polygon buffer(double distance, int quadrantSegments);

    @NotNull
    Polygon buffer(double distance, int quadrantSegments, int endCapStyle);

    /**
     * 求交集
     *
     * @param other
     * @return
     */
    @NotNull
    Geometry intersection(@NotNull Geometry other);

    /**
     * 求并集
     *
     * @param other
     * @return
     */
    @NotNull
    Geometry union(@NotNull Geometry other);

    /**
     * 求差集
     *
     * @param other
     * @return
     */
    @NotNull
    Geometry difference(@NotNull Geometry other);

    /**
     * 求并集减去交集
     *
     * @param other
     * @return
     */
    @NotNull
    Geometry symmetricDifference(@NotNull Geometry other);

    @NotNull
    Geometry simplify(double distanceTolerance);

    @NotNull
    Geometry triangulation(double distanceTolerance, @NotNull GeometryType resultType);

}
