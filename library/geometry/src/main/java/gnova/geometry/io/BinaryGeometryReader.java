package gnova.geometry.io;

import gnova.core.annotation.NotNull;
import gnova.core.annotation.Unsupported;
import gnova.geometry.model.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;

/**
 * 二进制几何对象输入接口
 *
 * <p>二进制几何对象输入接口接受一个字节的数组或{@link java.io.InputStream 字节输入流}作为参数，将其转化或读取成为一个{@link Geometry 几何对象}，
 * 注意，二进制几何对象输入接口不能够接受一个{@link java.io.Reader 字符输入流}作为参数，否则会抛出异常。
 */
@FunctionalInterface
public interface BinaryGeometryReader
        extends GeometryReader<byte[]>, BinaryGeometryIOSettings {

    /**
     * 从字节输入流中读取一个几何对象
     *
     * @param inputStream 字节输入流，不允许为null
     * @return 几何对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    Geometry read(@NotNull InputStream inputStream)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 从字节输入流中读取一个点对象
     *
     * @param inputStream 字节输入流，不允许为null
     * @return 点对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default Point readPoint(@NotNull InputStream inputStream)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (Point) read(inputStream);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 从字节输入流中读取一个线串对象
     *
     * @param inputStream 字节输入流，不允许为null
     * @return 线串对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default LineString readLineString(@NotNull InputStream inputStream)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (LineString) read(inputStream);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 从字节输入流中读取一个线环对象
     *
     * @param inputStream 字节输入流，不允许为null
     * @return 线环对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default LinearRing readLinearRing(@NotNull InputStream inputStream)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (LinearRing) read(inputStream);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 从字节输入流中读取一个多边形对象
     *
     * @param inputStream 字节输入流，不允许为null
     * @return 多边形对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default Polygon readPolygon(@NotNull InputStream inputStream)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (Polygon) read(inputStream);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 从字节输入流中读取一个几何集合对象
     *
     * @param inputStream 字节输入流，不允许为null
     * @return 几何集合对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default GeometryCollection readGeometryCollection(@NotNull InputStream inputStream)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (GeometryCollection) read(inputStream);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 从字节输入流中读取一个多点对象
     *
     * @param inputStream 字节输入流，不允许为null
     * @return 多点对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default MultiPoint readMultiPoint(@NotNull InputStream inputStream)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (MultiPoint) read(inputStream);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 从字节输入流中读取一个多线串对象
     *
     * @param inputStream 字节输入流，不允许为null
     * @return 多线串对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default MultiLineString readMultiLineString(@NotNull InputStream inputStream)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (MultiLineString) read(inputStream);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 从字节输入流中读取一个多多边形对象
     *
     * @param inputStream 字节输入流，不允许为null
     * @return 多多边形对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default MultiPolygon readMultiPolygon(@NotNull InputStream inputStream)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (MultiPolygon) read(inputStream);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将二进制对象转换为几何对象
     *
     * @param binaries 二进制对象，不允许为null
     * @return 几何对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default Geometry read(@NotNull byte[] binaries) throws GeometryIOException {
        return read(new ByteArrayInputStream(binaries));
    }

    /**
     * 将二进制对象转换为几何对象
     *
     * @param binaries 二进制对象，不允许为null
     * @return 点对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default Point readPoint(@NotNull byte[] binaries) throws GeometryIOException {
        return readPoint(new ByteArrayInputStream(binaries));
    }

    /**
     * 将二进制对象转换为线串对象或从输入流中读取一个线串对象
     *
     * @param binaries 二进制对象，不允许为null
     * @return 线串对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default LineString readLineString(@NotNull byte[] binaries) throws GeometryIOException {
        return readLineString(new ByteArrayInputStream(binaries));
    }

    /**
     * 将二进制对象转换为线环对象或从输入流中读取一个线环对象
     *
     * @param binaries 二进制对象，不允许为null
     * @return 线环对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default LinearRing readLinearRing(@NotNull byte[] binaries) throws GeometryIOException {
        return readLinearRing(new ByteArrayInputStream(binaries));
    }

    /**
     * 将二进制对象转换为多边形对象或从输入流中读取一个多边形对象
     *
     * @param binaries 二进制对象，不允许为null
     * @return 多边形对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default Polygon readPolygon(@NotNull byte[] binaries) throws GeometryIOException {
        return readPolygon(new ByteArrayInputStream(binaries));
    }

    /**
     * 将二进制对象转换为几何集合对象或从输入流中读取一个几何集合对象
     *
     * @param binaries 二进制对象，不允许为null
     * @return 几何集合对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default GeometryCollection readGeometryCollection(@NotNull byte[] binaries) throws GeometryIOException {
        return readGeometryCollection(new ByteArrayInputStream(binaries));
    }

    /**
     * 将二进制对象转换为多点对象或从输入流中读取一个多点对象
     *
     * @param binaries 二进制对象，不允许为null
     * @return 多点对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default MultiPoint readMultiPoint(@NotNull byte[] binaries) throws GeometryIOException {
        return readMultiPoint(new ByteArrayInputStream(binaries));
    }

    /**
     * 将二进制对象转换为多线串对象或从输入流中读取一个多线串对象
     *
     * @param binaries 二进制对象，不允许为null
     * @return 多线串对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default MultiLineString readMultiLineString(@NotNull byte[] binaries) throws GeometryIOException {
        return readMultiLineString(new ByteArrayInputStream(binaries));
    }

    /**
     * 将二进制对象转换为多多边形对象或从输入流中读取一个多多边形对象
     *
     * @param binaries 二进制对象，不允许为null
     * @return 多多边形对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default MultiPolygon readMultiPolygon(@NotNull byte[] binaries) throws GeometryIOException {
        return readMultiPolygon(new ByteArrayInputStream(binaries));
    }

}
