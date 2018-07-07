package gnova.geometry.model;

/**
 * 线环
 * 
 * <p>线环是一种特殊的{@link LineString 线串}，表示收尾相连的一条折线，它不是一种单独的类型，仅用于构建{@link Polygon 多边形}。
 *
 * @see LineString
 * @author birderyu
 * @date 2017/6/21
 * @version 1.0.0
 */
public interface LinearRing
        extends LineString {

    @Override
    default GeometryType getType() {
        return GeometryType.LinearRing;
    }

    @Override
    LinearRing reverse();

    @Override
    LinearRing normalize();

    @Override
    LinearRing clone();

}
