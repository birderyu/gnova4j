package gnova.geometry.io;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.*;

/**
 * 几何对象输出接口
 *
 * <p>该接口用于将几何对象转化为其他对象
 */
public interface GeometryWriter<T> extends GeometryIOSettings {

    /**
     * 将几何对象转换成其他对象
     *
     * @param geometry 几何对象，不允许为null
     * @return 其他对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default T write(@NotNull Geometry geometry) throws GeometryIOException {
        switch (geometry.getType()) {
            case Point:
                return writePoint((Point) geometry);
            case LineString:
                return writeLineString((LineString) geometry);
            case LinearRing:
                return writeLinearRing((LinearRing) geometry);
            case Polygon:
                return writePolygon((Polygon) geometry);
            case MultiPoint:
                return writeMultiPoint((MultiPoint) geometry);
            case MultiLineString:
                return writeMultiLineString((MultiLineString) geometry);
            case MultiPolygon:
                return writeMultiPolygon((MultiPolygon) geometry);
            case GeometryCollection:
                return writeGeometryCollection((GeometryCollection) geometry);
        }
        throw new GeometryIOException("不支持的几何类型：" + geometry.getType());
    }

    /**
     * 将点对象转换成其他对象
     *
     * @param point 点对象，不允许为null
     * @return 其他对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    T writePoint(@NotNull Point point) throws GeometryIOException;

    /**
     * 将线串对象转换成其他对象
     *
     * @param lineString 线串对象，不允许为null
     * @return 其他对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    T writeLineString(@NotNull LineString lineString) throws GeometryIOException;

    /**
     * 将线环对象转换成其他对象
     *
     * @param linearRing 线环对象，不允许为null
     * @return 其他对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    T writeLinearRing(@NotNull LinearRing linearRing) throws GeometryIOException;

    /**
     * 将多边形对象转换成其他对象
     *
     * @param polygon 多边形对象，不允许为null
     * @return 其他对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    T writePolygon(@NotNull Polygon polygon) throws GeometryIOException;

    /**
     * 将线串对象转换成其他对象
     *
     * @param geometries 几何集合对象，不允许为null
     * @return 其他对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    T writeGeometryCollection(@NotNull GeometryCollection geometries) throws GeometryIOException;

    /**
     * 将多多边形对象转换成其他对象
     *
     * @param multiPoint 多多边形对象，不允许为null
     * @return 其他对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    T writeMultiPoint(@NotNull MultiPoint multiPoint) throws GeometryIOException;

    /**
     * 将多线串对象转换成其他对象
     *
     * @param multiLineString 多线串对象，不允许为null
     * @return 其他对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    T writeMultiLineString(@NotNull MultiLineString multiLineString) throws GeometryIOException;

    /**
     * 将多多边形对象转换成其他对象
     *
     * @param multiPolygon 多多边形对象，不允许为null
     * @return 其他对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    T writeMultiPolygon(@NotNull MultiPolygon multiPolygon) throws GeometryIOException;

}
