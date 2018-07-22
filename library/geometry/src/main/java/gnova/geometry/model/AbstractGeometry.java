package gnova.geometry.model;

import gnova.core.json.JsonObject;
import gnova.geometry.json.GeometryJSON;
import gnova.core.json.JsonArrayBuilder;
import gnova.core.json.JsonObjectBuilder;

public abstract class AbstractGeometry
        implements Geometry {

    protected final GeometryFactory factory;

    public AbstractGeometry(GeometryFactory factory) {
        this.factory = factory;
    }

    @Override
    public GeometryFactory getFactory() {
        return factory;
    }

    @Override
    public String toGeometryJSON() {
        return GeometryJSON.fromGeometry(this).toString();
    }

    @Override
    public JsonObject toGeometryJSON(JsonObjectBuilder job, JsonArrayBuilder jab) {
        return GeometryJSON.fromGeometry(this).toJsonObject(job, jab);
    }

    @Override
    public String toString() {
        return toGeometryJSON();
    }

    @Override
    public Geometry clone() {
        try {
            return (Geometry) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
