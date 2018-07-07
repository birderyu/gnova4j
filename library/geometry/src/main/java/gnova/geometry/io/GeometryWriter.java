package gnova.geometry.io;

import gnova.annotation.NotNull;
import gnova.geometry.model.*;

/**
 * 几何对象输出接口
 *
 * 该接口用于将几何对象转化为其他对象，比如将几何对象转化成字符串对象；
 * 或将一个几何对象写入到输出流。
 */
public interface GeometryWriter {

    /**
     * 将几何对象转换成其他对象
     *
     * @param geometry 几何对象，不允许为null
     * @param clazz 其他对象的类，不允许为null
     * @param <T> 其他对象的类型
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    default <T> T write(@NotNull Geometry geometry, @NotNull Class<T> clazz) throws GeometryIOException {
        switch (geometry.getType()) {
            case Point:
                return writePoint((Point) geometry, clazz);
            case LineString:
                return writeLineString((LineString) geometry, clazz);
            case LinearRing:
                return writeLinearRing((LinearRing) geometry, clazz);
            case Polygon:
                return writePolygon((Polygon) geometry, clazz);
            case MultiPoint:
                return writeMultiPoint((MultiPoint) geometry, clazz);
            case MultiLineString:
                return writeMultiLineString((MultiLineString) geometry, clazz);
            case MultiPolygon:
                return writeMultiPolygon((MultiPolygon) geometry, clazz);
            case GeometryCollection:
                return writeGeometryCollection((GeometryCollection) geometry, clazz);
        }
        throw new GeometryIOException("不支持的几何类型：" + geometry.getType());
    }

    /**
     * 将点对象转换成其他对象
     *
     * @param point 点对象，不允许为null
     * @param clazz 其他对象的类，不允许为null
     * @param <T> 其他对象的类型
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    <T> T writePoint(@NotNull Point point, @NotNull Class<T> clazz) throws GeometryIOException;

    /**
     * 将线串对象转换成其他对象
     *
     * @param lineString 线串对象，不允许为null
     * @param clazz 其他对象的类，不允许为null
     * @param <T> 其他对象的类型
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    <T> T writeLineString(@NotNull LineString lineString, @NotNull Class<T> clazz) throws GeometryIOException;

    /**
     * 将线环对象转换成其他对象
     *
     * @param linearRing 线环对象，不允许为null
     * @param clazz 其他对象的类，不允许为null
     * @param <T> 其他对象的类型
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    <T> T writeLinearRing(@NotNull LinearRing linearRing, @NotNull Class<T> clazz) throws GeometryIOException;

    /**
     * 将多边形对象转换成其他对象
     *
     * @param polygon 多边形对象，不允许为null
     * @param clazz 其他对象的类，不允许为null
     * @param <T> 其他对象的类型
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    <T> T writePolygon(@NotNull Polygon polygon, @NotNull Class<T> clazz) throws GeometryIOException;

    /**
     * 将线串对象转换成其他对象
     *
     * @param geometries 几何集合对象，不允许为null
     * @param clazz 其他对象的类，不允许为null
     * @param <T> 其他对象的类型
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    <T> T writeGeometryCollection(@NotNull GeometryCollection geometries, @NotNull Class<T> clazz) throws GeometryIOException;

    /**
     * 将多多边形对象转换成其他对象
     *
     * @param multiPoint 多多边形对象，不允许为null
     * @param clazz 其他对象的类，不允许为null
     * @param <T> 其他对象的类型
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    <T> T writeMultiPoint(@NotNull MultiPoint multiPoint, @NotNull Class<T> clazz) throws GeometryIOException;

    /**
     * 将多线串对象转换成其他对象
     *
     * @param multiLineString 多线串对象，不允许为null
     * @param clazz 其他对象的类，不允许为null
     * @param <T> 其他对象的类型
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    <T> T writeMultiLineString(@NotNull MultiLineString multiLineString, @NotNull Class<T> clazz) throws GeometryIOException;

    /**
     * 将多多边形对象转换成其他对象
     *
     * @param multiPolygon 多多边形对象，不允许为null
     * @param clazz 其他对象的类，不允许为null
     * @param <T> 其他对象的类型
     * @return 其他对象
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    <T> T writeMultiPolygon(@NotNull MultiPolygon multiPolygon, @NotNull Class<T> clazz) throws GeometryIOException;

    /**
     * 将一个几何对象写入到输出流
     *
     * @param geometry 几何对象，不允许为null
     * @param output 输出流，应该是{@link java.io.OutputStream}或{@link java.io.Writer}，视具体情况而定，不允许为null
     * @param <T> 输出流的类型
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default <T> void write(@NotNull Geometry geometry, @NotNull T output) throws GeometryIOException {

        switch (geometry.getType()) {
            case Point:
                writePoint((Point) geometry, output);
                break;
            case LineString:
                writeLineString((LineString) geometry, output);
                break;
            case LinearRing:
                writeLinearRing((LinearRing) geometry, output);
                break;
            case Polygon:
                writePolygon((Polygon) geometry, output);
                break;
            case MultiPoint:
                writeMultiPoint((MultiPoint) geometry, output);
                break;
            case MultiLineString:
                writeMultiLineString((MultiLineString) geometry, output);
                break;
            case MultiPolygon:
                writeMultiPolygon((MultiPolygon) geometry, output);
                break;
            case GeometryCollection:
                writeGeometryCollection((GeometryCollection) geometry, output);
                break;
            default:
                throw new GeometryIOException("不支持的几何类型：" + geometry.getType());
        }

    }

    /**
     * 将一个点对象写入到输出流
     *
     * @param point 点对象，不允许为null
     * @param output 输出流，应该是{@link java.io.OutputStream}或{@link java.io.Writer}，视具体情况而定，不允许为null
     * @param <T> 输出流的类型
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    <T> void writePoint(@NotNull Point point, @NotNull T output) throws GeometryIOException;

    /**
     * 将一个几何对象写入到输出流
     *
     * @param lineString 线串对象，不允许为null
     * @param output 输出流，应该是{@link java.io.OutputStream}或{@link java.io.Writer}，视具体情况而定，不允许为null
     * @param <T> 输出流的类型
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    <T> void writeLineString(@NotNull LineString lineString, @NotNull T output) throws GeometryIOException;

    /**
     * 将一个线环对象写入到输出流
     *
     * @param linearRing 线环对象，不允许为null
     * @param output 输出流，应该是{@link java.io.OutputStream}或{@link java.io.Writer}，视具体情况而定，不允许为null
     * @param <T> 输出流的类型
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    <T> void writeLinearRing(@NotNull LinearRing linearRing, @NotNull T output) throws GeometryIOException;

    /**
     * 将一个多边形对象写入到输出流
     *
     * @param polygon 多边形对象，不允许为null
     * @param output 输出流，应该是{@link java.io.OutputStream}或{@link java.io.Writer}，视具体情况而定，不允许为null
     * @param <T> 输出流的类型
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    <T> void writePolygon(@NotNull Polygon polygon, @NotNull T output) throws GeometryIOException;

    /**
     * 将一个几何集合对象写入到输出流
     *
     * @param geometries 几何集合对象，不允许为null
     * @param output 输出流，应该是{@link java.io.OutputStream}或{@link java.io.Writer}，视具体情况而定，不允许为null
     * @param <T> 输出流的类型
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    <T> void writeGeometryCollection(@NotNull GeometryCollection geometries, @NotNull T output) throws GeometryIOException;

    /**
     * 将一个多点对象写入到输出流
     *
     * @param multiPoint 多点对象，不允许为null
     * @param output 输出流，应该是{@link java.io.OutputStream}或{@link java.io.Writer}，视具体情况而定，不允许为null
     * @param <T> 输出流的类型
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    <T> void writeMultiPoint(@NotNull MultiPoint multiPoint, @NotNull T output) throws GeometryIOException;

    /**
     * 将一个多线串对象写入到输出流
     *
     * @param multiLineString 多线串对象，不允许为null
     * @param output 输出流，应该是{@link java.io.OutputStream}或{@link java.io.Writer}，视具体情况而定，不允许为null
     * @param <T> 输出流的类型
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    <T> void writeMultiLineString(@NotNull MultiLineString multiLineString, @NotNull T output) throws GeometryIOException;

    /**
     * 将一个多多边形对象写入到输出流
     *
     * @param multiPolygon 多多边形对象，不允许为null
     * @param output 输出流，应该是{@link java.io.OutputStream}或{@link java.io.Writer}，视具体情况而定，不允许为null
     * @param <T> 输出流的类型
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    <T> void writeMultiPolygon(@NotNull MultiPolygon multiPolygon, @NotNull T output) throws GeometryIOException;

}
