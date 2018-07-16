package gnova.drawing.d2;

/**
 * 投影映射
 */
public interface Projection {

    double toTarget(double distance);

    Point toTarget(Point source);

}
