package gnova.geometry.factory;

import gnova.geometry.model.Coordinate;
import gnova.geometry.model.CoordinateSequence;

/**
 * 坐标序列工厂
 */
public interface CoordinateSequenceFactory {

    /**
     * 创建一个坐标序列
     *
     * @param coordinates 坐标的数组
     * @return 坐标序列
     */
    CoordinateSequence create(Coordinate[] coordinates);

    /**
     * 创建一个坐标序列
     *
     * @param coordinates 坐标序列
     * @return 坐标序列
     */
    CoordinateSequence create(CoordinateSequence coordinates);

}
