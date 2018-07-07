package gnova.geometry;

import gnova.geometry.model.Geometry;

import java.util.function.Supplier;

@FunctionalInterface
public interface GeometricGetter
        extends Supplier<Geometry> {

    Geometry getGeometry();

    @Override
    default Geometry get() {
        return getGeometry();
    }

}
