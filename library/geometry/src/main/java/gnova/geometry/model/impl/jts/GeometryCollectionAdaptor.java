package gnova.geometry.model.impl.jts;

import gnova.geometry.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Birderyu on 2017/6/23.
 */
class GeometryCollectionAdaptor<G extends Geometry>
    extends AbstractGeometryAdaptor implements GeometryCollection<G> {

    public GeometryCollectionAdaptor(com.vividsolutions.jts.geom.GeometryCollection jtsGeometryCollection) {
        super(jtsGeometryCollection);
    }

    @Override
    public int size() {
        return getJts().getNumGeometries();
    }

    @Override
    public G getGeometryAt(int n) {
        return (G) GeometryFactoryAdaptor.fromJtsGeometry(getJts().getGeometryN(n));
    }

    @Override
    public int getDimension() {
        return getJts().getDimension();
    }

    @Override
    public Geometry union() {
        return getFactory().fromJtsGeometry(getJts().union());
    }

    @Override
    public MultiLineString lineMerge() {

        if (getType() == GeometryType.MultiPoint
                || getType() == GeometryType.MultiPolygon) {
            // 这些集合中不会包含线串
            return null;
        }

        // 获取集合中的所有线串
        List<com.vividsolutions.jts.geom.LineString> jtsLineStrings
                = new ArrayList<>();
        getLineStringFromJtsGeometryCollection(getJts(), jtsLineStrings);

        // 构造线合并器
        com.vividsolutions.jts.operation.linemerge.LineMerger lineMerger
                = new com.vividsolutions.jts.operation.linemerge.LineMerger();
        lineMerger.add(jtsLineStrings);
        Collection jtsMergedLineStrings = lineMerger.getMergedLineStrings();

        // 将结果构造成一个多线
        if (jtsMergedLineStrings.isEmpty()) {
            return null;
        }

        List<LineString> lineStrings = new ArrayList<>();
        for (Object object : jtsMergedLineStrings) {
            if (object instanceof com.vividsolutions.jts.geom.LineString) {
                lineStrings.add(new LineStringAdaptor(
                        (com.vividsolutions.jts.geom.LineString) object));
            }
        }
        if (lineStrings.isEmpty()) {
            return null;
        }

        return getFactory().createMultiLineString(
                lineStrings.toArray(new LineString[lineStrings.size()]));
    }

    @Override
    public MultiPolygon polygonize() {

        if (getType() == GeometryType.MultiPoint
                || getType() == GeometryType.MultiPolygon) {
            // 这些集合中不会包含折线
            return null;
        }

        // 获取集合中的所有线串
        List<com.vividsolutions.jts.geom.LineString> jtsLineStrings
                = new ArrayList<>();
        getLineStringFromJtsGeometryCollection(getJts(), jtsLineStrings);

        // 构造多变化器
        com.vividsolutions.jts.operation.polygonize.Polygonizer polygonizer
                = new com.vividsolutions.jts.operation.polygonize.Polygonizer();
        polygonizer.add(jtsLineStrings);
        Collection jtsPolygonizePolygons = polygonizer.getPolygons();

        // 将结果构造成一个多多边形
        if (jtsPolygonizePolygons.isEmpty()) {
            return null;
        }

        List<Polygon> polygons = new ArrayList<>();
        for (Object object : jtsPolygonizePolygons) {
            if (object instanceof com.vividsolutions.jts.geom.Polygon) {
                polygons.add(new PolygonAdaptor(
                        (com.vividsolutions.jts.geom.Polygon) object));
            }
        }
        if (polygons.isEmpty()) {
            return null;
        }

        return getFactory().createMultiPolygon(
                polygons.toArray(new Polygon[polygons.size()]));

    }

    @Override
    public com.vividsolutions.jts.geom.GeometryCollection getJts() {
        return (com.vividsolutions.jts.geom.GeometryCollection) super.getJts();
    }

    @Override
    public GeometryCollection reverse() {
        return (GeometryCollection) super.reverse();
    }

    @Override
    public GeometryCollection normalize() {
        return (GeometryCollection) super.normalize();
    }

    @Override
    public GeometryCollection clone() {
        return (GeometryCollection) super.clone();
    }

    /**
     * 从JTS几何集合中递归获取所有的折线
     *
     * @param jtsGeometryCollection
     * @param jtsLineStrings
     */
    private void getLineStringFromJtsGeometryCollection(
            com.vividsolutions.jts.geom.GeometryCollection jtsGeometryCollection,
            List<com.vividsolutions.jts.geom.LineString> jtsLineStrings) {

        int size = jtsGeometryCollection.getNumGeometries();

        for (int i = 0; i < size; i++) {

            com.vividsolutions.jts.geom.Geometry jtsGeometry = jtsGeometryCollection.getGeometryN(i);
            if (jtsGeometry instanceof com.vividsolutions.jts.geom.LineString) {

                jtsLineStrings.add((com.vividsolutions.jts.geom.LineString) jtsGeometry);

            } else if (jtsGeometry instanceof com.vividsolutions.jts.geom.MultiLineString
                    || jtsGeometry instanceof com.vividsolutions.jts.geom.GeometryCollection) {

                getLineStringFromJtsGeometryCollection(
                        (com.vividsolutions.jts.geom.GeometryCollection) jtsGeometry,
                        jtsLineStrings
                );

            }

        }

    }

}
