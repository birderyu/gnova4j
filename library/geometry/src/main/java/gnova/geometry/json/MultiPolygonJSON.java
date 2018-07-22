package gnova.geometry.json;

import gnova.geometry.model.GeometryFactory;
import gnova.geometry.model.LinearRing;
import gnova.geometry.model.MultiPolygon;
import gnova.geometry.model.Polygon;
import gnova.core.json.JsonArrayBuilder;
import gnova.core.json.JsonObjectBuilder;
import gnova.core.json.JsonObject;

import java.util.Objects;

public final class MultiPolygonJSON
        extends GeometryJSON {

    private final PositionArrayArrayArray positions;

    public MultiPolygonJSON(double[][][][] positions) {
        this.positions = new PositionArrayArrayArray(positions);
    }

    public MultiPolygonJSON(PositionArrayArrayArray positions) {
        this.positions = positions;
    }

    public double[][][][] getCoordinates() {
        return positions.getCopyValues();
    }

    public MultiPolygon toMultiPolygon(GeometryFactory factory) {
        double[][][][] coordinates = positions.getValues();
        Polygon[] polygons = new Polygon[coordinates.length];
        for (int i = 0; i < polygons.length; i++) {
            LinearRing shell = factory.createLinearRing(toCoordinates(coordinates[i][0]));
            LinearRing[] holes = null;
            if (coordinates.length > 1) {
                holes = new LinearRing[coordinates[i].length - 1];
                for (int j = 0; j < holes.length; j++) {
                    holes[j] = factory.createLinearRing(toCoordinates(coordinates[i][j + 1]));
                }
            }
            polygons[i] = factory.createPolygon(shell, holes);
        }
        return factory.createMultiPolygon(polygons);
    }

    @Override
    public String getType() {
        return TYPE_MULTIPOLYGON;
    }

    @Override
    public MultiPolygon toGeometry(GeometryFactory factory) {
        return toMultiPolygon(factory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiPolygonJSON that = (MultiPolygonJSON) o;
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
    public MultiPolygonJSON clone() {
        return new MultiPolygonJSON(positions.clone());
    }

    @Override
    public JsonObject toJsonObject(JsonObjectBuilder job, JsonArrayBuilder jab) {
        JsonObject jsonObject = job.build();
        jsonObject.append(FIELD_NAME_TYPE, getType());
        jsonObject.append(FIELD_NAME_COORDINATES, positions.toJsonArray(jab));
        return jsonObject;
    }
}