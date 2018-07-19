package gnova.geometry.io;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.*;

import java.io.OutputStream;
import java.io.Writer;

/**
 * 几何对象输出接口
 *
 * 该接口用于将几何对象转化为其他对象，比如将几何对象转化成字符串对象；
 * 或将一个几何对象写入到输出流。
 */
public interface GeometryWriter<T> extends GeometryIOSettings {

    /**
     * 将几何对象转换成其他对象
     *
     * @param geometry 几何对象，不允许为null
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
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
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    T writePoint(@NotNull Point point) throws GeometryIOException;

    /**
     * 将线串对象转换成其他对象
     *
     * @param lineString 线串对象，不允许为null
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    T writeLineString(@NotNull LineString lineString) throws GeometryIOException;

    /**
     * 将线环对象转换成其他对象
     *
     * @param linearRing 线环对象，不允许为null
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    T writeLinearRing(@NotNull LinearRing linearRing) throws GeometryIOException;

    /**
     * 将多边形对象转换成其他对象
     *
     * @param polygon 多边形对象，不允许为null
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    T writePolygon(@NotNull Polygon polygon) throws GeometryIOException;

    /**
     * 将线串对象转换成其他对象
     *
     * @param geometries 几何集合对象，不允许为null
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    T writeGeometryCollection(@NotNull GeometryCollection geometries) throws GeometryIOException;

    /**
     * 将多多边形对象转换成其他对象
     *
     * @param multiPoint 多多边形对象，不允许为null
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    T writeMultiPoint(@NotNull MultiPoint multiPoint) throws GeometryIOException;

    /**
     * 将多线串对象转换成其他对象
     *
     * @param multiLineString 多线串对象，不允许为null
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    T writeMultiLineString(@NotNull MultiLineString multiLineString) throws GeometryIOException;

    /**
     * 将多多边形对象转换成其他对象
     *
     * @param multiPolygon 多多边形对象，不允许为null
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    T writeMultiPolygon(@NotNull MultiPolygon multiPolygon) throws GeometryIOException;

    /**
     * 将一个几何对象写入到字节输出流
     *
     * @param geometry 几何对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    default void write(@NotNull Geometry geometry, @NotNull OutputStream outputStream)
            throws GeometryIOException, UnsupportedOperationException {

        switch (geometry.getType()) {
            case Point:
                writePoint((Point) geometry, outputStream);
                break;
            case LineString:
                writeLineString((LineString) geometry, outputStream);
                break;
            case LinearRing:
                writeLinearRing((LinearRing) geometry, outputStream);
                break;
            case Polygon:
                writePolygon((Polygon) geometry, outputStream);
                break;
            case MultiPoint:
                writeMultiPoint((MultiPoint) geometry, outputStream);
                break;
            case MultiLineString:
                writeMultiLineString((MultiLineString) geometry, outputStream);
                break;
            case MultiPolygon:
                writeMultiPolygon((MultiPolygon) geometry, outputStream);
                break;
            case GeometryCollection:
                writeGeometryCollection((GeometryCollection) geometry, outputStream);
                break;
            default:
                throw new GeometryIOException("不支持的几何类型：" + geometry.getType());
        }

    }

    /**
     * 将一个点对象写入到字节输出流
     *
     * @param point 点对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writePoint(@NotNull Point point, @NotNull OutputStream outputStream)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 将一个几何对象写入到字节输出流
     *
     * @param lineString 线串对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writeLineString(@NotNull LineString lineString, @NotNull OutputStream outputStream)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 将一个线环对象写入到字节输出流
     *
     * @param linearRing 线环对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writeLinearRing(@NotNull LinearRing linearRing, @NotNull OutputStream outputStream)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 将一个多边形对象写入到字节输出流
     *
     * @param polygon 多边形对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writePolygon(@NotNull Polygon polygon, @NotNull OutputStream outputStream)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 将一个几何集合对象写入到字节输出流
     *
     * @param geometries 几何集合对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writeGeometryCollection(@NotNull GeometryCollection geometries, @NotNull OutputStream outputStream)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 将一个多点对象写入到字节输出流
     *
     * @param multiPoint 多点对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writeMultiPoint(@NotNull MultiPoint multiPoint, @NotNull OutputStream outputStream)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 将一个多线串对象写入到字节输出流
     *
     * @param multiLineString 多线串对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writeMultiLineString(@NotNull MultiLineString multiLineString, @NotNull OutputStream outputStream)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 将一个多多边形对象写入到字节输出流
     *
     * @param multiPolygon 多多边形对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writeMultiPolygon(@NotNull MultiPolygon multiPolygon, @NotNull OutputStream outputStream)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 将一个几何对象写入到字符输出流
     *
     * @param geometry 几何对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    default void write(@NotNull Geometry geometry, @NotNull Writer writer)
            throws GeometryIOException, UnsupportedOperationException {

        switch (geometry.getType()) {
            case Point:
                writePoint((Point) geometry, writer);
                break;
            case LineString:
                writeLineString((LineString) geometry, writer);
                break;
            case LinearRing:
                writeLinearRing((LinearRing) geometry, writer);
                break;
            case Polygon:
                writePolygon((Polygon) geometry, writer);
                break;
            case MultiPoint:
                writeMultiPoint((MultiPoint) geometry, writer);
                break;
            case MultiLineString:
                writeMultiLineString((MultiLineString) geometry, writer);
                break;
            case MultiPolygon:
                writeMultiPolygon((MultiPolygon) geometry, writer);
                break;
            case GeometryCollection:
                writeGeometryCollection((GeometryCollection) geometry, writer);
                break;
            default:
                throw new GeometryIOException("不支持的几何类型：" + geometry.getType());
        }

    }

    /**
     * 将一个点对象写入到字符输出流
     *
     * @param point 点对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writePoint(@NotNull Point point, @NotNull Writer writer)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 将一个几何对象写入到字符输出流
     *
     * @param lineString 线串对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writeLineString(@NotNull LineString lineString, @NotNull Writer writer)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 将一个线环对象写入到字符输出流
     *
     * @param linearRing 线环对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writeLinearRing(@NotNull LinearRing linearRing, @NotNull Writer writer)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 将一个多边形对象写入到字符输出流
     *
     * @param polygon 多边形对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writePolygon(@NotNull Polygon polygon, @NotNull Writer writer)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 将一个几何集合对象写入到字符输出流
     *
     * @param geometries 几何集合对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writeGeometryCollection(@NotNull GeometryCollection geometries, @NotNull Writer writer)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 将一个多点对象写入到字符输出流
     *
     * @param multiPoint 多点对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writeMultiPoint(@NotNull MultiPoint multiPoint, @NotNull Writer writer)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 将一个多线串对象写入到字符输出流
     *
     * @param multiLineString 多线串对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writeMultiLineString(@NotNull MultiLineString multiLineString, @NotNull Writer writer)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 将一个多多边形对象写入到字符输出流
     *
     * @param multiPolygon 多多边形对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    void writeMultiPolygon(@NotNull MultiPolygon multiPolygon, @NotNull Writer writer)
            throws GeometryIOException, UnsupportedOperationException;
}
