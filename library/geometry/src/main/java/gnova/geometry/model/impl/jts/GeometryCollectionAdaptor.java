package gnova.geometry.model.impl.jts;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Birderyu on 2017/6/23.
 */
class GeometryCollectionAdaptor<G extends Geometry>
    extends AbstractGeometryAdaptor implements GeometryCollection<G> {

    public GeometryCollectionAdaptor(org.locationtech.jts.geom.GeometryCollection jtsGeometryCollection) {
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
    @NotNull
    public Geometry getBoundary() {
        switch (getType()) {
            case GeometryCollection: {
                List<Geometry> geometries = new ArrayList<>();
                if (!getGeometryFromJtsGeometryCollection(getJts(), geometries)
                        || geometries.isEmpty()) {
                    return NONE;
                }
                if (geometries.size() == 1) {
                    return geometries.get(0).getBoundary();
                } else {
                    switch (geometries.get(0).getType()) {
                        case LineString:
                        case LinearRing: {
                            LineString[] lineStrings = new LineString[geometries.size()];
                            int index = 0;
                            for (Geometry geometry : geometries) {
                                lineStrings[index++] = (LineString) geometry;
                            }
                            MultiLineString mls = getFactory().createMultiLineString(lineStrings);
                            return mls.getBoundary();
                        }
                        case Polygon: {
                            Polygon[] polygons = new Polygon[geometries.size()];
                            int index = 0;
                            for (Geometry geometry : geometries) {
                                polygons[index++] = (Polygon) geometry;
                            }
                            MultiPolygon mp = getFactory().createMultiPolygon(polygons);
                            return mp.getBoundary();
                        }
                    }
                    return NONE;
                }
            }
            case MultiPoint:
                return NONE;
            case MultiLineString:
            case MultiPolygon:
                return getFactory().fromJtsGeometry(getJts().getBoundary());
        }
        return NONE;
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
        List<org.locationtech.jts.geom.LineString> jtsLineStrings
                = new ArrayList<>();
        getJtsLineStringFromJtsGeometryCollection(getJts(), jtsLineStrings);

        // 构造线合并器
        org.locationtech.jts.operation.linemerge.LineMerger lineMerger
                = new org.locationtech.jts.operation.linemerge.LineMerger();
        lineMerger.add(jtsLineStrings);
        Collection jtsMergedLineStrings = lineMerger.getMergedLineStrings();

        // 将结果构造成一个多线
        if (jtsMergedLineStrings.isEmpty()) {
            return null;
        }

        List<LineString> lineStrings = new ArrayList<>();
        for (Object object : jtsMergedLineStrings) {
            if (object instanceof org.locationtech.jts.geom.LineString) {
                lineStrings.add(new LineStringAdaptor(
                        (org.locationtech.jts.geom.LineString) object));
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
        List<org.locationtech.jts.geom.LineString> jtsLineStrings
                = new ArrayList<>();
        getJtsLineStringFromJtsGeometryCollection(getJts(), jtsLineStrings);

        // 构造多变化器
        org.locationtech.jts.operation.polygonize.Polygonizer polygonizer
                = new org.locationtech.jts.operation.polygonize.Polygonizer();
        polygonizer.add(jtsLineStrings);
        Collection jtsPolygonizePolygons = polygonizer.getPolygons();

        // 将结果构造成一个多多边形
        if (jtsPolygonizePolygons.isEmpty()) {
            return null;
        }

        List<Polygon> polygons = new ArrayList<>();
        for (Object object : jtsPolygonizePolygons) {
            if (object instanceof org.locationtech.jts.geom.Polygon) {
                polygons.add(new PolygonAdaptor(
                        (org.locationtech.jts.geom.Polygon) object));
            }
        }
        if (polygons.isEmpty()) {
            return null;
        }

        return getFactory().createMultiPolygon(
                polygons.toArray(new Polygon[polygons.size()]));

    }

    @Override
    public org.locationtech.jts.geom.GeometryCollection getJts() {
        return (org.locationtech.jts.geom.GeometryCollection) super.getJts();
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
    private void getJtsLineStringFromJtsGeometryCollection(
            org.locationtech.jts.geom.GeometryCollection jtsGeometryCollection,
            List<org.locationtech.jts.geom.LineString> jtsLineStrings) {

        int size = jtsGeometryCollection.getNumGeometries();

        for (int i = 0; i < size; i++) {

            org.locationtech.jts.geom.Geometry jtsGeometry = jtsGeometryCollection.getGeometryN(i);
            if (jtsGeometry instanceof org.locationtech.jts.geom.LineString) {

                jtsLineStrings.add((org.locationtech.jts.geom.LineString) jtsGeometry);

            } else if (jtsGeometry instanceof org.locationtech.jts.geom.MultiLineString
                    || jtsGeometry instanceof org.locationtech.jts.geom.GeometryCollection) {

                getJtsLineStringFromJtsGeometryCollection(
                        (org.locationtech.jts.geom.GeometryCollection) jtsGeometry,
                        jtsLineStrings
                );

            }

        }

    }

    /**
     * 若集合中的所有集合对象都是一致的，则将其添加到geometries中并返回true，否则返回false
     * @param jtsGeometryCollection 几何集合对象
     * @param geometries
     * @return
     */
    private boolean getGeometryFromJtsGeometryCollection(
            org.locationtech.jts.geom.GeometryCollection jtsGeometryCollection,
            List<Geometry> geometries) {

        int size = jtsGeometryCollection.getNumGeometries();

        for (int i = 0; i < size; i++) {

            org.locationtech.jts.geom.Geometry jtsGeometry = jtsGeometryCollection.getGeometryN(i);
            Geometry geometry = getFactory().fromJtsGeometry(jtsGeometry);
            GeometryType gt = geometry.getType();
            switch (gt) {
                case None:
                    throw new IllegalArgumentException("there is a null or NONE in GeometryCollection");
                case Point:
                case LineString:
                case LinearRing:
                case Polygon:
                    if (!geometries.isEmpty() && geometries.get(0).getType() != gt) {
                        return false;
                    } else {
                        geometries.add(geometry);
                    }
                    break;
                case MultiPoint:
                    if (!geometries.isEmpty() && geometries.get(0).getType() != GeometryType.Point) {
                        return false;
                    } else {
                        MultiPoint mp = (MultiPoint) geometry;
                        for (Point p : mp) {
                            geometries.add(p);
                        }
                    }
                    break;
                case MultiLineString:
                    if (!geometries.isEmpty() && geometries.get(0).getType() != GeometryType.LineString) {
                        return false;
                    } else {
                        MultiLineString mls = (MultiLineString) geometry;
                        for (LineString ls : mls) {
                            geometries.add(ls);
                        }
                    }
                    break;
                case MultiPolygon:
                    if (!geometries.isEmpty() && geometries.get(0).getType() != GeometryType.Polygon) {
                        return false;
                    } else {
                        MultiPolygon mp = (MultiPolygon) geometry;
                        for (Polygon p : mp) {
                            geometries.add(p);
                        }
                    }
                    break;
                case GeometryCollection:
                    GeometryCollection gc = (GeometryCollection) geometry;
                    if (!getGeometryFromJtsGeometryCollection(
                            getFactory().toJtsGeometryCollection(gc),
                            geometries)) {
                        return false;
                    }
            }
        }
        return true;
    }
}
