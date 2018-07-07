package gnova.data.index.key;

import gnova.annotation.NotNull;
import gnova.geometry.model.BoundingBox;
import gnova.geometry.model.Geometry;

public class SpatialKey
        extends SingleKey {

    private BoundingBox bbox = null;

    public SpatialKey(@NotNull Geometry value) {
        super(value);
    }

    public BoundingBox getHyperCube() {
        if (bbox == null) {
            bbox = ((Geometry) value).getBoundingBox();
        }
        return bbox;
    }

}
