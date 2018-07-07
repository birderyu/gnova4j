package gnova.geometry.model.operator;

import gnova.geometry.model.MultiLineString;
import gnova.geometry.model.MultiPolygon;

/**
 * 空间集合操作
 */
public interface CollectionOperator {

    /**
     * 合并线操作
     *
     * 将集合中的所有线状要素合并，产生一个多线的结果集合
     *
     * @return 多线串，若不包含任何线状要素，则返回null
     */
    MultiLineString lineMerge();

    /**
     * 多边形化
     *
     * 将集合中的所有要素构建多个多边形
     *
     * @return
     */
    MultiPolygon polygonize();

}
