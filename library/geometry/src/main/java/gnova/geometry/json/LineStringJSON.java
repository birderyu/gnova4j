package gnova.geometry.json;

import gnova.geometry.factory.GeometryFactory;
import gnova.geometry.model.LineString;
import gnova.core.json.JsonArrayBuilder;
import gnova.core.json.JsonObjectBuilder;
import gnova.core.json.JsonObject;

import java.util.Objects;

public final class LineStringJSON
        extends GeometryJSON {

    private final PositionArray positions;

    public LineStringJSON(double[][] positions) {
        this.positions = new PositionArray(positions);
    }

    public LineStringJSON(PositionArray positions) {
        this.positions = positions;
    }

    public double[][] getCoordinates() {
        return positions.getCopyValues();
    }

    public LineString toLineString(GeometryFactory factory) {
        double[][] coordinates = positions.getValues();
        return factory.createLineString(toCoordinates(coordinates));
    }

    @Override
    public String getType() {
        return TYPE_LINESTRING;
    }

    @Override
    public LineString toGeometry(GeometryFactory factory) {
        return toLineString(factory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineStringJSON that = (LineStringJSON) o;
        return Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positions);
    }

    @Override
    public String toString() {
        return "{\"type\": \""
                + getType()
                + "\", \"coordinates\": "
                + positions.toString()
                + "}";
    }

    @Override
    public LineStringJSON clone() {
        return new LineStringJSON(positions.clone());
    }

    @Override
    public <JO, JA> JO toJsonObject(JsonObjectBuilder<JO> job, JsonArrayBuilder<JA> jab) {
        JsonObject<JO> jsonObject = job.build();
        jsonObject.append("type", getType());
        jsonObject.append("coordinates", positions.toJsonArray(jab));
        return jsonObject.getSubject();
    }
}
