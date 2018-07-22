package gnova.geometry.json;

import gnova.geometry.model.GeometryFactory;
import gnova.geometry.model.Point;
import gnova.core.json.JsonArrayBuilder;
import gnova.core.json.JsonObjectBuilder;
import gnova.core.json.JsonObject;

import java.util.Objects;

/**
 * 类型为点类型的GeometryJSON
 */
public final class PointJSON
        extends GeometryJSON {

    private final Position position;

    public PointJSON(double[] position) {
        this.position = new Position(position);
    }

    public PointJSON(Position position) {
        this.position = position;
    }

    public double[] getCoordinates() {
        return position.getCopyValues();
    }

    public Point toPoint(GeometryFactory factory) {
        return factory.createPoint(toCoordinate(position.getValues()));
    }

    @Override
    public String getType() {
        return TYPE_POINT;
    }

    @Override
    public Point toGeometry(GeometryFactory factory) {
        return toPoint(factory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointJSON pointJSON = (PointJSON) o;
        return Objects.equals(position, pointJSON.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return "{\"" + FIELD_NAME_TYPE + "\": \""
                + getType()
                + "\", \"" + FIELD_NAME_COORDINATES + "\": "
                + position.toString() + "}";
    }

    @Override
    public PointJSON clone() {
        return new PointJSON(position.clone());
    }

    @Override
    public JsonObject toJsonObject(JsonObjectBuilder job, JsonArrayBuilder jab) {
        JsonObject jsonObject = job.build();
        jsonObject.append(FIELD_NAME_TYPE, getType());
        jsonObject.append(FIELD_NAME_COORDINATES, position.toJsonArray(jab));
        return jsonObject;
    }
}
