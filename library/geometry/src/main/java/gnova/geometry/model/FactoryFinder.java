package gnova.geometry.model;

import gnova.geometry.model.impl.jts.GeometryFactoryAdaptor;
import gnova.geometry.model.impl.jts.GeometryIndexFactoryAdaptor;

/**
 * 工厂查找器
 */
public class FactoryFinder {

    /**
     * 一个默认的几何对象工厂
     */
    static private GeometryFactory geometryFactory = new GeometryFactoryAdaptor();

    /**
     * 一个默认的坐标序列工厂
     */
    static private CoordinateSequenceFactory coordinateSequenceFactory = null;

    /**
     * 一个默认的几何索引工厂
     */
    static private GeometryIndexFactory geometryIndexFactory = new GeometryIndexFactoryAdaptor();

    /**
     * 获取一个默认的几何对象工厂
     *
     * @return 几何对象工厂
     */
    static public GeometryFactory getDefaultGeometryFactory() {
        return geometryFactory;
    }

    /**
     * 获取一个默认的坐标序列工厂
     *
     * @return 坐标序列工厂
     */
    static public CoordinateSequenceFactory getDefaultCoordinateSequenceFactory() {
        if (coordinateSequenceFactory == null) {
            coordinateSequenceFactory = getDefaultGeometryFactory().getCoordinateSequenceFactory();
        }
        return coordinateSequenceFactory;
    }

    /**
     * 获取一个默认的几何索引工厂
     *
     * @return 几何索引工厂
     */
    static public GeometryIndexFactory getDefaultGeometryIndexFactory() {
        return geometryIndexFactory;
    }

}
