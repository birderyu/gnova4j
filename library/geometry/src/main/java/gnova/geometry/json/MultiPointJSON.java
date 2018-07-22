package gnova.geometry.json;

import gnova.geometry.model.GeometryFactory;
import gnova.geometry.model.MultiPoint;
import gnova.core.json.JsonArrayBuilder;
import gnova.core.json.JsonObjectBuilder;
import gnova.core.json.JsonObject;

import java.util.Objects;

public final class MultiPointJSON
        extends GeometryJSON {

    private final PositionArray positions;

    public MultiPointJSON(double[][] positions) {
        this.positions = new PositionArray(positions);
    }

    public MultiPointJSON(PositionArray positions) {
        this.positions = positions;
    }

    public double[][] getCoordinates() {
        return positions.getCopyValues();
    }

    public MultiPoint toMultiPoint(GeometryFactory factory) {
        double[][] coordinates = positions.getValues();
        return factory.createMultiPoint(toCoordinates(coordinates));
    }

    @Override
    public String getType() {
        return TYPE_MULTIPOINT;
    }

    @Override
    public MultiPoint toGeometry(GeometryFactory factory) {
        return toMultiPoint(factory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiPointJSON that = (MultiPointJSON) o;
        return Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positions);
    }

    @Override
    public String toString() {
        return "{\"" + FIELD_NAME_TYPE + "\": \""
                + getType()
                + "\", \"" + FIELD_NAME_COORDINATES + "\": "
                + positions + "}";
    }

    @Override
    public MultiPointJSON clone() {
        return new MultiPointJSON(positions.clone());
    }

    @Override
    public JsonObject toJsonObject(JsonObjectBuilder job, JsonArrayBuilder jab) {
        JsonObject jsonObject = job.build();
        jsonObject.append(FIELD_NAME_TYPE, getType());
        jsonObject.append(FIELD_NAME_COORDINATES, positions.toJsonArray(jab));
        return jsonObject;
    }
}
