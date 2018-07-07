package gnova.geometry.model;

import gnova.annotation.Immutable;
import gnova.annotation.NotNull;
import gnova.geometry.model.pattern.Puntal;
import gnova.geometry.model.pattern.Puntal;

/**
 * 点
 *
 * <p>点用于表示一个坐标点，是一个0维的几何对象。
 *
 * @see Geometry
 * @see Puntal
 * @author birderyu
 * @date 2017/6/21
 * @version 1.0.0
 */
@Immutable
public interface Point
        extends Geometry, Puntal {

    /**
     * 获取X坐标
     *
     * @return X坐标值
     */
    default double getX() {
        return getCoordinate().getX();
    }

    /**
     * 获取Y坐标
     *
     * @return Y坐标值
     */
    default double getY() {
        return getCoordinate().getY();
    }

    /**
     * 获取Z坐标
     *
     * @return Z坐标值
     */
    default double getZ() {
        return getCoordinate().getZ();
    }

    /**
     * 获取坐标
     *
     * @return 坐标
     */
    @Override
    @NotNull
    Coordinate getCoordinate();

    @Override
    default GeometryType getType() {
        return GeometryType.Point;
    }

    @Override
    default int getDimension() {
        return Puntal.DIMENSION;
    }

    @Override
    default boolean isSimple() {
        return true;
    }

    @Override
    @NotNull
    Point reverse();

    @Override
    @NotNull
    Point normalize();

    @Override
    @NotNull
    Point clone();

}
