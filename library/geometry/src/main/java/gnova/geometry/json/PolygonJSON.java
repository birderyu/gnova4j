package gnova.geometry.json;

import gnova.geometry.model.GeometryFactory;
import gnova.geometry.model.LinearRing;
import gnova.geometry.model.Polygon;
import gnova.core.json.JsonArrayBuilder;
import gnova.core.json.JsonObjectBuilder;
import gnova.core.json.JsonObject;

import java.util.Objects;

public final class PolygonJSON
        extends GeometryJSON {

    private final PositionArrayArray positions;

    public PolygonJSON(double[][][] positions) {
        this.positions = new PositionArrayArray(positions);
    }

    public PolygonJSON(PositionArrayArray positions) {
        this.positions = positions;
    }

    public double[][][] getCoordinates() {
        return positions.getCopyValues();
    }

    public Polygon toPolygon(GeometryFactory factory) {
        double[][][] coordinates = positions.getValues();
        LinearRing shell = factory.createLinearRing(toCoordinates(coordinates[0]));
        LinearRing[] holes = null;
        if (coordinates.length > 1) {
            holes = new LinearRing[coordinates.length - 1];
            for (int i = 0; i < holes.length; i++) {
                holes[i] = factory.createLinearRing(toCoordinates(coordinates[i + 1]));
            }
        }
        return factory.createPolygon(shell, holes);
    }

    @Override
    public String getType() {
        return TYPE_POLYGON;
    }

    @Override
    public Polygon toGeometry(GeometryFactory factory) {
        return toPolygon(factory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolygonJSON that = (PolygonJSON) o;
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
    public PolygonJSON clone() {
        return new PolygonJSON(positions.clone());
    }

    @Override
    public JsonObject toJsonObject(JsonObjectBuilder job, JsonArrayBuilder jab) {
        JsonObject jsonObject = job.build();
        jsonObject.append(FIELD_NAME_TYPE, getType());
        jsonObject.append(FIELD_NAME_COORDINATES, positions.toJsonArray(jab));
        return jsonObject;
    }
}
