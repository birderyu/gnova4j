package gnova.geometry.model.operator;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.Coordinate;
import gnova.geometry.model.Geometry;

/**
 * 空间距离操作
 *
 * Created by Birderyu on 2017/6/22.
 */
public interface ProximityOperator {

    /**
     * 获取与另一个几何对象的最短距离
     *
     * @param other
     * @return
     */
    double distance(@NotNull Geometry other);

    /**
     * 判断与另一个几何对象的距离是否不大于指定的长度
     *
     * @param other
     * @param distance
     * @return
     */
    boolean isWithinDistance(@NotNull Geometry other, double distance);

    /**
     * 获取与另一个几何对象的最短点
     *
     * @param other
     * @return 返回一个坐标数组，其中包含两个元素，
     *          第一个元素是当前几何对象上距离另一个几何对象距离最近的坐标，
     *          第二个元素是另一个几何对象上距离当前几何对象距离最近的坐标，
     */
    @NotNull
    Coordinate[] nearestPoints(@NotNull Geometry other);
}
