package gnova.data.table;

import gnova.geometry.model.BoundingBox;
import gnova.geometry.model.Polygon;

import java.util.Set;

/**
 * 表统计计算
 */
public interface StatisticalOperator {

    /**
     * 求最大值
     *
     * @param cName
     * @param selection
     * @param params
     * @return
     */
    Object getMaximum(String cName, String selection, Object[] params);

    /**
     * 求最小值
     *
     * @param cName
     * @param selection
     * @param params
     * @return
     */
    Object getMinimum(String cName, String selection, Object[] params);

    Object getSummation(String cName, String selection, Object[] params);

    Object average(String cName, String selection, Object[] params);

    /**
     * 求交集
     *
     * @param cName
     * @param selection
     * @param params
     * @return
     */
    Set getIntersection(String cName, String selection, Object[] params);

    /**
     * 求并集
     *
     * @param cName
     * @param selection
     * @param params
     * @return
     */
    Set getUnion(String cName, String selection, Object[] params);

    /**
     * 差集
     *
     * @param cName
     * @param selection
     * @param params
     * @return
     */
    Set getDifference(String cName, String selection, Object[] params);

    /**
     * 并集减去交集
     *
     * @param cName
     * @param selection
     * @param params
     * @return
     */
    Set getSymmetricDifference(String cName, String selection, Object[] params);

    /**
     * 获取包围盒
     *
     * @param cName
     * @param selection
     * @param params
     * @return
     */
    BoundingBox getBoundingBox(String cName, String selection, Object[] params);

    /**
     * 获取最小凸包
     *
     * @param cName
     * @param selection
     * @param params
     * @return
     */
    Polygon getConvexHull(String cName, String selection, Object[] params);

}
