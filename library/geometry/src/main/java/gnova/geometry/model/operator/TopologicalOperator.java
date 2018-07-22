package gnova.geometry.model.operator;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.*;

/**
 * 空间拓扑操作
 *
 * @author birderyu
 * @date 2017/6/21
 * @version 1.0.0
 */
public interface TopologicalOperator {

    /**
     * 获取对象的边界
     *
     * <p>若几何对象的维度为0，如{@link Point 点类型}和{@link MultiPoint 多点类型}，
     * 那么它的边界将为{@link Geometry#NONE 空几何对象}。
     * <br>此外，针对{@link GeometryCollection 几何集合类型}（并非其子类型）的调用，
     * 若集合中包含不同维度的集合对象，那么它也将返回{@link Geometry#NONE 空几何对象}。
     *
     * @return 对象的边界，不会返回null
     */
    @NotNull
    Geometry getBoundary();

    /**
     * 获取对象的质心点
     *
     * @return 对象的质心点，不会返回null
     */
    @NotNull
    Point getCentroid();

    /**
     * 获取对象内部的点
     *
     * <p>对象内部的点一定位于对象的内部，或者位于对象的边界上。
     *
     * @return 对象内部的点，不会返回null
     */
    @NotNull
    Point getInterior();

    /**
     * 获取对象的最小凸包
     *
     * @return 最小凸包几何对象，不会返回null
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
    Geometry buffer(double distance);

    @NotNull
    Geometry buffer(double distance, int quadrantSegments);

    @NotNull
    Geometry buffer(double distance, int quadrantSegments, int endCapStyle);

    /**
     * 求当前对象与另一个几何对象间的交集
     *
     * @param other
     * @return
     */
    @NotNull
    Geometry intersection(@NotNull Geometry other);

    /**
     * 求当前对象与另一个几何对象间的并集
     *
     * @param other
     * @return
     */
    @NotNull
    Geometry union(@NotNull Geometry other);

    /**
     * 求当前对象与另一个几何对象间的差集
     *
     * @param other
     * @return
     */
    @NotNull
    Geometry difference(@NotNull Geometry other);

    /**
     * 求当前对象与另一个几何对象间的并集减去交集
     *
     * @param other
     * @return
     */
    @NotNull
    Geometry symmetricDifference(@NotNull Geometry other);

    @NotNull
    Geometry triangulation(double distanceTolerance, @NotNull GeometryType resultType);

}
