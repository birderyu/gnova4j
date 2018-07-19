package gnova.geometry.model.pattern;

/**
 * 面型
 * 
 * <p>面型对象用于表示几何形态为面状的对象，如{@link gnova.geometry.model.Polygon 多边形}和{@link gnova.geometry.model.MultiPolygon 多多边形}。
 *
 * @author birderyu
 * @date 2017/6/21
 * @version 1.0.0
 */
public interface Polygonal {

    /**
     * 维度值
     */
    int DIMENSION = 2;

    double getArea();

}
