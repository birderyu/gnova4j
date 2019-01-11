package gnova.symbology;

import gnova.core.annotation.Immutable;
import gnova.drawing.d2.Extent;
import gnova.drawing.d2.Matrix;
import gnova.drawing.d2.Point;
import gnova.geometry.model.Coordinate;

/**
 * 投影映射
 *
 * <p>投影映射处理两个区域范围之间长度、坐标和变换矩阵的映射关系
 */
@Immutable
public interface Projection {

    /**
     * 来源的范围
     *
     * @return 范围
     */
    Extent getSource();

    /**
     * 目标的范围
     *
     * @return 范围
     */
    Extent getTarget();

    /**
     * 来源的Y轴是否翻转
     *
     * <p>正常情况Y轴正方向向上，Y轴翻转的情况下Y轴正方向向下
     *
     * @return 若来源的Y轴翻转，则返回true，否则返回false
     */
    boolean isReverseSourceY();

    /**
     * 目标的Y轴是否翻转
     *
     * <p>正常情况Y轴正方向向上，Y轴翻转的情况下Y轴正方向向下
     *
     * @return 若目标的Y轴翻转，则返回true，否则返回false
     */
    boolean isReverseTargetY();

    /**
     * 将目标上的一段长度转换为来源上的一段长度
     *
     * @param distance 目标上的长度值
     * @return 来源上的长度值
     */
    double toSource(double distance);

    /**
     * 将目标上的X坐标转换为来源上的X坐标
     *
     * @param targetX 目标上的X坐标
     * @return 来源上的X坐标
     */
    double toSourceX(double targetX);

    /**
     * 将目标上的Y坐标转换为来源上的Y坐标
     *
     * @param targetY 目标上的Y坐标
     * @return 来源上的Y坐标
     */
    double toSourceY(double targetY);

    /**
     * 将目标上的一对坐标值转换为来源上的一对坐标值
     *
     * @param targetX 目标上的X坐标
     * @param targetY 目标上的Y坐标
     * @return 来源上的一对坐标值
     */
    default double[] toSource(double targetX, double targetY) {
        return new double[] { toSourceX(targetX), toSourceY(targetX) };
    }

    /**
     * 将目标上的一个点转换为来源上的一个点
     *
     * @param target 目标上的一个点
     * @return 来源上的一个点
     */
    default Point toSource(Point target) {
        return new Point((float) toSourceX(target.getX()), (float) toSourceY(target.getY()));
    }

    /**
     * 将目标上的一个坐标转换为来源上的一个坐标
     *
     * @param target 目标上的一个坐标
     * @return 来源上的一个坐标
     */
    default Coordinate toSource(Coordinate target) {
        return new Coordinate(toSourceX(target.getX()), toSourceY(target.getY()));
    }

    /**
     * 将目标上的变换矩阵转换为来源上的变换矩阵
     *
     * @param target 标上的变换矩阵
     * @return 来源上的变换矩阵
     */
    Matrix toSource(Matrix target);

    /**
     * 将来源上的一段长度转换为目标上的一段长度
     *
     * @param distance 来源上的长度值
     * @return 目标上的长度值
     */
    double toTarget(double distance);

    /**
     * 将来源上的X坐标转换为目标上的X坐标
     *
     * @param sourceX 来源上的X坐标
     * @return 目标上的X坐标
     */
    double toTargetX(double sourceX);

    /**
     * 将来源上的Y坐标转换为目标上的Y坐标
     *
     * @param sourceY 来源上的Y坐标
     * @return 目标上的Y坐标
     */
    double toTargetY(double sourceY);

    /**
     * 将来源上的一对坐标值转换为目标上的一对坐标值
     *
     * @param sourceX 来源上的X坐标
     * @param sourceY 来源上的Y坐标
     * @return 目标上的一对坐标值
     */
    default double[] toTarget(double sourceX, double sourceY) {
        return new double[] { toTargetX(sourceX), toTargetY(sourceY) };
    }

    /**
     * 将来源上的一个点转换为目标上的一个点
     *
     * @param source 来源上的一个点
     * @return 目标上的一个点
     */
    default Point toTarget(Point source) {
        return new Point((float) toTargetX(source.getX()), (float) toTargetY(source.getY()));
    }

    /**
     * 将来源上的一个坐标转换为目标上的一个坐标
     *
     * @param source 来源上的一个坐标
     * @return 目标上的一个坐标
     */
    default Coordinate toTarget(Coordinate source) {
        return new Coordinate(toTargetX(source.getX()), toTargetY(source.getY()));
    }

    /**
     * 将来源上的变换矩阵转换为目标上的变换矩阵
     *
     * @param source 来源上的变换矩阵
     * @return 目标上的变换矩阵
     */
    Matrix toTarget(Matrix source);
}
