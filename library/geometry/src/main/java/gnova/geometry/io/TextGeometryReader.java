package gnova.geometry.io;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.*;

import java.io.Reader;
import java.io.StringReader;

/**
 * 文本几何对象输入接口
 */
@FunctionalInterface
public interface TextGeometryReader
        extends GeometryReader<String> {

    /**
     * 从字符输入流中读取一个几何对象
     *
     * @param reader 字符输入流，不允许为null
     * @return 几何对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    Geometry read(@NotNull Reader reader)
            throws GeometryIOException, UnsupportedOperationException;

    /**
     * 从字符输入流中读取一个点对象
     *
     * @param reader 字符输入流，不允许为null
     * @return 点对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default Point readPoint(@NotNull Reader reader)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (Point) read(reader);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 从字符输入流中读取一个线串对象
     *
     * @param reader 字符输入流，不允许为null
     * @return 线串对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default LineString readLineString(@NotNull Reader reader)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (LineString) read(reader);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 从字符输入流中读取一个线环对象
     *
     * @param reader 字符输入流，不允许为null
     * @return 线环对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default LinearRing readLinearRing(@NotNull Reader reader)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (LinearRing) read(reader);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 从字符输入流中读取一个多边形对象
     *
     * @param reader 字符输入流，不允许为null
     * @return 多边形对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default Polygon readPolygon(@NotNull Reader reader)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (Polygon) read(reader);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 从字符输入流中读取一个几何集合对象
     *
     * @param reader 字符输入流，不允许为null
     * @return 几何集合对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default GeometryCollection readGeometryCollection(@NotNull Reader reader)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (GeometryCollection) read(reader);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 从字符输入流中读取一个多点对象
     *
     * @param reader 字符输入流，不允许为null
     * @return 多点对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default MultiPoint readMultiPoint(@NotNull Reader reader)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (MultiPoint) read(reader);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 从字符输入流中读取一个多线串对象
     *
     * @param reader 字符输入流，不允许为null
     * @return 多线串对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default MultiLineString readMultiLineString(@NotNull Reader reader)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (MultiLineString) read(reader);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 从字符输入流中读取一个多多边形对象
     *
     * @param reader 字符输入流，不允许为null
     * @return 多多边形对象，不会返回null
     * @throws GeometryIOException 若读取失败，则抛出此异常
     * @throws UnsupportedOperationException 若不支持此方法，则抛出此异常
     */
    @NotNull
    default MultiPolygon readMultiPolygon(@NotNull Reader reader)
            throws GeometryIOException, UnsupportedOperationException {
        try {
            return (MultiPolygon) read(reader);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将文本对象转换为几何对象
     *
     * @param text 文本对象，不允许为null
     * @return 几何对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default Geometry read(@NotNull String text) throws GeometryIOException {
        return read(new StringReader(text));
    }

    /**
     * 将文本对象转换为几何对象
     *
     * @param text 文本对象，不允许为null
     * @return 点对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default Point readPoint(@NotNull String text) throws GeometryIOException {
        return readPoint(new StringReader(text));
    }

    /**
     * 将文本对象转换为线串对象或从输入流中读取一个线串对象
     *
     * @param text 文本对象，不允许为null
     * @return 线串对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default LineString readLineString(@NotNull String text) throws GeometryIOException {
        return readLineString(new StringReader(text));
    }

    /**
     * 将文本对象转换为线环对象或从输入流中读取一个线环对象
     *
     * @param text 文本对象，不允许为null
     * @return 线环对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default LinearRing readLinearRing(@NotNull String text) throws GeometryIOException {
        return readLinearRing(new StringReader(text));
    }

    /**
     * 将文本对象转换为多边形对象或从输入流中读取一个多边形对象
     *
     * @param text 文本对象，不允许为null
     * @return 多边形对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default Polygon readPolygon(@NotNull String text) throws GeometryIOException {
        return readPolygon(new StringReader(text));
    }

    /**
     * 将文本对象转换为几何集合对象或从输入流中读取一个几何集合对象
     *
     * @param text 文本对象，不允许为null
     * @return 几何集合对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default GeometryCollection readGeometryCollection(@NotNull String text) throws GeometryIOException {
        return readGeometryCollection(new StringReader(text));
    }

    /**
     * 将文本对象转换为多点对象或从输入流中读取一个多点对象
     *
     * @param text 文本对象，不允许为null
     * @return 多点对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default MultiPoint readMultiPoint(@NotNull String text) throws GeometryIOException {
        return readMultiPoint(new StringReader(text));
    }

    /**
     * 将文本对象转换为多线串对象或从输入流中读取一个多线串对象
     *
     * @param text 文本对象，不允许为null
     * @return 多线串对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default MultiLineString readMultiLineString(@NotNull String text) throws GeometryIOException {
        return readMultiLineString(new StringReader(text));
    }

    /**
     * 将文本对象转换为多多边形对象或从输入流中读取一个多多边形对象
     *
     * @param text 文本对象，不允许为null
     * @return 多多边形对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default MultiPolygon readMultiPolygon(@NotNull String text) throws GeometryIOException {
        return readMultiPolygon(new StringReader(text));
    }

}
