package gnova.geometry.json;

import gnova.geometry.model.GeometryFactory;
import gnova.geometry.model.LineString;
import gnova.geometry.model.MultiLineString;
import gnova.core.json.JsonArrayBuilder;
import gnova.core.json.JsonObjectBuilder;
import gnova.core.json.JsonObject;

import java.util.Objects;

public final class MultiLineStringJSON
        extends GeometryJSON {

    private final PositionArrayArray positions;

    public MultiLineStringJSON(double[][][] positions) {
        this.positions = new PositionArrayArray(positions);
    }

    public MultiLineStringJSON(PositionArrayArray positions) {
        this.positions = positions;
    }

    public double[][][] getCoordinates() {
        return positions.getCopyValues();
    }

    public MultiLineString toMultiLineString(GeometryFactory factory) {
        double[][][] coordinates = positions.getValues();
        LineString[] lineStrings = new LineString[coordinates.length];
        for (int i = 0; i < lineStrings.length; i++) {
            lineStrings[i] = factory.createLineString(toCoordinates(coordinates[i]));
        }
        return factory.createMultiLineString(lineStrings);
    }

    @Override
    public String getType() {
        return TYPE_MULTILINESTRING;
    }

    @Override
    public MultiLineString toGeometry(GeometryFactory factory) {
        return toMultiLineString(factory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiLineStringJSON that = (MultiLineStringJSON) o;
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
    public MultiLineStringJSON clone() {
        return new MultiLineStringJSON(positions.clone());
    }

    @Override
    public JsonObject toJsonObject(JsonObjectBuilder job, JsonArrayBuilder jab) {
        JsonObject jsonObject = job.build();
        jsonObject.append(FIELD_NAME_TYPE, getType());
        jsonObject.append(FIELD_NAME_COORDINATES, positions.toJsonArray(jab));
        return jsonObject;
    }
}