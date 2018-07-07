package gnova.geometry;

import gnova.geometry.model.Geometry;

import java.util.function.Consumer;

@FunctionalInterface
public interface GeometricSetter extends Consumer<Geometry> {

    void setGeometry(Geometry geometry);

    @Override
    default void accept(Geometry geometry) {
        setGeometry(geometry);
    }

}
