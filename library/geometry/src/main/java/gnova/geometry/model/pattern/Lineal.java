package gnova.geometry.model.pattern;

/**
 * 线型（Linear）
 *
 * <p>线型对象用于表示几何形态为线状的对象，如{@link gnova.geometry.model.LineString 线串}和{@link gnova.geometry.model.MultiLineString 多线串}。
 * 
 * @author birderyu
 * @date 2017/6/21
 * @version 1.0.0
 */
public interface Lineal {

    /**
     * 维度值
     */
    int DIMENSION = 1;

    double getLength();

}
