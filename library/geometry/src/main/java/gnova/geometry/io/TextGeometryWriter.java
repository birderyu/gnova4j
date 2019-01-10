package gnova.geometry.io;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public interface TextGeometryWriter
        extends GeometryWriter<String> {

    /**
     * 将点对象转换成文本对象
     *
     * @param point 点对象，不允许为null
     * @return 文本对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    String writePoint(@NotNull Point point) throws GeometryIOException;

    /**
     * 将线串对象转换成文本对象
     *
     * @param lineString 线串对象，不允许为null
     * @return 文本对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    String writeLineString(@NotNull LineString lineString) throws GeometryIOException;

    /**
     * 将线环对象转换成文本对象
     *
     * @param linearRing 线环对象，不允许为null
     * @return 文本对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    String writeLinearRing(@NotNull LinearRing linearRing) throws GeometryIOException;

    /**
     * 将多边形对象转换成文本对象
     *
     * @param polygon 多边形对象，不允许为null
     * @return 文本对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    String writePolygon(@NotNull Polygon polygon) throws GeometryIOException;

    /**
     * 将线串对象转换成文本对象
     *
     * @param geometries 几何集合对象，不允许为null
     * @return 文本对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    String writeGeometryCollection(@NotNull GeometryCollection geometries) throws GeometryIOException;

    /**
     * 将多多边形对象转换成文本对象
     *
     * @param multiPoint 多多边形对象，不允许为null
     * @return 文本对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    String writeMultiPoint(@NotNull MultiPoint multiPoint) throws GeometryIOException;

    /**
     * 将多线串对象转换成文本对象
     *
     * @param multiLineString 多线串对象，不允许为null
     * @return 文本对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    String writeMultiLineString(@NotNull MultiLineString multiLineString) throws GeometryIOException;

    /**
     * 将多多边形对象转换成文本对象
     *
     * @param multiPolygon 多多边形对象，不允许为null
     * @return 文本对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    String writeMultiPolygon(@NotNull MultiPolygon multiPolygon) throws GeometryIOException;

    /**
     * 将一个几何对象写入到字节输出流
     *
     * @param geometry 几何对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void write(@NotNull Geometry geometry, @NotNull OutputStream outputStream)
            throws GeometryIOException {
        write(geometry, new OutputStreamWriter(outputStream));
    }

    /**
     * 将一个点对象写入到字节输出流
     *
     * @param point 点对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writePoint(@NotNull Point point, @NotNull OutputStream outputStream)
            throws GeometryIOException {
        writePoint(point, new OutputStreamWriter(outputStream));
    }

    /**
     * 将一个线串对象写入到字节输出流
     *
     * @param lineString 线串对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writeLineString(@NotNull LineString lineString, @NotNull OutputStream outputStream)
            throws GeometryIOException {
        writeLineString(lineString, new OutputStreamWriter(outputStream));
    }

    /**
     * 将一个线环对象写入到字节输出流
     *
     * @param linearRing 线环对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writeLinearRing(@NotNull LinearRing linearRing, @NotNull OutputStream outputStream)
            throws GeometryIOException {
        writeLinearRing(linearRing, new OutputStreamWriter(outputStream));
    }

    /**
     * 将一个多边形对象写入到字节输出流
     *
     * @param polygon 多边形对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writePolygon(@NotNull Polygon polygon, @NotNull OutputStream outputStream)
            throws GeometryIOException {
        writePolygon(polygon, new OutputStreamWriter(outputStream));
    }

    /**
     * 将一个几何集合对象写入到字节输出流
     *
     * @param geometries 几何集合对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writeGeometryCollection(@NotNull GeometryCollection geometries, @NotNull OutputStream outputStream)
            throws GeometryIOException {
        writeGeometryCollection(geometries, new OutputStreamWriter(outputStream));
    }

    /**
     * 将一个多点对象写入到字节输出流
     *
     * @param multiPoint 多点对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writeMultiPoint(@NotNull MultiPoint multiPoint, @NotNull OutputStream outputStream)
            throws GeometryIOException {
        writeMultiPoint(multiPoint, new OutputStreamWriter(outputStream));
    }

    /**
     * 将一个多线串对象写入到字节输出流
     *
     * @param multiLineString 多线串对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writeMultiLineString(@NotNull MultiLineString multiLineString, @NotNull OutputStream outputStream)
            throws GeometryIOException {
        writeMultiLineString(multiLineString, new OutputStreamWriter(outputStream));
    }

    /**
     * 将一个多多边形对象写入到字节输出流
     *
     * @param multiPolygon 多多边形对象，不允许为null
     * @param outputStream 字节输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writeMultiPolygon(@NotNull MultiPolygon multiPolygon, @NotNull OutputStream outputStream)
            throws GeometryIOException {
        writeMultiPolygon(multiPolygon, new OutputStreamWriter(outputStream));
    }

    /**
     * 将一个几何对象写入到字符输出流
     *
     * @param geometry 几何对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void write(@NotNull Geometry geometry, @NotNull Writer writer)
            throws GeometryIOException {
        String text = write(geometry);
        try {
            writer.write(text);
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将一个点对象写入到字符输出流
     *
     * @param point 点对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writePoint(@NotNull Point point, @NotNull Writer writer)
            throws GeometryIOException {
        String text = writePoint(point);
        try {
            writer.write(text);
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将一个线串对象写入到字符输出流
     *
     * @param lineString 线串对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writeLineString(@NotNull LineString lineString, @NotNull Writer writer)
            throws GeometryIOException {
        String text = writeLineString(lineString);
        try {
            writer.write(text);
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将一个线环对象写入到字符输出流
     *
     * @param linearRing 线环对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writeLinearRing(@NotNull LinearRing linearRing, @NotNull Writer writer)
            throws GeometryIOException {
        String text = writeLinearRing(linearRing);
        try {
            writer.write(text);
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将一个多边形对象写入到字符输出流
     *
     * @param polygon 多边形对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writePolygon(@NotNull Polygon polygon, @NotNull Writer writer)
            throws GeometryIOException {
        String text = writePolygon(polygon);
        try {
            writer.write(text);
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将一个几何集合对象写入到字符输出流
     *
     * @param geometries 几何集合对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writeGeometryCollection(@NotNull GeometryCollection geometries, @NotNull Writer writer)
            throws GeometryIOException {
        String text = writeGeometryCollection(geometries);
        try {
            writer.write(text);
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将一个多点对象写入到字符输出流
     *
     * @param multiPoint 多点对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writeMultiPoint(@NotNull MultiPoint multiPoint, @NotNull Writer writer)
            throws GeometryIOException {
        String text = writeMultiPoint(multiPoint);
        try {
            writer.write(text);
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将一个多线串对象写入到字符输出流
     *
     * @param multiLineString 多线串对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writeMultiLineString(@NotNull MultiLineString multiLineString, @NotNull Writer writer)
            throws GeometryIOException {
        String text = writeMultiLineString(multiLineString);
        try {
            writer.write(text);
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将一个多多边形对象写入到字符输出流
     *
     * @param multiPolygon 多多边形对象，不允许为null
     * @param writer 字符输出流，不允许为null
     * @throws GeometryIOException 若写入失败，则抛出此异常
     */
    default void writeMultiPolygon(@NotNull MultiPolygon multiPolygon, @NotNull Writer writer)
            throws GeometryIOException {
        String text = writeMultiPolygon(multiPolygon);
        try {
            writer.write(text);
        } catch (IOException e) {
            throw new GeometryIOException(e);
        }
    }

}
