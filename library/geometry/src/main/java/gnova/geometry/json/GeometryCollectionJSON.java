package gnova.geometry.json;

import gnova.geometry.factory.GeometryFactory;
import gnova.geometry.model.Geometry;
import gnova.geometry.model.GeometryCollection;
import gnova.json.JsonArrayBuilder;
import gnova.json.JsonObjectBuilder;
import gnova.json.JsonArray;
import gnova.json.JsonObject;

import java.util.Arrays;

/**
 * 类型为几何集合的GeometryJSON
 */
public final class GeometryCollectionJSON
        extends GeometryJSON {

    private final GeometryJSON[] geometries;

    public GeometryCollectionJSON(GeometryJSON[] geometries) {
        this.geometries = geometries;
    }

    public GeometryJSON[] getGeometries() {
        GeometryJSON[] geometries = new GeometryJSON[this.geometries.length];
        for (int i = 0; i < geometries.length; i++) {
            geometries[i] = this.geometries[i].clone();
        }
        return geometries;
    }

    public GeometryCollection toGeometryCollection(GeometryFactory factory) {
        Geometry[] geometries = new Geometry[this.geometries.length];
        for (int i = 0; i < geometries.length; i++) {
            geometries[i] = this.geometries[i].toGeometry(factory);
        }
        return factory.createGeometryCollection(geometries);
    }

    @Override
    public String getType() {
        return TYPE_GEOMETRYCOLLECTION;
    }

    @Override
    public GeometryCollection toGeometry(GeometryFactory factory) {
        return toGeometryCollection(factory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeometryCollectionJSON that = (GeometryCollectionJSON) o;
        return Arrays.equals(geometries, that.geometries);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(geometries);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{\"type\": \""
                + getType()
                + "\", \"geometries\": [");
        for (int i = 0; i < geometries.length; i++) {
            sb.append(geometries[i].toString());
            if (i != geometries.length - 1) {
                sb.append(", ");
            }
        }
        return sb.append("]}").toString();
    }

    @Override
    public GeometryCollectionJSON clone() {
        return new GeometryCollectionJSON(getGeometries());
    }

    @Override
    public <JO, JA> JO toJsonObject(JsonObjectBuilder<JO> job, JsonArrayBuilder<JA> jab) {
        JsonObject<JO> jsonObject = job.build();
        jsonObject.append("type", getType());
        JsonArray<JA> array = jab.build();
        for (int i = 0; i < geometries.length; i++) {
            array.add(geometries[i].toJsonObject(job, jab));
        }
        jsonObject.append("geometries", array.getSubject());
        return jsonObject.getSubject();
    }
}
