package gnova.geometry.io;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.*;

import java.io.InputStream;
import java.io.Reader;

/**
 * 几何对象输入接口
 *
 * <p>该接口用于将其他对象转化为几何对象，比如将字符串对象转化成几何对象；或从输入流中读取一个几何对象。
 */
public interface GeometryReader<T> extends GeometryIOSettings {

    /**
     * 若环没有闭合，是否自动将其闭合
     *
     * @return 自动将环闭合，则返回true，否则返回false
     */
    default boolean repairRings() {
        return true;
    }

    /**
     * 将其他对象转换为几何对象
     *
     * @param obj 其他对象，不允许为null
     * @return 几何对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    Geometry read(@NotNull T obj) throws GeometryIOException;

    /**
     * 将其他对象转换为几何对象
     *
     * @param obj 其他对象，不允许为null
     * @return 点对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default Point readPoint(@NotNull T obj) throws GeometryIOException {
        try {
            return (Point) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将其他对象转换为线串对象或从输入流中读取一个线串对象
     *
     * @param obj 其他对象，不允许为null
     * @return 线串对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default LineString readLineString(@NotNull T obj) throws GeometryIOException {
        try {
            return (LineString) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将其他对象转换为线环对象或从输入流中读取一个线环对象
     *
     * @param obj 其他对象，不允许为null
     * @return 线环对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default LinearRing readLinearRing(@NotNull T obj) throws GeometryIOException {
        try {
            return (LinearRing) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将其他对象转换为多边形对象或从输入流中读取一个多边形对象
     *
     * @param obj 其他对象，不允许为null
     * @return 多边形对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default Polygon readPolygon(@NotNull T obj) throws GeometryIOException {
        try {
            return (Polygon) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将其他对象转换为几何集合对象或从输入流中读取一个几何集合对象
     *
     * @param obj 其他对象，不允许为null
     * @return 几何集合对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default GeometryCollection readGeometryCollection(@NotNull T obj) throws GeometryIOException {
        try {
            return (GeometryCollection) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将其他对象转换为多点对象或从输入流中读取一个多点对象
     *
     * @param obj 其他对象，不允许为null
     * @return 多点对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default MultiPoint readMultiPoint(@NotNull T obj) throws GeometryIOException {
        try {
            return (MultiPoint) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将其他对象转换为多线串对象或从输入流中读取一个多线串对象
     *
     * @param obj 其他对象，不允许为null
     * @return 多线串对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default MultiLineString readMultiLineString(@NotNull T obj) throws GeometryIOException {
        try {
            return (MultiLineString) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将其他对象转换为多多边形对象或从输入流中读取一个多多边形对象
     *
     * @param obj 其他对象，不允许为null
     * @return 多多边形对象，不会返回null
     * @throws GeometryIOException 若转换失败，则抛出此异常
     */
    @NotNull
    default MultiPolygon readMultiPolygon(@NotNull T obj) throws GeometryIOException {
        try {
            return (MultiPolygon) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

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

}
